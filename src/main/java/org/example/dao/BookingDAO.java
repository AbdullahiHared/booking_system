package org.example.dao;

import org.example.model.Booking;
import org.example.model.TicketType;

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
        String sql = "INSERT INTO Bookings (customer_id, passenger_seat, ticket_type) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getCustomerId());
            ps.setString(2, booking.getSeat());
            ps.setString(3, booking.getTicketType().name()); // Store ticket type as a string

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
                Booking booking = mapResultSetToBooking(rs);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings: " + e.getMessage());
            throw e;
        }
        return bookings;
    }

    // Get booking by customer ID
    public Booking getBookingByCustomerId(int id) throws SQLException {
        String query = "SELECT * FROM Bookings WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBooking(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching booking by customer ID: " + e.getMessage());
            throw e;
        }
        return null; // Return null if no booking is found
    }

    // Get booking by ID
    public Booking getBookingById(int id) throws SQLException {
        String query = "SELECT * FROM Bookings WHERE booking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBooking(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching booking by ID: " + e.getMessage());
            throw e;
        }
        return null; // Return null if no booking is found
    }

    // Update a booking
    public void updateBooking(Booking booking) throws SQLException {
        String query = "UPDATE Bookings SET customer_id = ?, passenger_seat = ?, ticket_type = ? WHERE booking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, booking.getCustomerId());
            ps.setString(2, booking.getSeat());
            ps.setString(3, booking.getTicketType().name()); // Update ticket type
            ps.setInt(4, booking.getId());
            ps.executeUpdate();
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

    // Get bookings by customer ID
    public List<Booking> getBookingsByCustomerId(int customerId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Bookings WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = mapResultSetToBooking(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by customer ID: " + e.getMessage());
            throw e;
        }
        return bookings;
    }

    // Helper method to map ResultSet to Booking object
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getInt("booking_id"));
        booking.setCustomerId(rs.getInt("customer_id"));
        booking.setSeat(rs.getString("passenger_seat"));
        booking.setTicketType(TicketType.valueOf(rs.getString("ticket_type"))); // Map ticket type
        return booking;
    }
}