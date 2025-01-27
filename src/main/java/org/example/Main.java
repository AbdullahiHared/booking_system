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
            // initialize DAOs and services
            BookingDAO bookingDAO = new BookingDAO(connection);
            CustomerDAO customerDAO = new CustomerDAO(connection);
            SeatsDAO seatsDAO = new SeatsDAO(connection);
            bookingService = new BookingService(bookingDAO, customerDAO, seatsDAO);

            while(true) {
                System.out.println("\n=== Bus Booking System ===");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Login");
                System.out.println("4. View Available seats");
                System.out.println("5. Cancel Booking.");
                System.out.println("6. Exit");
                System.out.println("Choose an options: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 1 :
                        registerCustomer();
                        break;
                    case 2:
                        loginCustomer();
                        break;
                    case 3:
                        bookSeat();
                        break;
                    case 4:
                        viewAvailableSeats();
                        break;
                    case 5:
                        cancelBooking();
                        break;
                    case 6:
                        System.out.println("Exiting the program. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}