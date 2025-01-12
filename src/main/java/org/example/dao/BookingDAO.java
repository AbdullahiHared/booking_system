package org.example.dao;

import org.example.model.Booking;

import javax.xml.transform.Result;
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
        String sql = "INSERT INTO bookings (passenger_id, passenger_name, booking_date, booking_time) VALUES (?,?, ?, ?)";
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

    // Get all bookings
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("Booking_id"));
                booking.setPassengerId(rs.getInt("passenger_id"));
                booking.setPassengerName(rs.getString("passenger_name"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setBookingTime((rs.getTime("booking_time")).toString());

                bookings.add(booking);
            }
        }
        catch (SQLException e) {
            System.err.println("Error fetching bookings: " + e.getMessage());
        }

        return bookings;

    }

    // Get booking by ID
    public Booking getBookingById(int id ) throws SQLException {
        String query = "SELECT * FROM bookings WHERE id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(query)) {
            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();
            if(rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("booking_id"));
                booking.setPassengerId(rs.getInt("passenger_id"));
                booking.setPassengerName(rs.getString("passenger_name"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setBookingTime((rs.getTime("booking_time")).toString());

                // return booking if found
                return booking;
            }
        } catch (SQLException e) {
            // log errors
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        // return null if now booking is found
        return null;
    }
}
