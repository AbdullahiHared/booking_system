package org.example.database;

import java.sql.Connection;
import java.sql.Statement;

public class SchemaInitializer {

    public static void initializeSchema(Connection connection) {
        String[] schemaSQL = {
                // Create the Customers table
                "CREATE TABLE IF NOT EXISTS Customers (" +
                        "customer_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "customer_name VARCHAR(100) NOT NULL, " +
                        "birth_date DATE NOT NULL" +
                        "email VARCHAR(255) NOT NULL" +
                        "password_hash VARCHAR(255) NOT NULL" +
                        ");",

                // Create the Bookings table
                "CREATE TABLE IF NOT EXISTS Bookings (" +
                        "booking_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "customer_id INT NOT NULL, " + // Foreign key linking to Customers
                        "passenger_seat INT NOT NULL, " +
                        "FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE, " +
                        "UNIQUE(passenger_seat)" +
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
        }
    }
}
