package org.example.dao;

import org.example.model.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CustomerDAO {
    private Connection connection;

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
            ps.setString(4, customer.getPassword());

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

    // Login customer using mail  and password
    public Customer login(String email, String password) throws SQLException {
        String query = "SELECT * FROM customers WHERE email = ?  AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setBirthDate(rs.getDate("birth_date").toLocalDate());
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                return customer;
            } else  {
                System.out.println("Invalid mail or password");
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            throw e;
        }
        return null; // return null if login fails
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
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
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
    public void deleteCustomerBy(int customerId) {
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
