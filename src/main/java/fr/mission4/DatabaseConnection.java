package fr.mission4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/webcaisse?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Laragon/XAMPP default
    private static final String PASSWORD = ""; // Laragon/XAMPP default

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
