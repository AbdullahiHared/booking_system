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
        String sql = "INSERT INTO bookings (passenger_id, booking_name, booking_date, booking_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getPassengerId());
            ps.setString(2, booking.getPassengerName());
            ps.setDate(3, booking.getBookingDate());
            ps.setTime(4, booking.getBookingTime());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        booking.setId(generatedKeys.getInt(1)); // Set the generated ID
                    }
                } catch (SQLException e) {
                    // print errors
                    System.out.println(e.getErrorCode());
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            // print errors
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
        }
    }
}
