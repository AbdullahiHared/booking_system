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

        };
    }
}
