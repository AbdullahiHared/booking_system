package org.example.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String URL = "jdbc:mysql://localhost:3306/booking_system";
    private static String USER = "root";
    private static String PASSWORD = "password";

    public static Connection getConnection () {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        return null;

    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to mysql database successfully");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

}
