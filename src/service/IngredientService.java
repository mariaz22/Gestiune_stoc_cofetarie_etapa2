package service;

import model.Ingredient;
import model.IngredientDeBaza;
import model.IngredientSpecial;
import model.UnitateMasura;
import model.CerereAprovizionare;
import model.Distribuitor;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;




import java.util.List;
import java.util.stream.Collectors;

public class IngredientService {

    private List<Ingredient> ingrediente;

    public IngredientService() {
        this.ingrediente = getAllIngrediente();
    }



    public void adaugaIngredient(Ingredient ingredient) {
        ingrediente.add(ingredient);
        //System.out.println("Ingredientul " + ingredient.getNume() + " a fost adăugat cu succes.");
    }

    public List<Ingredient> getIngredienteExpirate() {
        return ingrediente.stream()
                .filter(Ingredient::esteExpirat)
                .collect(Collectors.toList());
    }

    public List<Ingredient> getIngredienteCritice(double prag) {
        return ingrediente.stream()
                .filter(ing -> ing.getCantitate() < prag)
                .collect(Collectors.toList());
    }

    public List<CerereAprovizionare> genereazaCereriAprovizionare(double pragCritic, Distribuitor distribuitorImplicit) {
        List<CerereAprovizionare> cereri = new ArrayList<>();

        for (Ingredient ingredient : ingrediente) {
            if (ingredient.getCantitate() < pragCritic) {
                double cantitateNecesar = pragCritic - ingredient.getCantitate();

                CerereAprovizionare cerere = new CerereAprovizionare(
                        ingredient,
                        cantitateNecesar,
                        distribuitorImplicit
                );
                cereri.add(cerere);
                System.out.println("\n[GENERAT] " + cerere);
            }
        }

        if (cereri.isEmpty()) {
            System.out.println("\n✔️ Nu au fost identificate ingrediente sub pragul critic.");
        }

        return cereri;
    }


    public double calculeazaValoareTotalaStoc() {
        return ingrediente.stream()
                .mapToDouble(Ingredient::getValoareTotala)
                .sum();
    }

    public List<Ingredient> getToateIngredientele() {
        return ingrediente;
    }

    public void afiseazaIngrediente() {
        if (ingrediente.isEmpty()) {
            System.out.println("Nu există ingrediente în stoc.");
        } else {
            System.out.println("=== Lista Ingredientelor ===");
            for (Ingredient ing : ingrediente) {
                System.out.println(ing);
            }
        }
    }

    public boolean stergeIngredient(String nume) {
        return ingrediente.removeIf(ing -> ing.getNume().equalsIgnoreCase(nume));
    }

    public void afiseazaIngredienteSortateDupaStoc() {
        if (ingrediente.isEmpty()) {
            System.out.println("Nu exista ingrediente in stoc.");
        } else {
            System.out.println("~~~ Lista Ingredientelor sortate după stoc ~~~");
            List<Ingredient> ingredienteSortate = ingrediente.stream()
                    .sorted((ing1, ing2) -> Double.compare(ing1.getCantitate(), ing2.getCantitate()))
                    .collect(Collectors.toList());

            for (Ingredient ing : ingredienteSortate) {
                System.out.println(ing);
            }
        }
    }

    public void createIngredient(Ingredient ingredient) {
        String insertSQL = """
        INSERT INTO ingredient (nume, cantitate, unitate_masura, data_expirare, pret_per_unitate)
        VALUES (?, ?, ?, ?, ?)
        RETURNING id
        """;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.prepareStatement(insertSQL);

            stmt.setString(1, ingredient.getNume());
            stmt.setDouble(2, ingredient.getCantitate());
            stmt.setString(3, ingredient.getUnitateMasura().name());
            stmt.setDate(4, Date.valueOf(ingredient.getDataExpirare()));
            stmt.setDouble(5, ingredient.getPretPerUnitate());

            rs = stmt.executeQuery();
            if (rs.next()) {
                int idGenerat = rs.getInt(1);
                AuditService.getInstance().logActiune("createIngredient");
                System.out.println("Ingredient adaugat cu ID: " + idGenerat);

                if (ingredient instanceof IngredientSpecial special) {
                    insertIngredientSpecial(special, idGenerat, conn);
                }

                if (ingredient instanceof IngredientDeBaza deBaza) {
                    deBaza.setId(idGenerat);
                    insertIngredientDeBaza(deBaza, conn);
                }
            }

        } catch (SQLException e) {
            System.err.println("Eroare la inserarea ingredientului: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertIngredientSpecial(IngredientSpecial special, int idIngredient, Connection conn) throws SQLException {
        String sql = "INSERT INTO ingredientspecial (id, este_produs_intern, timp_preparare) VALUES (?, ?, ?)";
        AuditService.getInstance().logActiune("insertIngredientSpecial");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIngredient);
            stmt.setBoolean(2, special.getEsteProdusIntern());
            stmt.setInt(3, special.getTimpPreparare());
            stmt.executeUpdate();
        }
    }

    private void insertIngredientDeBaza(IngredientDeBaza deBaza, Connection conn) throws SQLException {
        String sql = "INSERT INTO ingredientdebaza (id) VALUES (?)";
        AuditService.getInstance().logActiune("insertIngredientDeBaza");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, deBaza.getId());
            stmt.executeUpdate();
        }
    }

    public boolean stergeIngredient(int id) {
        String sql = "DELETE FROM ingredient WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Ingredient cu ID " + id + " a fost șters din baza de date.");
                AuditService.getInstance().logActiune("stergeIngredient");
                return true;
            } else {
                System.out.println("Niciun ingredient gasit cu ID-ul " + id);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Eroare la ștergerea ingredientului: " + e.getMessage());
            return false;
        }
    }

    public boolean updateIngredient(int id, String atribut, String valoareNoua) {
        String sql = "UPDATE ingredient SET " + atribut + " = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            switch (atribut) {
                case "nume" -> stmt.setString(1, valoareNoua);
                case "cantitate" -> stmt.setDouble(1, Double.parseDouble(valoareNoua));
                case "unitate_masura" -> stmt.setString(1, valoareNoua); // Trebuie să fie valid din enum!
                case "data_expirare" -> stmt.setDate(1, Date.valueOf(valoareNoua));
                case "pret_per_unitate" -> stmt.setDouble(1, Double.parseDouble(valoareNoua));
                default -> {
                    System.out.println("Atribut necunoscut.");
                    return false;
                }
            }

            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                Optional<Ingredient> ingOpt = ingrediente.stream()
                        .filter(i -> i.getId() == id)
                        .findFirst();

                ingOpt.ifPresent(ing -> {
                    switch (atribut) {
                        case "nume" -> ing.setNume(valoareNoua);
                        case "cantitate" -> ing.setCantitate(Double.parseDouble(valoareNoua));
                        case "unitate_masura" -> ing.setUnitateMasura(UnitateMasura.valueOf(valoareNoua));
                        case "data_expirare" -> ing.setDataExpirare(LocalDate.parse(valoareNoua));
                        case "pret_per_unitate" -> ing.setPretPerUnitate(Double.parseDouble(valoareNoua));
                    }

                    AuditService.getInstance().logActiune("updateIngredient");
                });

                System.out.println("Ingredient actualizat cu succes.");
                return true;
            } else {
                System.out.println("Ingredientul nu a fost gasit.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Eroare la actualizare: " + e.getMessage());
        }

        return false;
    }

    public void afiseazaIngredienteBD() {
        String sql = "SELECT * FROM ingredient";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("~~~ Lista Ingredientelor din BD ~~~");

            while (rs.next()) {
                AuditService.getInstance().logActiune("afiseazaIngredienteBD");
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                double cantitate = rs.getDouble("cantitate");
                String unitateMasura = rs.getString("unitate_masura");
                LocalDate dataExpirare = rs.getDate("data_expirare").toLocalDate();
                double pretPerUnitate = rs.getDouble("pret_per_unitate");

                System.out.println("ID: " + id +
                        " | Nume: " + nume +
                        " | Cantitate: " + cantitate + " " + unitateMasura +
                        " | Expira: " + dataExpirare +
                        " | Pret/unitate: " + pretPerUnitate + " RON");
            }

        } catch (SQLException e) {
            System.out.println("Eroare la afisarea ingredientelor din baza de date: " + e.getMessage());
        }
    }

    public List<Ingredient> getAllIngrediente() {
        List<Ingredient> ingrediente = new ArrayList<>();
        String sql = "SELECT * FROM ingredient";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                double cantitate = rs.getDouble("cantitate");
                String unitate = rs.getString("unitate_masura");
                LocalDate dataExpirare = rs.getDate("data_expirare").toLocalDate();
                double pretPerUnitate = rs.getDouble("pret_per_unitate");

                Ingredient ingredient;

                ingredient = new IngredientDeBaza(nume, cantitate, UnitateMasura.valueOf(unitate.toUpperCase()), dataExpirare, pretPerUnitate);

                ingredient.setId(id);
                ingrediente.add(ingredient);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la extragerea ingredientelor: " + e.getMessage());
        }

        return ingrediente;
    }


}
