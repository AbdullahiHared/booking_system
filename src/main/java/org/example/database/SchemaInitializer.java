package org.example.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class SchemaInitializer {
    public static void initializeSchema(Connection connection) {
        String[] schemaSQL = {
                // Create the Users table
                "CREATE TABLE IF NOT EXISTS Customers (" +
                        "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(100) NOT NULL, " +
                        "email VARCHAR(100) UNIQUE NOT NULL, " +
                        "password VARCHAR(255) NOT NULL);",
        };
    }
}
