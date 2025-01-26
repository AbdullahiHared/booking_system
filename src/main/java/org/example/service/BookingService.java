package org.example.service;

import org.example.dao.BookingDAO;
import org.example.dao.CustomerDAO;
import org.example.dao.SeatsDAO;
import org.example.model.Booking;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

}