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

    // get all profits
    public double getTotalProfits() throws SQLException {
        String query = "SELECT SUM(amount) AS total_profit FROM profits";
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getDouble("total_profit");
            }
        }

        return 0.0; // return 0 if not profit data is found
    }
}
