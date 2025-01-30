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

public class BookingService {

    private static final Logger logger = Logger.getLogger(BookingService.class.getName());

    private final BookingDAO bookingDAO;
    private final CustomerDAO customerDAO;
    private final SeatsDAO seatsDAO;
    private final String[][] busSeats;


    public BookingService(BookingDAO bookingDAO, CustomerDAO customerDAO, SeatsDAO seatsDAO) {
        this.bookingDAO = bookingDAO;
        this.customerDAO = customerDAO;
        this.seatsDAO = seatsDAO;
        this.busSeats = initializeSeats(); // Initialize the busSeats array
        updateSeatsFromDatabase(); // Update the busSeats array with booked seats from the database
    }

    private String[][] initializeSeats() {
        String[][] seats = new String[9][5]; // 9 rows, 5 seats each
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                seats[i][j] = (i + 1) + "" + (char) ('A' + j); // seat format:
            }
        }
        return  seats;
    }

   private void updateSeatsFromDatabase() {
        try {
            List<String> bookedSeats = seatsDAO.getAllSeats();
            for (String seat : bookedSeats) {
                markSeatAsBooked(seat);
            }
        }  catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating seats from database: " + e.getMessage(), e);
        }
   }

    // check if seats is valid
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

    // check seat availability
    private boolean isSeatAvailable(String seatNumber) throws SQLException {
        List<String> bookedSeats = seatsDAO.getAllSeats();
        return !bookedSeats.contains(seatNumber);
    }

    // check if customer exists
    private boolean customerExists(int customerId) throws SQLException {
        return customerDAO.getCustomerById(customerId) != null;
    }

    public boolean addBooking(int customerId, String seatNumber) {
        try {
            if(!isValidSeat(seatNumber)) {
                logger.warning("Invalid seat number:  "  + seatNumber);
                return false;
            }

            if (!isSeatAvailable(seatNumber)) {
                logger.warning("Seat " + seatNumber + " is already booked!");
                return false;
            }

            if(!customerExists(customerId)) {
                logger.warning("Customer with ID " + customerId + " does not exist.");
                return true;
            }

            // Add booking to the database
            bookingDAO.addBooking(new Booking(customerId, seatNumber));

            // Add the seat as booked in the booked_seats database
            seatsDAO.addSeat(seatNumber);

            // Mark seat as booked
            markSeatAsBooked(seatNumber);

            logger.info("Booking added successfully for seat " + seatNumber);
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding booking: " + e.getMessage(), e);
            return false;
        }
    }

    // method for updating seat availability
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

    public boolean cancelBooking(int bookingId) {
        try {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if(booking == null) {
                logger.warning("Booking with ID " + bookingId + " not found.");
                return false;
            }

            // Delete the booking from the database
            bookingDAO.deleteBookingById(bookingId);

            // Remove seat from the booked_seats database
            seatsDAO.deleteSeat(booking.getSeat());

            // Mark the seat as available in the busSeats array
            markSeatAsAvailable(String.valueOf(booking.getSeat()));

            logger.info("Booking with ID " + bookingId + " canceled successfully.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error canceling booking: " + e.getMessage(), e);
            return false;
        }
    }

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

    private String getSeatNumber(int row, int col) {
        return (row + 1) + "" + (char) ('A' + col);
    }


    public void displayAvailableSeats() {
        try  {
            List<String> bookedSeats = seatsDAO.getAllSeats();
            for (int i = 0; i < busSeats.length; i++) {
                for (int j = 0; j < busSeats[i].length; j++) {
                    if (bookedSeats.contains(busSeats[i][j])) {
                        busSeats[i][j] = "X";
                    }
                }
            }

            // print the updated busSeats array
            for (String[] row : busSeats) {
                for (String seat: row) {
                    System.out.println(seat + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error displaying available seats: " + e.getMessage(), e);
        }
    }

    public static boolean isValidEmailAddress(String email) {
        // create the EmailValidator instance
        EmailValidator validator = EmailValidator.getInstance();
        // check for valid email addresses using isValid method
        return validator.isValid(email);
    }

    public List<Booking> getBookingsByCustomerId(int customerId) throws SQLException {
            return bookingDAO.getBookingsByCustomerId(customerId);
    }

}