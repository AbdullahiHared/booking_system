package org.example.dao;

import org.example.database.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusInspectorDAO {

    // method to store profit data
    public void addProfit(double amount) {
        String query = "INSERT INTO profits (amount) VALUES(?)";
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDouble(1, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: "  + e.getMessage());
        }
    }

}
