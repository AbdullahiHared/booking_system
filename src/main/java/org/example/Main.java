package org.example;

import org.example.dao.BookingDAO;
import org.example.dao.BusInspectorDAO;
import org.example.dao.CustomerDAO;
import org.example.dao.SeatsDAO;
import org.example.database.DatabaseConnection;
import org.example.model.Booking;
import org.example.model.Customer;
import org.example.model.TicketType;
import org.example.service.BookingService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static BookingService bookingService;
    private static Customer currentCustomer;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Initialize DAOs and services
            BookingDAO bookingDAO = new BookingDAO(connection);
            CustomerDAO customerDAO = new CustomerDAO(connection);
            SeatsDAO seatsDAO = new SeatsDAO(connection);
            BusInspectorDAO busInspectorDAO = new BusInspectorDAO();
            bookingService = new BookingService(bookingDAO, customerDAO, seatsDAO, busInspectorDAO);

            while (true) {
                try {
                    System.out.println("\n=== Bus Booking System ===");
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("3. Book a seat");
                    System.out.println("4. View Available seats");
                    System.out.println("5. Cancel Booking");
                    System.out.println("6. Find Booking");
                    System.out.println("7. Exit");
                    System.out.print("Choose an option: ");

                    int choice = getValidIntegerInput(1, 7); // Validate menu choice
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
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
                            findBooking();
                            break;
                        case 7:
                            System.out.println("Exiting the program. Goodbye!");
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void customerMenu() {
        while (true) {
            try {
                System.out.println("\n === Customer Menu === ");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Book a seat");
                System.out.println("4. View Available seats");
                System.out.println("5. Cancel Booking");
                System.out.println("6. Find Booking");
                System.out.println("7. Back to menu");
                System.out.print("Choose an option: ");

                int choice = getValidIntegerInput(1, 7); // Validate menu choice
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
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
                        findBooking();
                        break;
                    case 7:
                        return; // Go back to the main menu
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void registerCustomer() {
        System.out.println("\n=== Register ===");

        // Get name
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Get and validate email
        String email;
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            if (BookingService.isValidEmailAddress(email)) {
                break; // Exit the loop if the email is valid
            } else {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        }

        // Get and validate password
        String password;
        while (true) {
            System.out.print("Enter your password (minimum 6 characters): ");
            password = scanner.nextLine();
            if (password.length() >= 6) {
                break; // Exit the loop if the password is valid
            } else {
                System.out.println("Password must be at least 6 characters long.");
            }
        }

        // Get and validate birthdate
        LocalDate birthDate = null;
        while (birthDate == null) {
            System.out.print("Enter your birth date (YYYY-MM-DD): ");
            String birthDateStr = scanner.nextLine();
            try {
                birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        // Create and insert customer
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setBirthDate(birthDate);

        try {
            CustomerDAO customerDAO = new CustomerDAO(DatabaseConnection.getConnection());
            customerDAO.insertCustomer(customer);
            System.out.println("Registration successful! You can now log in.");
        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
        }
    }

    private static void loginCustomer() {
        System.out.println("\n=== Login ===");

        String email;
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            if (BookingService.isValidEmailAddress(email)) {
                break; // Exit the loop if the email is valid
            } else {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try {
            CustomerDAO customerDAO = new CustomerDAO(DatabaseConnection.getConnection());
            currentCustomer = customerDAO.login(email, password);
            if (currentCustomer != null) {
                System.out.println("Login successful! Welcome, " + currentCustomer.getName() + ".");
            } else {
                System.out.println("Login failed. Invalid email or password.");
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
    }
    private static void bookSeat() throws SQLException {
        if (currentCustomer == null) {
            System.out.println("You must be logged in to book a seat.");
            return;
        }

        System.out.println("\n=== Book a Seat ===");
        bookingService.displayAvailableSeats();
        System.out.print("Enter the seat number you want to book: ");
        String seatNumber = scanner.nextLine();

        // Prompt for ticket type
        System.out.println("Select ticket type:");
        System.out.println("1. Adult (299 SEK)");
        System.out.println("2. Child (149 SEK)");
        System.out.print("Choose an option: ");
        int ticketChoice = getValidIntegerInput(1, 2);
        scanner.nextLine(); // Consume newline

        TicketType ticketType = (ticketChoice == 1) ? TicketType.ADULT : TicketType.CHILD;

        if (bookingService.addBooking(currentCustomer.getId(), seatNumber, ticketType)) {
            System.out.println("Seat " + seatNumber + " booked successfully.");
        } else {
            System.out.println("Failed to book seat " + seatNumber + ".");
        }
    }

    private static void viewAvailableSeats() throws SQLException {
        System.out.println("\n=== Available Seats ===");
        bookingService.displayAvailableSeats();
    }

    private static void cancelBooking() {
        if (currentCustomer == null) {
            System.out.println("You must be logged in to cancel a booking.");
            return;
        }

        System.out.println("\n=== Cancel Booking ===");
        try {
            var bookings = bookingService.getBookingsByCustomerId(currentCustomer.getId());
            if (bookings.isEmpty()) {
                System.out.println("You have no bookings to cancel.");
                return;
            }

            System.out.println("Your bookings:");
            for (var booking : bookings) {
                System.out.println("Booking ID: " + booking.getId() + ", Seat: " + booking.getSeat());
            }

            System.out.print("Enter the booking ID to cancel: ");
            int bookingID = getValidIntegerInput(1, Integer.MAX_VALUE); // Validate booking ID
            scanner.nextLine(); // Consume newline

            if (bookingService.cancelBooking(bookingID)) {
                System.out.println("Booking cancelled successfully.");
            } else {
                System.out.println("Failed to cancel booking.");
            }
        } catch (SQLException e) {
            System.err.println("Error cancelling booking: " + e.getMessage());
        }
    }

    private static void findBooking() {
        if (currentCustomer == null) {
            System.out.println("You must be logged in to find a booking.");
            return;
        }

        System.out.println("\n=== Find Booking ===");
        try {
            // Fetch the booking for the current customer
            Booking booking = bookingService.getBookingByCustomerId(currentCustomer.getId());

            if (booking != null) {
                System.out.println("\nBooking Details:");
                System.out.println("Booking ID: " + booking.getId());
                System.out.println("Seat Number: " + booking.getSeat());
                System.out.println("Customer ID: " + booking.getCustomerId());
            } else {
                System.out.println("No booking found for the current user.");
            }
        } catch (SQLException e) {
            System.err.println("Error finding booking: " + e.getMessage());
        }
    }

    // Helper method to validate integer input within a range
    private static int getValidIntegerInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}