package service;

import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.lang.reflect.Field;


public class ClientService {
    private List<Client> clienti;


    public ClientService() {
        this.clienti = getAllClienti();
    }

    public void adaugaClient(Client client) {
        clienti.add(client);
        System.out.println("Clientul " + client.getNume() + " a fost adăugat.");
    }

    public boolean stergeClient(int idClient) {
        return clienti.removeIf(client -> client.getIdClient() == idClient);
    }

    public Optional<Client> cautaClientDupaId(int idClient) {
        return clienti.stream()
                .filter(client -> client.getIdClient() == idClient)
                .findFirst();
    }

    public void adaugaPuncteFidelitate(int idClient, int puncte) {
        Optional<Client> clientOpt = cautaClientDupaId(idClient);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.adaugaPuncteFidelitate(puncte);
            System.out.println("Au fost adăugate " + puncte + " puncte de fidelitate clientului " + client.getNume() + ".");
        } else {
            System.out.println("Clientul cu ID " + idClient + " nu a fost gasit.");
        }
    }


    public void afiseazaClienti() {
        if (clienti.isEmpty()) {
            System.out.println("Nu exista clienti inregistrati.");
        } else {
            System.out.println("~~~ Lista Clienților ~~~");
            for (Client client : clienti) {
                System.out.println(client);
            }
        }
    }

    public void afiseazaClientiCuPuncteFidelitatePesteUnPrag(int prag) {
        List<Client> clientiFiltrati = clienti.stream()
                .filter(c -> c.getBonusFidelitate() > prag)
                .toList();

        if (clientiFiltrati.isEmpty()) {
            System.out.println("Nu exista clienti cu mai mult de " + prag + " puncte de fidelitate.");
        } else {
            System.out.println("Clienti cu mai mult de " + prag + " puncte de fidelitate:");
            clientiFiltrati.forEach(System.out::println);
        }
    }


    public void afiseazaIstoricComenzi(int idClient) {
        Optional<Client> clientOpt = cautaClientDupaId(idClient);
        if (clientOpt.isPresent()) {
            clientOpt.get().afiseazaIstoricComenzi();
        } else {
            System.out.println("Clientul cu ID " + idClient + " nu a fost gasit.");
        }
    }


    public boolean verificaClient(int idClient) {
        return clienti.stream().anyMatch(client -> client.getIdClient() == idClient);
    }

    public void createClient(Client client) {
        String sql = """
        INSERT INTO client (nume, bonus_fidelitate)
        VALUES (?, ?)
        RETURNING id_client
    """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getNume());
            stmt.setInt(2, client.getBonusFidelitate());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idGenerat = rs.getInt("id_client");

                Field idField = Client.class.getDeclaredField("idClient");
                idField.setAccessible(true);
                idField.setInt(client, idGenerat);

                clienti.add(client);

                System.out.println("Clientul a fost salvat în DB cu ID-ul: " + idGenerat);

                AuditService.getInstance().logActiune("createClient");
            }

        } catch (SQLException e) {
            System.err.println("Eroare la inserarea clientului: " + e.getMessage());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Eroare la setarea ID-ului clientului: " + e.getMessage());
        }
    }

    public boolean stergeClientBD(int id) {
        String sql = "DELETE FROM client WHERE id_client = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                clienti.removeIf(c -> c.getIdClient() == id);
                System.out.println("Clientul cu ID-ul " + id + " a fost sters.");
                return true;
            } else {
                System.out.println("Clientul cu ID-ul " + id + " nu a fost gasit în baza de date.");
            }

            AuditService.getInstance().logActiune("stergeClientBD");

        } catch (SQLException e) {
            System.out.println("Eroare la stergerea clientului: " + e.getMessage());
        }

        return false;
    }

    public boolean updateClient(int id, String atribut, String valoareNoua) {
        String sql = "UPDATE client SET " + atribut + " = ? WHERE id_client = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            switch (atribut) {
                case "nume" -> stmt.setString(1, valoareNoua);
                case "bonus_fidelitate" -> stmt.setInt(1, Integer.parseInt(valoareNoua));
                default -> {
                    System.out.println("Atribut necunoscut.");
                    return false;
                }
            }

            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                Optional<Client> clientOpt = cautaClientDupaId(id);
                clientOpt.ifPresent(client -> {
                    switch (atribut) {
                        case "nume" -> {
                            try {
                                Field f = Client.class.getDeclaredField("nume");
                                f.setAccessible(true);
                                f.set(client, valoareNoua);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                System.err.println("Eroare la actualizarea numelui in memorie: " + e.getMessage());
                            }
                        }
                        case "bonus_fidelitate" -> {
                            try {
                                Field f = Client.class.getDeclaredField("bonusFidelitate");
                                f.setAccessible(true);
                                f.setInt(client, Integer.parseInt(valoareNoua));
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                System.err.println("Eroare la actualizarea bonusului in memorie: " + e.getMessage());
                            }
                        }
                    }
                });

                System.out.println("Clientul a fost actualizat cu succes.");
                AuditService.getInstance().logActiune("updateClient");
                return true;
            } else {
                System.out.println("Clientul cu ID-ul " + id + " nu a fost gasit.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Eroare la actualizare: " + e.getMessage());
        }

        return false;
    }

    public void afiseazaClientiBD() {
        String sql = "SELECT id_client, nume, bonus_fidelitate FROM client";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Lista clienti:");
            while (rs.next()) {
                int id = rs.getInt("id_client");
                String nume = rs.getString("nume");
                int bonus = rs.getInt("bonus_fidelitate");
                System.out.println("ID: " + id + " | Nume: " + nume + " | Bonus: " + bonus);
            }

            AuditService.getInstance().logActiune("afiseazaClientiBD");

        } catch (SQLException e) {
            System.out.println("Eroare la afisarea clientilor: " + e.getMessage());
        }
    }

    public Optional<Client> cautaClientDupaIdDB(int id) {
        String sql = "SELECT id_client, nume, bonus_fidelitate FROM client WHERE id_client = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idClient = rs.getInt("id_client");
                String nume = rs.getString("nume");
                int bonus = rs.getInt("bonus_fidelitate");

                Client client = new Client(nume);
                return Optional.of(client);
            }

        } catch (SQLException e) {
            System.out.println("Eroare la cautarea clientului: " + e.getMessage());
        }

        return Optional.empty();
    }

    public boolean existaClientInDB(int idClient) {
        String sql = "SELECT 1 FROM client WHERE id_client = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // true dacă există

        } catch (SQLException e) {
            System.out.println("Eroare la verificarea clientului: " + e.getMessage());
            return false;
        }
    }

    public void afiseazaDetaliiClienti() {
        String sql = "SELECT id_client, nume, bonus_fidelitate FROM client";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("~~~ Detalii clienți din BD ~~~");

            boolean existaClienti = false;

            while (rs.next()) {
                existaClienti = true;
                int id = rs.getInt("id_client");
                String nume = rs.getString("nume");
                int bonus = rs.getInt("bonus_fidelitate");

                System.out.println("ID: " + id);
                System.out.println("Nume: " + nume);
                System.out.println("Puncte de fidelitate: " + bonus);

                AuditService.getInstance().logActiune("afiseazaDetaliiClienti");
            }

            if (!existaClienti) {
                System.out.println("Nu există clienți în baza de date.");
            }

        } catch (SQLException e) {
            System.out.println("Eroare la afișarea detaliilor clienților din BD: " + e.getMessage());
        }
    }

    public List<Client> getAllClienti() {
        List<Client> clienti = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_client");
                String nume = rs.getString("nume");
                int bonus = rs.getInt("bonus_fidelitate");

                Client client = new Client(nume);

                Field idField = Client.class.getDeclaredField("idClient");
                idField.setAccessible(true);
                idField.setInt(client, id);

                Field bonusField = Client.class.getDeclaredField("bonusFidelitate");
                bonusField.setAccessible(true);
                bonusField.setInt(client, bonus);

                clienti.add(client);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la citirea clienților din DB: " + e.getMessage());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Eroare la setarea campurilor clientului: " + e.getMessage());
        }

        return clienti;
    }



}
