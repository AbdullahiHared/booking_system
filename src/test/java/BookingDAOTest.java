import org.example.database.DatabaseConnection;
import org.example.model.Booking;
import org.example.dao.BookingDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BookingDAOTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
            BookingDAO bookingDao = new BookingDAO(connection);

            System.out.println("=== Testing BookingDAO ===");

            // Test: Get Booking by ID
            System.out.print("Enter booking ID to retrieve: ");
            int bookingId = sc.nextInt();
            sc.nextLine(); // Consume newline

            Booking retrievedBooking = bookingDao.getBookingById(bookingId);
            if (retrievedBooking != null) {
                System.out.println("Retrieved Booking:");
                System.out.println("Booking ID: " + retrievedBooking.getId());
                System.out.println("Customer ID: " + retrievedBooking.getCustomerId());
                System.out.println("Seat: " + retrievedBooking.getSeat());

                // Test: Update Booking
                System.out.print("Enter new seat number: ");
                String newSeat = sc.nextLine();
                sc.nextLine(); // Consume newline

                retrievedBooking.setSeat(newSeat);
                try {
                    bookingDao.updateBooking(retrievedBooking);
                    System.out.println("Booking updated successfully.");
                } catch (SQLException e) {
                    System.err.println("Error updating booking: " + e.getMessage());
                }
            } else {
                System.out.println("No booking found with ID: " + bookingId);
            }

            // Test: Add a new booking
            System.out.println("\nTesting addBooking:");
            System.out.print("Enter customer ID: ");
            int customerId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.print("Enter seat number: ");
            String seat = sc.nextLine();
            sc.nextLine(); // Consume newline

            Booking newBooking = new Booking();
            newBooking.setCustomerId(customerId);
            newBooking.setSeat(seat);

            try {
                bookingDao.addBooking(newBooking);
                System.out.println("Booking added successfully. Booking ID: " + newBooking.getId());
            } catch (SQLException e) {
                System.err.println("Error adding booking: " + e.getMessage());
            }

            // Test: Delete a booking
            System.out.println("\nTesting deleteBookingById:");
            System.out.print("Enter booking ID to delete: ");
            int deleteBookingId = sc.nextInt();
            sc.nextLine(); // Consume newline

            try {
                bookingDao.deleteBookingById(deleteBookingId);
                System.out.println("Booking deleted successfully.");
            } catch (SQLException e) {
                System.err.println("Error deleting booking: " + e.getMessage());
            }

            // Test: Get all bookings
            System.out.println("\nTesting getAllBookings:");
            try {
                var bookings = bookingDao.getAllBookings();
                if (bookings.isEmpty()) {
                    System.out.println("No bookings found.");
                } else {
                    for (Booking booking : bookings) {
                        System.out.println("Booking ID: " + booking.getId() +
                                ", Customer ID: " + booking.getCustomerId() +
                                ", Seat: " + booking.getSeat());
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error fetching bookings: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}