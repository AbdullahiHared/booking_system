package org.example.dao;

import org.example.model.Customer;

import java.sql.*;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a new customer
    public void insertCustomer(Customer customer) {
        String query = "INSERT INTO customers(customer_id, customer_name, birth_date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setDate(3, customer.getBirthDate());
            preparedStatement.executeUpdate();

            // Retrieve the auto-generated ID
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET customer_name = ?, birth_date = ? WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setDate(3, customer.getBirthDate());
            preparedStatement.executeUpdate();
    } catch (SQLException e) {
            System.out.println("Error updating: " + e.getMessage());
        }
    }

}
