package service;

import model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import service.DatabaseConnection;
import java.sql.*;



public class ProdusService {
    private List<Produs> produse = new ArrayList<>();

    public void adaugaProdus(Produs produs) {
        produse.add(produs);
    }


    public ProdusService() {
        this.produse = getAllProduse();
    }



    public Optional<Map<String, Object>> cautaProdusDupaId(int idCautat) {
        List<Map<String, Object>> produse = incarcaProduseDinBD();

        for (Map<String, Object> produs : produse) {
            int id = (int) produs.get("id");
            if (id == idCautat) {
                return Optional.of(produs);
            }
        }

        return Optional.empty();
    }

    public Optional<Produs> cautaProdusDupaId1(int id) {
        return produse.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }


    public List<Produs> getProduseExpirate() {
        return produse.stream()
                .filter(Produs::isExpirat)
                .collect(Collectors.toList());
    }

    public List<Produs> getProduseLowStock(int prag) {
        return produse.stream()
                .filter(p -> p.getCantitate() < prag)
                .collect(Collectors.toList());
    }

    public List<Produs> recomandaProduse(boolean preferaVegan) {
        return produse.stream()
                .filter(p -> p.isVegan() == preferaVegan)
                .sorted(Comparator.comparingDouble(Produs::getPret).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }


    public List<Produs> cautaDupaIngredient(String numeIngredient) {
        return produse.stream()
                .filter(p -> p.contineIngredient(numeIngredient))
                .collect(Collectors.toList());
    }

    public List<Produs> getToateProdusele() {
        return produse;
    }

    public boolean ingredienteleSuntInStoc(Produs produs, List<Ingredient> stoc) {
        return produs.getIngrediente().stream().allMatch(
                ingredientNecesar -> stoc.stream().anyMatch(
                        stocIngredient -> stocIngredient.getNume().equalsIgnoreCase(ingredientNecesar.getNume()) &&
                                stocIngredient.getCantitate() >= ingredientNecesar.getCantitate()
                )
        );
    }

    public SortedSet<Produs> sorteazaDupaCalorii(boolean crescator) {
        Comparator<Produs> comparator = crescator ?
                Comparator.comparingInt(Produs::getCalorii) :
                Comparator.comparingInt(Produs::getCalorii).reversed();

        SortedSet<Produs> setSortat = new TreeSet<>(comparator);
        setSortat.addAll(getToateProdusele());
        return setSortat;
    }


    public List<Produs> getProduseVegane() {
        return produse.stream()
                .filter(Produs::isVegan)
                .collect(Collectors.toList());
    }


    public void createProdus(Produs produs) {
        String sql = """
        INSERT INTO produs (nume, pret, cantitate, data_expirare, gramaj, calorii, categorie, este_vegan)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, produs.getNume());
                stmt.setDouble(2, produs.getPret());
                stmt.setInt(3, produs.getCantitate());
                stmt.setDate(4, Date.valueOf(produs.getDataExpirare()));
                stmt.setDouble(5, produs.getGramaj());
                stmt.setInt(6, produs.getCalorii());
                stmt.setString(7, produs.getCategorie().name());
                stmt.setBoolean(8, produs.isVegan());

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        produs.setId(generatedId);
                    } else {
                        throw new SQLException("Crearea produsului a eșuat, nu s-a generat niciun ID.");
                    }
                }
            }

            GenericWriteService.getInstance().insertSubtipProdus(produs, conn);

            conn.commit();
            System.out.println("Produsul a fost inserat cu succes.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Eroare la inserarea produsului.");
        }
    }


    public boolean stergeProdus(int id) {
        String sql = "DELETE FROM produs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                AuditService.getInstance().logActiune("stergeProdus");
                produse.removeIf(p -> p.getId() == id);
                System.out.println("Produsul cu id " + id + " a fost șters.");
                return true;
            } else {
                System.out.println("Produsul cu id " + id + " nu a fost gasit.");
            }

        } catch (SQLException e) {
            System.out.println("Eroare la ștergerea produsului: " + e.getMessage());
        }

        return false;
    }

    public boolean updateProdus(int id, String atribut, String valoareNoua) {
        String sql = "UPDATE produs SET " + atribut + " = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            switch (atribut) {
                case "nume" -> stmt.setString(1, valoareNoua);
                case "pret" -> stmt.setDouble(1, Double.parseDouble(valoareNoua));
                case "cantitate" -> stmt.setInt(1, Integer.parseInt(valoareNoua));
                case "data_expirare" -> stmt.setDate(1, Date.valueOf(valoareNoua));
                case "gramaj" -> stmt.setDouble(1, Double.parseDouble(valoareNoua));
                case "calorii" -> stmt.setInt(1, Integer.parseInt(valoareNoua));
                case "categorie" -> stmt.setString(1, valoareNoua);
                case "este_vegan" -> stmt.setBoolean(1, Boolean.parseBoolean(valoareNoua));
                default -> {
                    System.out.println("Atribut necunoscut.");
                    return false;
                }
            }

            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                AuditService.getInstance().logActiune("updateProdus");
                Optional<Produs> produsOpt = cautaProdusDupaId1(id);
                produsOpt.ifPresent(p -> {
                    switch (atribut) {
                        case "nume" -> p.setNume(valoareNoua);
                        case "pret" -> p.setPret(Double.parseDouble(valoareNoua));
                        case "cantitate" -> p.setCantitate(Integer.parseInt(valoareNoua));
                        case "data_expirare" -> p.setDataExpirare(LocalDate.parse(valoareNoua));
                        case "gramaj" -> p.setGramaj(Double.parseDouble(valoareNoua));
                        case "calorii" -> p.setCalorii(Integer.parseInt(valoareNoua));
                        case "categorie" -> p.setCategorie(Categorie.valueOf(valoareNoua.toUpperCase()));
                        case "este_vegan" -> p.setVegan(Boolean.parseBoolean(valoareNoua));
                    }
                });

                System.out.println("Produs actualizat cu succes.");
                return true;
            } else {
                System.out.println("Produsul nu a fost gasit.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Eroare la actualizare: " + e.getMessage());
        }
        return false;
    }

    public List<Map<String, Object>> incarcaProduseDinBD() {
        List<Map<String, Object>> produse = new ArrayList<>();
        String sql = "SELECT * FROM produs";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> produs = new HashMap<>();
                produs.put("id", rs.getInt("id"));
                produs.put("nume", rs.getString("nume"));
                produs.put("pret", rs.getDouble("pret"));
                produs.put("cantitate", rs.getInt("cantitate"));
                produs.put("data_expirare", rs.getDate("data_expirare").toLocalDate());
                produs.put("gramaj", rs.getDouble("gramaj"));
                produs.put("calorii", rs.getInt("calorii"));
                produs.put("categorie", rs.getString("categorie"));
                produs.put("este_vegan", rs.getBoolean("este_vegan"));

                produse.add(produs);
            }

        } catch (SQLException e) {
            System.out.println("Eroare la încărcarea produselor: " + e.getMessage());
        }

        return produse;
    }


    public void afiseazaProduseBD() {
        String sql = "SELECT * FROM produs";
        AuditService.getInstance().logActiune("afiseazaProduseBD");

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Lista produse:");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                double pret = rs.getDouble("pret");
                int cantitate = rs.getInt("cantitate");
                LocalDate dataExp = rs.getDate("data_expirare").toLocalDate();
                double gramaj = rs.getDouble("gramaj");
                int calorii = rs.getInt("calorii");
                String categorie = rs.getString("categorie");
                boolean esteVegan = rs.getBoolean("este_vegan");

                System.out.println("ID: " + id +
                        " | Nume: " + nume +
                        " | Pret: " + pret +
                        " RON | Cantitate: " + cantitate +
                        " | Expira: " + dataExp +
                        " | Gramaj: " + gramaj + "g" +
                        " | Calorii: " + calorii +
                        " | Categorie: " + categorie +
                        " | Vegan: " + (esteVegan ? "Da" : "Nu"));
            }

        } catch (SQLException e) {
            System.out.println("Eroare la afisarea produselor: " + e.getMessage());
        }
    }

    public List<Ingredient> getIngredientePentruProdus(int produsId, Connection conn) throws SQLException {
        List<Ingredient> ingrediente = new ArrayList<>();

        String sql = "SELECT i.* FROM produs_ingredient pi " +
                "JOIN ingredient i ON pi.ingredient_id = i.id " +
                "WHERE pi.produs_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, produsId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nume = rs.getString("nume");
                    double cantitate = rs.getDouble("cantitate");
                    String unitate = rs.getString("unitate_masura");
                    LocalDate dataExp = rs.getDate("data_expirare").toLocalDate();
                    double pretUnit = rs.getDouble("pret_per_unitate");

                    Ingredient ing;

                    ing = new IngredientDeBaza(nume, cantitate, UnitateMasura.valueOf(unitate.toUpperCase()), dataExp, pretUnit);

                    ing.setId(id);
                    ingrediente.add(ing);
                }
            }
        }

        return ingrediente;
    }


    public List<Produs> getAllProduse() {
        List<Produs> produse = new ArrayList<>();
        String sql = "SELECT * FROM produs";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                double pret = rs.getDouble("pret");
                int cantitate = rs.getInt("cantitate");
                LocalDate dataExp = rs.getDate("data_expirare").toLocalDate();
                double gramaj = rs.getDouble("gramaj");
                int calorii = rs.getInt("calorii");
                String categorieStr = rs.getString("categorie");
                boolean esteVegan = rs.getBoolean("este_vegan");

                Categorie categorie = Categorie.valueOf(categorieStr.toUpperCase());
                List<Ingredient> ingrediente = getIngredientePentruProdus(id, conn); // dacă ai o funcție pentru asta

                switch (categorie) {
                    case TORT -> {
                        Tort tort = GenericReadService.getInstance().getTortById(id, conn);
                        if (tort != null) produse.add(tort);
                    }
                    case FURSEC -> {
                        Fursec fursec = GenericReadService.getInstance().getFursecById(id, conn);
                        if (fursec != null) produse.add(fursec);
                    }
                    case BOMBOANA -> {
                        Bomboana b = GenericReadService.getInstance().getBomboanaById(id, conn);
                        if (b != null) produse.add(b);
                    }
                    case PATISERIE -> {
                        Patiserie p = GenericReadService.getInstance().getPatiserieById(id, conn);
                        if (p != null) produse.add(p);
                    }
                    default -> {
                        System.out.println("Categorie nesuportată pentru produsul cu ID " + id);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Eroare la extragerea produselor: " + e.getMessage());
        }

        return produse;
    }



    private static final GenericReadService readService = GenericReadService.getInstance();

    public void afiseazaProduseDupaTip(String tipProdus) {
        switch (tipProdus.toUpperCase()) {
            case "TORT" -> {
                List<Tort> torturi = readService.getAllTorturi();
                if (torturi.isEmpty()) System.out.println("Nu există torturi.");
                else torturi.forEach(System.out::println);
            }
            case "FURSEC" -> {
                List<Fursec> fursecuri = readService.getAllFursecuri();
                if (fursecuri.isEmpty()) System.out.println("Nu există fursecuri.");
                else fursecuri.forEach(System.out::println);
            }
            case "BOMBOANA" -> {
                List<Bomboana> bomboane = readService.getAllBomboane();
                if (bomboane.isEmpty()) System.out.println("Nu există bomboane.");
                else bomboane.forEach(System.out::println);
            }
            case "PATISERIE" -> {
                List<Patiserie> patiserii = readService.getAllPatiserii();
                if (patiserii.isEmpty()) System.out.println("Nu există produse de patiserie.");
                else patiserii.forEach(System.out::println);
            }
            default -> System.out.println("Tip de produs necunoscut.");
        }
    }


}
