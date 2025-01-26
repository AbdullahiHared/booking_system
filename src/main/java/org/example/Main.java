package org.example;

import org.example.dao.BookingDAO;
import org.example.dao.CustomerDAO;
import org.example.dao.SeatsDAO;
import org.example.database.DatabaseConnection;
import org.example.model.Customer;
import org.example.service.BookingService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static BookingService bookingService;
    private static Customer currentCustomer;

    public static void main(String[] args) {
        Scanner scanner = scanner = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
            BookingDAO bookingDAO = new BookingDAO(connection);
            CustomerDAO customerDAO = new CustomerDAO(connection);
            SeatsDAO seatsDAO = new SeatsDAO(connection);
            bookingService = new BookingService(bookingDAO, customerDAO, seatsDAO);


            // Main menu loop here
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}