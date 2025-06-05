package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/baza_de_date_cofetarie";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";

    private DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectat la baza de date");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Eroare la conectarea la baza de date: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null)
            instance = new DatabaseConnection();
        return instance;
    }

    public  Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la obținerea conexiunii: " + e.getMessage());
        }
        return connection;
    }
}
