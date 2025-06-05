package service;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GenericReadService {
    private static GenericReadService instance;


    private GenericReadService() {}

    public static GenericReadService getInstance() {
        if (instance == null) {
            instance = new GenericReadService();
        }
        return instance;
    }

    public List<Tort> getAllTorturi() {
        List<Tort> torturi = new ArrayList<>();
        String sql = """
            SELECT p.*, t.tip_blat, t.tip_glazura, t.eveniment, t.portii
            FROM produs p
            JOIN tort t ON p.id = t.id
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tort tort = new Tort(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getDouble("pret"),
                        rs.getInt("cantitate"),
                        new ArrayList<>(),
                        rs.getDate("data_expirare").toLocalDate(),
                        rs.getDouble("gramaj"),
                        rs.getInt("calorii"),
                        Categorie.valueOf(rs.getString("categorie")),
                        rs.getBoolean("este_vegan"),
                        Tort.TipBlat.valueOf(rs.getString("tip_blat")),
                        Tort.TipGlazura.valueOf(rs.getString("tip_glazura")),
                        Tort.Eveniment.valueOf(rs.getString("eveniment")),
                        rs.getInt("portii")
                );
                torturi.add(tort);
            }

        } catch (SQLException e) {
            System.out.println("Eroare la citirea torturilor: " + e.getMessage());
        }

        return torturi;
    }

    public List<Fursec> getAllFursecuri() {
        List<Fursec> fursecuri = new ArrayList<>();
        String sql = """
            SELECT p.*, f.aroma, f.tip_umplutura, f.glazurat
            FROM produs p
            JOIN fursec f ON p.id = f.id
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fursec fursec = new Fursec(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getDouble("pret"),
                        rs.getInt("cantitate"),
                        new ArrayList<>(),
                        rs.getDate("data_expirare").toLocalDate(),
                        rs.getDouble("gramaj"),
                        rs.getInt("calorii"),
                        Categorie.valueOf(rs.getString("categorie")),
                        rs.getBoolean("este_vegan"),
                        Fursec.Aroma.valueOf(rs.getString("aroma")),
                        Fursec.TipUmplutura.valueOf(rs.getString("tip_umplutura")),
                        rs.getBoolean("glazurat")
                );
                fursecuri.add(fursec);
            }

        } catch (SQLException e) {
            System.out.println("Eroare la citirea fursecurilor: " + e.getMessage());
        }

        return fursecuri;
    }

    public List<Bomboana> getAllBomboane() {
        List<Bomboana> bomboane = new ArrayList<>();
        String sql = """
            SELECT p.*, b.tip_ciocolata, b.umplutura, b.contine_alcool
            FROM produs p
            JOIN bomboana b ON p.id = b.id
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bomboana bomboana = new Bomboana(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getDouble("pret"),
                        rs.getInt("cantitate"),
                        new ArrayList<>(),
                        rs.getDate("data_expirare").toLocalDate(),
                        rs.getDouble("gramaj"),
                        rs.getInt("calorii"),
                        Categorie.valueOf(rs.getString("categorie")),
                        rs.getBoolean("este_vegan"),
                        Bomboana.TipCiocolata.valueOf(rs.getString("tip_ciocolata")),
                        rs.getString("umplutura"),
                        rs.getBoolean("contine_alcool")
                );
                bomboane.add(bomboana);
            }

        } catch (SQLException e) {
            System.out.println("Eroare la citirea bomboanelor: " + e.getMessage());
        }

        return bomboane;
    }

    public List<Patiserie> getAllPatiserii() {
        List<Patiserie> patiserii = new ArrayList<>();
        String sql = """
            SELECT p.*, pat.tip, pat.timp_coacere, pat.este_congelata
            FROM produs p
            JOIN patiserie pat ON p.id = pat.id
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patiserie patiserie = new Patiserie(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getDouble("pret"),
                        rs.getInt("cantitate"),
                        new ArrayList<>(),
                        rs.getDate("data_expirare").toLocalDate(),
                        rs.getDouble("gramaj"),
                        rs.getInt("calorii"),
                        Categorie.valueOf(rs.getString("categorie")),
                        rs.getBoolean("este_vegan"),
                        Patiserie.TipPatiserie.valueOf(rs.getString("tip")),
                        rs.getInt("timp_coacere"),
                        rs.getBoolean("este_congelata")
                );
                patiserii.add(patiserie);
            }

        } catch (SQLException e) {
            System.out.println("Eroare la citirea produselor de patiserie: " + e.getMessage());
        }

        return patiserii;
    }

    public List<Ingredient> getIngredientePentruProdus_(int produsId, Connection conn) throws SQLException {
        List<Ingredient> ingrediente = new ArrayList<>();

        String sql = "SELECT i.* FROM produs_ingredient pi " +
                "JOIN ingredient i ON pi.ingredient_id = i.id " +
                "WHERE pi.produs_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, produsId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_ingredient");
                    String nume = rs.getString("nume");
                    double cantitate = rs.getDouble("cantitate");
                    String unitate = rs.getString("unitate_masura");
                    LocalDate dataExp = rs.getDate("data_expirare").toLocalDate();
                    double pretUnit = rs.getDouble("pret_per_unitate");
                    String tip = rs.getString("tip");

                    Ingredient ing;

                    ing = new IngredientDeBaza(nume, cantitate, UnitateMasura.valueOf(unitate.toUpperCase()), dataExp, pretUnit);

                    ing.setId(id);
                    ingrediente.add(ing);
                }
            }
        }

        return ingrediente;
    }


    public Tort getTortById(int id, Connection conn) throws SQLException {
        String sql = """
        SELECT p.*, t.tip_blat, t.tip_glazura, t.eveniment, t.portii
        FROM produs p
        JOIN tort t ON p.id = t.id
        WHERE p.id = ?
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Tort(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getDouble("pret"),
                            rs.getInt("cantitate"),
                            getIngredientePentruProdus_(id, conn),
                            rs.getDate("data_expirare").toLocalDate(),
                            rs.getDouble("gramaj"),
                            rs.getInt("calorii"),
                            Categorie.valueOf(rs.getString("categorie")),
                            rs.getBoolean("este_vegan"),
                            Tort.TipBlat.valueOf(rs.getString("tip_blat")),
                            Tort.TipGlazura.valueOf(rs.getString("tip_glazura")),
                            Tort.Eveniment.valueOf(rs.getString("eveniment")),
                            rs.getInt("portii")
                    );
                }
            }
        }
        return null;
    }

    public Fursec getFursecById(int id, Connection conn) throws SQLException {
        String sql = """
        SELECT p.*, f.aroma, f.tip_umplutura, f.glazurat
        FROM produs p
        JOIN fursec f ON p.id = f.id
        WHERE p.id = ?
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Fursec(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getDouble("pret"),
                            rs.getInt("cantitate"),
                            getIngredientePentruProdus_(id, conn),
                            rs.getDate("data_expirare").toLocalDate(),
                            rs.getDouble("gramaj"),
                            rs.getInt("calorii"),
                            Categorie.valueOf(rs.getString("categorie")),
                            rs.getBoolean("este_vegan"),
                            Fursec.Aroma.valueOf(rs.getString("aroma")),
                            Fursec.TipUmplutura.valueOf(rs.getString("tip_umplutura")),
                            rs.getBoolean("glazurat")
                    );
                }
            }
        }
        return null;
    }

    public Bomboana getBomboanaById(int id, Connection conn) throws SQLException {
        String sql = """
        SELECT p.*, b.tip_ciocolata, b.umplutura, b.contine_alcool
        FROM produs p
        JOIN bomboana b ON p.id = b.id
        WHERE p.id = ?
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Bomboana(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getDouble("pret"),
                            rs.getInt("cantitate"),
                            getIngredientePentruProdus_(id, conn),
                            rs.getDate("data_expirare").toLocalDate(),
                            rs.getDouble("gramaj"),
                            rs.getInt("calorii"),
                            Categorie.valueOf(rs.getString("categorie")),
                            rs.getBoolean("este_vegan"),
                            Bomboana.TipCiocolata.valueOf(rs.getString("tip_ciocolata")),
                            rs.getString("umplutura"),
                            rs.getBoolean("contine_alcool")
                    );
                }
            }
        }
        return null;
    }

    public Patiserie getPatiserieById(int id, Connection conn) throws SQLException {
        String sql = """
        SELECT p.*, pat.tip, pat.timp_coacere, pat.este_congelata
        FROM produs p
        JOIN patiserie pat ON p.id = pat.id
        WHERE p.id = ?
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Patiserie(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getDouble("pret"),
                            rs.getInt("cantitate"),
                            getIngredientePentruProdus_(id, conn),
                            rs.getDate("data_expirare").toLocalDate(),
                            rs.getDouble("gramaj"),
                            rs.getInt("calorii"),
                            Categorie.valueOf(rs.getString("categorie")),
                            rs.getBoolean("este_vegan"),
                            Patiserie.TipPatiserie.valueOf(rs.getString("tip")),
                            rs.getInt("timp_coacere"),
                            rs.getBoolean("este_congelata")
                    );
                }
            }
        }
        return null;
    }




}
