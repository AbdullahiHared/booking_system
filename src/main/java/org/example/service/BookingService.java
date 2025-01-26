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
}