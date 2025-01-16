package org.example.dao;

import org.example.model.Customer;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

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

    // update customer
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

    // get customer by id
    public void getCustomerById(int id) {
        String query = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(query)) {
            psmt.setInt(1, id);
            psmt.executeUpdate();
        } catch (SQLException e) {
            // log errors
            System.out.println("Could not find custome with the given id.");
            System.out.println(e.getMessage());
        }
    }

    // get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (PreparedStatement psmt = connection.prepareStatement(query)) {
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setBirthDate(rs.getDate("birth_date").toLocalDate());

                customers.add(customer);
            }

        } catch (SQLException e) {
            // log errors
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
        }
        return customers;
    }

    // delete customers
    public void deleteCustomerById(int customerId) {
        String sql = "DELETE FROM Customers WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer with ID " + customerId + " was deleted.");
            } else {
                System.out.println("No customer found with ID " + customerId);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
        }
    }

}
