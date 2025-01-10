package org.example.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class SchemaInitializer {
    public static void initializeSchema(Connection connection) {
        String[] schemaSQL = {
                // Create the Users table
                "CREATE TABLE IF NOT EXISTS Customers (" +
                        "customer_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "full_name VARCHAR(100) NOT NULL, " +
                        "birth_date DATE NOT NULL, " ,

                // Create the Seats table
                "CREATE TABLE IF NOT EXISTS Seats (" +
                        "seat_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "seat_number INT NOT NULL, " +
                        "is_reserved BOOLEAN DEFAULT FALSE, " +
                        "passenger_name VARCHAR(100), " +
                        "passenger_birthdate DATE, " +
                        "booking_date DATE, " +
                        "price DECIMAL(10, 2), " +
                        "FOREIGN KEY (bus_id) REFERENCES Buses(bus_id));",
        };
    }
}
