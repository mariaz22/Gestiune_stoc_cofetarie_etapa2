package service;
import service.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexiunea la baza de date a fost realizată cu succes!");
            } else {
                System.out.println("Nu s-a putut realiza conexiunea.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
