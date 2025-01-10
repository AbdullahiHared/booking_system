package org.example.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String URL = "jdbc:mysql://localhost:3306/booking_system";
    private static String USER = "root";
    private static String PASSWORD = "password";

    public static Connection getConnection () {

    }

}
