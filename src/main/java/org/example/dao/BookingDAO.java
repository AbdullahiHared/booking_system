package org.example.dao;

import org.example.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private final Connection connection;

    public BookingDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a new booking
    public void addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO Bookings (customer_id, passenger_seat) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getId());
            ps.setInt(2, booking.getSeat());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        booking.setId(generatedKeys.getInt(1)); // Set the generated booking_id
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding booking: " + e.getMessage());
            throw e;
        }
    }

    // Get all bookings
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Bookings";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("booking_id"));
                booking.setId(rs.getInt("customer_id"));
                booking.setSeatNumber(rs.getInt("passenger_seat"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings: " + e.getMessage());
        }
        return bookings;
    }

    // Get booking by ID
    public Booking getBookingById(int id) throws SQLException {
        String query = "SELECT * FROM Bookings WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Booking booking = new Booking();
                    booking.setId(rs.getInt("booking_id"));
                    booking.setId(rs.getInt("customer_id"));
                    booking.setSeatNumber(rs.getInt("passenger_seat"));
                    System.out.println("Successfully fetched booking with id: " + id);
                    return booking;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching booking by ID: " + e.getMessage());
            throw e;
        }
        return null; // Return null if no booking is found
    }

    // Update a booking
    public void updateBooking(Booking booking, int customerId) throws SQLException {
        String query = "UPDATE Bookings SET customer_id = ?, passenger_seat = ? WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerId); // This is incorrect
            ps.setInt(2, booking.getSeat());
            ps.setInt(3, booking.getId());
            ps.executeUpdate();
            System.out.println("Successfully updated booking.");
        } catch (SQLException e) {
            System.err.println("Error updating booking: " + e.getMessage());
            throw e;
        }
    }

    // Delete a booking
    public void deleteBookingById(int bookingId) throws SQLException {
        String query = "DELETE FROM Bookings WHERE booking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            throw e;
        }
    }
}
