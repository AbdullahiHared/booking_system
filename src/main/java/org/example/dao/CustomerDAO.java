package org.example.dao;

import org.example.model.Customer;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private final Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a new customer
    public void insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (customer_name, birth_date, email, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getName());
            ps.setDate(2, Date.valueOf(customer.getBirthDate()));
            ps.setString(3, customer.getEmail());
            ps.setString(4, hashPassword(customer.getPassword())); // Hash the password before storing

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
            throw e;
        }
    }

    // Login customer using email and password
    public Customer login(String email, String password) throws SQLException {
        String query = "SELECT * FROM customers WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (verifyPassword(password, storedHash)) { // Verify hashed password
                        Customer customer = new Customer();
                        customer.setId(rs.getInt("customer_id"));
                        customer.setName(rs.getString("customer_name"));
                        customer.setBirthDate(rs.getDate("birth_date").toLocalDate());
                        customer.setEmail(rs.getString("email"));
                        customer.setPassword(storedHash); // Store the hashed password
                        return customer;
                    } else {
                        System.out.println("Invalid password for email: " + email);
                    }
                } else {
                    System.out.println("No customer found with email: " + email);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
            throw e;
        }
        return null; // Return null if login fails
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setBirthDate(rs.getDate("birth_date").toLocalDate());
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customers: " + e.getMessage());
        }
        return customers;
    }

    // delete customer by ID
    public void deleteCustomerById(int customerId) throws SQLException {
        String query = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("No customer found with ID: " + customerId);
            }
        }
    }

    // Delete customer by email and password
    public void deleteCustomer(String email, String password) throws SQLException {
        String sql = "DELETE FROM customers WHERE email = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, hashPassword(password));
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer with email " + email + " was deleted.");
            } else {
                System.out.println("No customer found with email: " + email);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            throw e;
        }
    }

    // Find customer by ID
    public Customer getCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("customer_id"));
                    customer.setName(rs.getString("customer_name"));
                    customer.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    customer.setEmail(rs.getString("email"));
                    customer.setPassword(rs.getString("password"));
                    return customer;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customer by ID: " + e.getMessage());
            throw e;
        }
        return null; // Return null if no customer is found
    }

    // Helper method to hash passwords using BCrypt
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Helper method to verify passwords using BCrypt
    private boolean verifyPassword(String inputPassword, String storedHash) {
        return BCrypt.checkpw(inputPassword, storedHash); // Verifies input password against stored hash
    }
}