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

    // get customer by id
    public void getCustomerById(int id) {
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(query)) {
            psmt.setInt(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Customer found: ");
                    System.out.println("ID: " + rs.getInt("customer_id"));
                    System.out.println("Name: " + rs.getString("customer_name"));
                    System.out.println("Birth Date: " + rs.getDate("birth_date"));
                } else {
                    System.out.println("No customer found with ID " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not find customer with the given ID.");
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

    // print user information
    public void printUserInfo(String name, LocalDate birthdate) {
        System.out.println("Your  information");
        System.out.println("Name: " + name);
        System.out.println("BirthDate: " + birthdate);
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
