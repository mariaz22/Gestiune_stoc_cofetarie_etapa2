package service;

import model.*;
import java.sql.*;

public class GenericWriteService {
    private static GenericWriteService instance;

    private GenericWriteService() {}

    public static GenericWriteService getInstance() {
        if (instance == null) {
            instance = new GenericWriteService();
        }
        return instance;
    }

    public void insertSubtipProdus(Produs produs, Connection conn) throws SQLException {
        if (produs instanceof Tort tort) {
            insertTort(tort, conn);
        } else if (produs instanceof Fursec fursec) {
            insertFursec(fursec, conn);
        } else if (produs instanceof Bomboana bomboana) {
            insertBomboana(bomboana, conn);
        } else if (produs instanceof Patiserie patiserie) {
            insertPatiserie(patiserie, conn);
        }
    }

    private void insertTort(Tort tort, Connection conn) throws SQLException {
        String sql = "INSERT INTO tort (id, tip_blat, tip_glazura, eveniment, portii) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tort.getId());
            stmt.setString(2, tort.getTipBlat().name());
            stmt.setString(3, tort.getTipGlazura().name());
            stmt.setString(4, tort.getEveniment().name());
            stmt.setInt(5, tort.getPortii());
            stmt.executeUpdate();
        }
    }

    private void insertFursec(Fursec fursec, Connection conn) throws SQLException {
        String sql = "INSERT INTO fursec (id, aroma, tip_umplutura, glazurat) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fursec.getId());
            stmt.setString(2, fursec.getAroma().name());
            stmt.setString(3, fursec.getTipUmplutura().name());
            stmt.setBoolean(4, fursec.isGlazurat());
            stmt.executeUpdate();
        }
    }

    private void insertBomboana(Bomboana b, Connection conn) throws SQLException {
        String sql = "INSERT INTO bomboana (id, tip_ciocolata, umplutura, contine_alcool) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, b.getId());
            stmt.setString(2, b.getTipCiocolata().name());
            stmt.setString(3, b.getUmplutura());
            stmt.setBoolean(4, b.isContineAlcool());
            stmt.executeUpdate();
        }
    }

    private void insertPatiserie(Patiserie p, Connection conn) throws SQLException {
        String sql = "INSERT INTO patiserie (id, tip, timp_coacere, este_congelata) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getTipPatiserie().name());
            stmt.setInt(3, p.getTimpCoacere());
            stmt.setBoolean(4, p.isEsteCongelata());
            stmt.executeUpdate();
        }
    }
}
