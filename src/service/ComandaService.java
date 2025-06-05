package service;

import model.Client;
import model.Comanda;
import model.Produs;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
import java.sql.Statement;



public class ComandaService {

    private List<Comanda> comenzi;

    public ComandaService() {
        this.comenzi = getAllComenzi();
    }



    public Comanda plaseazaComanda(Client client, List<Produs> produse, List<Integer> cantitati) {
        if (produse.size() != cantitati.size()) {
            throw new IllegalArgumentException("Lista de produse si cantitati trebuie să aiba aceeasi dimensiune.");
        }

        Comanda comanda = new Comanda(client.getIdClient());

        for (int i = 0; i < produse.size(); i++) {
            comanda.adaugaProdus(produse.get(i), cantitati.get(i));
        }

        comenzi.add(comanda);
        client.adaugaComanda(comanda);

        System.out.println("Comanda a fost plasata cu succes: " + comanda);
        return comanda;
    }

    public void adaugaComanda(Comanda comanda) {
        comenzi.add(comanda);
    }


    public List<Comanda> filtreazaComenziDupaStatus(Comanda.StatusComanda status) {
        return comenzi.stream()
                .filter(c -> c.getStatus() == status)
                .toList();
    }

    public void modificaStatusComanda(int idComanda, Comanda.StatusComanda noulStatus) {
        Optional<Comanda> comandaOpt = cautaComandaDupaId(idComanda);
        if (comandaOpt.isPresent()) {
            comandaOpt.get().modificaStatus(noulStatus);
        } else {
            System.out.println("Comanda cu ID-ul " + idComanda + " nu a fost găsită.");
        }
    }


    public boolean finalizeazaComanda(int idComanda) {
        Optional<Comanda> comandaOpt = comenzi.stream()
                .filter(c -> c.getIdComanda() == idComanda)
                .findFirst();

        if (comandaOpt.isPresent()) {
            comandaOpt.get().finalizeazaComanda();
            return true;
        }

        System.out.println("Comanda cu ID-ul " + idComanda + " nu a fost găsită.");
        return false;
    }

    public void afiseazaToateComenzile() {
        if (comenzi.isEmpty()) {
            System.out.println("Nu există comenzi înregistrate.");
            return;
        }
        comenzi.forEach(System.out::println);
    }

    public List<Comanda> getComenziClient(Client client) {
        return comenzi.stream()
                .filter(c -> c.getIdClient() == client.getIdClient())
                .toList();
    }


    public Optional<Comanda> cautaComandaDupaId(int idComanda) {
        return comenzi.stream()
                .filter(c -> c.getIdComanda() == idComanda)
                .findFirst();
    }

    public boolean stergeComanda(int idComanda) {
        return comenzi.removeIf(c -> c.getIdComanda() == idComanda);
    }

    public void createComanda(Scanner scanner, ClientService clientService, ProdusService produsService) {
        System.out.println("Selecteaza un client din lista:");
        clientService.afiseazaClientiBD();

        System.out.print("Introdu id-ul clientului: ");
        int idClient = scanner.nextInt();
        scanner.nextLine();

        if (!clientService.cautaClientDupaIdDB(idClient).isPresent()) {
            System.out.println("Clientul nu a fost gasit.");
            return;
        }

        Comanda comanda = new Comanda(idClient);

        System.out.println("Selecteaza produse pentru comanda:");
        produsService.afiseazaProduseBD();
        Map<Map<String, Object>, Integer> produseSelectate = new HashMap<>();

        while (true) {
            System.out.print("Introdu id produs (sau 0 pentru a termina): ");
            int idProdus = scanner.nextInt();
            if (idProdus == 0) break;

            Optional<Map<String, Object>> produsOpt = produsService.cautaProdusDupaId(idProdus);

            if (produsOpt.isEmpty()) {
                System.out.println("Produs invalid.");
                continue;
            }

            System.out.print("Cantitate: ");
            int cantitate = scanner.nextInt();
            produseSelectate.put(produsOpt.get(), cantitate);
        }

        String insertComandaSql = "INSERT INTO comanda (id_client, data_plasare, status) VALUES (?, ?, ?) RETURNING id_comanda";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertComandaSql)) {

            stmt.setInt(1, comanda.getIdClient());
            stmt.setDate(2, Date.valueOf(comanda.getDataPlasare()));
            stmt.setString(3, comanda.getStatus().toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idComandaGenerat = rs.getInt("id_comanda");

                // Inseram in tabelul comanda_produs
                String insertCP = "INSERT INTO comanda_produs (id_comanda, id_produs, cantitate) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertCP)) {
                    for (Map.Entry<Map<String, Object>, Integer> entry : produseSelectate.entrySet()) {
                        Map<String, Object> produsMap = entry.getKey();
                        int idProdus = (int) produsMap.get("id");
                        int cantitate = entry.getValue();

                        ps.setInt(1, idComandaGenerat);
                        ps.setInt(2, idProdus);
                        ps.setInt(3, cantitate);
                        ps.executeUpdate();
                    }
                }

                AuditService.getInstance().logActiune("createComanda");

                System.out.println("Comanda a fost salvata cu ID: " + idComandaGenerat);
            }

        } catch (SQLException e) {
            System.out.println("Eroare la salvarea comenzii: " + e.getMessage());
        }
    }

    public boolean stergeComandaDinBD(int idComanda) {
        String deleteComandaProdusSql = "DELETE FROM comanda_produs WHERE id_comanda = ?";
        String deleteComandaSql = "DELETE FROM comanda WHERE id_comanda = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtCP = conn.prepareStatement(deleteComandaProdusSql)) {
                stmtCP.setInt(1, idComanda);
                stmtCP.executeUpdate();
            }


            try (PreparedStatement stmtC = conn.prepareStatement(deleteComandaSql)) {
                stmtC.setInt(1, idComanda);
                int affectedRows = stmtC.executeUpdate();

                if (affectedRows > 0) {
                    conn.commit();


                    stergeComanda(idComanda);

                    System.out.println("Comanda #" + idComanda + " a fost stearsa cu succes.");
                    AuditService.getInstance().logActiune("stergeComandaDinBD");
                    return true;
                } else {
                    conn.rollback();
                    System.out.println("Nu a fost gasita comanda cu ID-ul: " + idComanda);
                    return false;
                }

            }

        } catch (SQLException e) {
            System.out.println("Eroare la stergerea comenzii: " + e.getMessage());
            return false;
        }
    }

    public boolean updateComanda(int idComanda, String atribut, String valoareNoua) {
        String sql = "UPDATE comanda SET " + atribut + " = ? WHERE id_comanda = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            switch (atribut) {
                case "status" -> stmt.setString(1, valoareNoua.toUpperCase());
                case "data_plasare" -> stmt.setDate(1, Date.valueOf(valoareNoua));
                case "id_client" -> stmt.setInt(1, Integer.parseInt(valoareNoua));
                default -> {
                    System.out.println("Atribut necunoscut.");
                    return false;
                }
            }

            stmt.setInt(2, idComanda);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Comanda actualizata cu succes.");
                AuditService.getInstance().logActiune("updateComanda");
                return true;
            } else {
                System.out.println("Comanda nu a fost gasita.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Eroare la actualizare: " + e.getMessage());
        }

        return false;
    }

    public void afiseazaComenziBD() {
        String sql = "SELECT id_comanda, id_client, data_plasare, status FROM comanda";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("~~~ Lista comenzilor din BD ~~~");

            boolean existaComenzi = false;

            while (rs.next()) {
                existaComenzi = true;
                int idComanda = rs.getInt("id_comanda");
                int idClient = rs.getInt("id_client");
                Date dataPlasare = rs.getDate("data_plasare");
                String status = rs.getString("status");

                System.out.println("ID Comandă: " + idComanda);
                System.out.println("ID Client: " + idClient);
                System.out.println("Data Plasării: " + dataPlasare);
                System.out.println("Status: " + status);

                AuditService.getInstance().logActiune("afiseazaComenziBD");
            }

            if (!existaComenzi) {
                System.out.println("Nu există comenzi înregistrate în baza de date.");
            }

        } catch (SQLException e) {
            System.out.println("Eroare la afisarea comenzilor din BD: " + e.getMessage());
        }
    }

    public Map<Produs, Integer> getProdusePentruComanda(int idComanda) {
        Map<Produs, Integer> produse = new HashMap<>();
        String sql = "SELECT cp.id_produs, cp.cantitate FROM comanda_produs cp WHERE cp.id_comanda = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idComanda);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idProdus = rs.getInt("id_produs");
                    int cantitate = rs.getInt("cantitate");

                    Produs produs = null;

                    if ((produs = GenericReadService.getInstance().getTortById(idProdus, conn)) != null ||
                            (produs = GenericReadService.getInstance().getFursecById(idProdus, conn)) != null ||
                            (produs = GenericReadService.getInstance().getBomboanaById(idProdus, conn)) != null ||
                            (produs = GenericReadService.getInstance().getPatiserieById(idProdus, conn)) != null) {

                        produse.put(produs, cantitate);
                    } else {
                        System.err.println("Produs necunoscut cu ID-ul: " + idProdus);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Eroare la extragerea produselor pentru comanda: " + e.getMessage());
        }

        return produse;
    }


    public List<Comanda> getAllComenzi() {
        List<Comanda> comenzi = new ArrayList<>();
        String sql = "SELECT * FROM comanda";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idComanda = rs.getInt("id_comanda");
                int idClient = rs.getInt("id_client");
                LocalDate dataPlasare = rs.getDate("data_plasare").toLocalDate();
                String statusString = rs.getString("status");

                Comanda comanda = new Comanda(idClient);

                Field idField = Comanda.class.getDeclaredField("idComanda");
                idField.setAccessible(true);
                idField.setInt(comanda, idComanda);

                Field dataField = Comanda.class.getDeclaredField("dataPlasare");
                dataField.setAccessible(true);
                dataField.set(comanda, dataPlasare);

                Field statusField = Comanda.class.getDeclaredField("status");
                statusField.setAccessible(true);
                statusField.set(comanda, Comanda.StatusComanda.valueOf(statusString));

                Map<Produs, Integer> produseComanda = getProdusePentruComanda(idComanda);
                Field produseField = Comanda.class.getDeclaredField("listaProduse");
                produseField.setAccessible(true);
                produseField.set(comanda, produseComanda);

                comenzi.add(comanda);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la extragerea comenzilor: " + e.getMessage());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Eroare la setarea câmpurilor comenzii: " + e.getMessage());
        }

        return comenzi;
    }




}
