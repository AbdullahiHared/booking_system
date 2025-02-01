package org.example.database;

import java.sql.Connection;
import java.sql.Statement;

public class SchemaInitializer {

    public static void initializeSchema(Connection connection) {
        String[] schemaSQL = {
                "CREATE TABLE IF NOT EXISTS Customers (" +
                        "customer_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "customer_name VARCHAR(100) NOT NULL, " +
                        "birth_date DATE NOT NULL, " +
                        "email VARCHAR(255) NOT NULL UNIQUE, " +
                        "password VARCHAR(255) NOT NULL" +
                        ");",

                "CREATE TABLE IF NOT EXISTS Bookings (" +
                        "booking_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "customer_id INT NOT NULL, " +
                        "seat_number VARCHAR(10) NOT NULL, " +
                        "ticket_type ENUM('ADULT', 'CHILD') NOT NULL, " +
                        "booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE, " +
                        ");",

                "CREATE TABLE IF NOT EXISTS booked_seats (" +
                        "booked_seat_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "seat_number VARCHAR(255) NOT NULL UNIQUE" +
                        ");"
        };

        try (Statement statement = connection.createStatement()) {
            for (String sql : schemaSQL) {
                statement.executeUpdate(sql);
                System.out.println("Executed: " + sql);
            }
            System.out.println("Database schema initialized successfully!");
        } catch (Exception e) {
            System.err.println("Error initializing schema: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database schema", e); // Fail fast
        }
    }
}