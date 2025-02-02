package org.example.service;

import org.example.dao.BookingDAO;
import org.example.dao.CustomerDAO;
import org.example.dao.SeatsDAO;
import org.example.model.Booking;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.validator.routines.EmailValidator;
import org.example.model.TicketType;
import org.example.dao.BusInspectorDAO;

public class BookingService {

    private static final Logger logger = Logger.getLogger(BookingService.class.getName());

    private final BookingDAO bookingDAO;
    private final CustomerDAO customerDAO;
    private final SeatsDAO seatsDAO;
    private final BusInspectorDAO busInspectorDAO;
    private final String[][] busSeats;

    public BookingService(BookingDAO bookingDAO, CustomerDAO customerDAO, SeatsDAO seatsDAO, BusInspectorDAO busInspectorDAO) {
        this.bookingDAO = bookingDAO;
        this.customerDAO = customerDAO;
        this.seatsDAO = seatsDAO;
        this.busInspectorDAO = busInspectorDAO;
        this.busSeats = initializeSeats(); // Initialize the busSeats array
        updateSeatsFromDatabase(); // Update the busSeats array with booked seats from the database
    }

    private String[][] initializeSeats() {
        String[][] seats = new String[9][5]; // 9 rows, 5 seats each
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                seats[i][j] = (i + 1) + "" + (char) ('A' + j); // seat format: e.g., "1A", "1B", etc.
            }
        }
        return seats;
    }

    private void updateSeatsFromDatabase() {
        try {
            List<String> bookedSeats = seatsDAO.getAllSeats();
            for (String seat : bookedSeats) {
                markSeatAsBooked(seat);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating seats from database: " + e.getMessage(), e);
        }
    }

    // Check if a seat is valid
    private boolean isValidSeat(String seatNumber) {
        for (String[] row : busSeats) {
            for (String seat : row) {
                if (seat.equals(seatNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check if a seat is available
    private boolean isSeatAvailable(String seatNumber) throws SQLException {
        List<String> bookedSeats = seatsDAO.getAllSeats();
        return !bookedSeats.contains(seatNumber);
    }

    // Check if a customer exists
    private boolean customerExists(int customerId) throws SQLException {
        return customerDAO.getCustomerById(customerId) != null;
    }

    // Add a booking
    public boolean addBooking(int customerId, String seatNumber, TicketType ticketType) {
        try {
            if (!isValidSeat(seatNumber)) {
                logger.warning("Invalid seat number: " + seatNumber);
                return false;
            }

            if (!isSeatAvailable(seatNumber)) {
                logger.warning("Seat " + seatNumber + " is already booked!");
                return false;
            }

            if (!customerExists(customerId)) {
                logger.warning("Customer with ID " + customerId + " does not exist.");
                return false;
            }

            // Calculate price based on ticket type
            double ticketPrice = calculatePrice(ticketType);

            // Add booking to the database
            bookingDAO.addBooking(new Booking(customerId, seatNumber, ticketType));

            // Add the seat as booked in the booked_seats database
            seatsDAO.addSeat(seatNumber);

            // Mark seat as booked
            markSeatAsBooked(seatNumber);

            // add profit to the database
            busInspectorDAO.addProfit(ticketPrice);

            logger.info("Booking added successfully for seat " + seatNumber);
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding booking: " + e.getMessage(), e);
            return false;
        }
    }

    // Mark a seat as booked
    private void markSeatAsBooked(String seatNumber) {
        for (int i = 0; i < busSeats.length; i++) {
            for (int j = 0; j < busSeats[i].length; j++) {
                if (busSeats[i][j].equals(seatNumber)) {
                    busSeats[i][j] = "X";
                    break;
                }
            }
        }
    }

    // Cancel a booking
    public boolean cancelBooking(int bookingId) {
        try {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                logger.warning("Booking with ID " + bookingId + " not found.");
                return false;
            }

            // Delete the booking from the database
            bookingDAO.deleteBookingById(bookingId);

            // Remove seat from the booked_seats database
            seatsDAO.deleteSeat(booking.getSeat());

            // Mark the seat as available in the busSeats array
            markSeatAsAvailable(booking.getSeat());

            logger.info("Booking with ID " + bookingId + " canceled successfully.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error canceling booking: " + e.getMessage(), e);
            return false;
        }
    }

    // Helper method to calculate price based on ticket type
    private double calculatePrice(TicketType ticketType) {
        return (ticketType == TicketType.ADULT) ? 299.0 : 149.0;
    }

    // Mark a seat as available
    private void markSeatAsAvailable(String seatNumber) {
        for (int i = 0; i < busSeats.length; i++) {
            for (int j = 0; j < busSeats[i].length; j++) {
                if (busSeats[i][j].equals("X") && getSeatNumber(i, j).equals(seatNumber)) {
                    busSeats[i][j] = seatNumber; // Mark as available
                    break;
                }
            }
        }
    }

    // Get seat number from row and column
    private String getSeatNumber(int row, int col) {
        return (row + 1) + "" + (char) ('A' + col);
    }

    // Display available seats
    public void displayAvailableSeats() {
        try {
            List<String> bookedSeats = seatsDAO.getAllSeats();
            for (int i = 0; i < busSeats.length; i++) {
                for (int j = 0; j < busSeats[i].length; j++) {
                    if (bookedSeats.contains(busSeats[i][j])) {
                        busSeats[i][j] = "X";
                    }
                }
            }

            // Print the updated busSeats array
            for (String[] row : busSeats) {
                for (String seat : row) {
                    System.out.printf("%-4s", seat);
                }
                System.out.println();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error displaying available seats: " + e.getMessage(), e);
        }
    }

    // Validate email address
    public static boolean isValidEmailAddress(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    // Get bookings by customer ID
    public List<Booking> getBookingsByCustomerId(int customerId) throws SQLException {
        return bookingDAO.getBookingsByCustomerId(customerId);
    }

    // Find a booking by customer ID
    public Booking getBookingByCustomerId(int customerId) throws SQLException {
        return bookingDAO.getBookingByCustomerId(customerId);
    }

    // get total profit
    public static double getTotalProfit() throws SQLException {
        BusInspectorDAO busInspectorDAO = new BusInspectorDAO();
        return busInspectorDAO.getTotalProfits();
    }
}