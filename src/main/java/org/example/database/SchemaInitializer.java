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
                        "birth_date DATE NOT NULL);",

                // Create the Seats table
                "CREATE TABLE IF NOT EXISTS Seats (" +
                        "seat_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "seat_number INT NOT NULL, " +
                        "is_reserved BOOLEAN DEFAULT FALSE, " +
                        "passenger_name VARCHAR(100), " +
                        "passenger_birthdate DATE, " +
                        "booking_date DATE, " +
                        "price DECIMAL(10, 2));",

                // Create the Bookings table
                "CREATE TABLE IF NOT EXISTS Bookings (" +
                        "booking_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "passenger_id INT NOT NULL, " +
                        "passenger_name VARCHAR(100) NOT NULL, " +
                        "booking_date DATE NOT NULL, " +
                        "booking_time TIME NOT NULL, " +
                        "FOREIGN KEY (passenger_id) REFERENCES Customers(customer_id));",

                // Create the BusInspectors table
                "CREATE TABLE IF NOT EXISTS BusInspectors (" +
                        "inspector_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "bus_number VARCHAR(50) NOT NULL, " +
                        "bus_date DATE NOT NULL, " +
                        "current_profit DECIMAL(10, 2) DEFAULT 0.0, " +
                        "inspector_logs TEXT);",

                // Create the ProfitLogs table
                "CREATE TABLE IF NOT EXISTS ProfitLogs (" +
                        "log_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "bus_id INT NOT NULL, " +
                        "date DATE NOT NULL, " +
                        "total_profit DECIMAL(10, 2));"
        };
    }
}
