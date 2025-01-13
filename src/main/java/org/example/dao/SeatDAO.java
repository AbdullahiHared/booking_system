package org.example.dao;
import org.example.model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SeatDAO {
    private Connection connection;

    public SeatDAO(Connection connection) {
        this.connection = connection;
    }

    // Add new seat to the db
    public void addSeat(Seat seat) {
        String query = "INSERT INTO seats (seat_number, is_reserved, passenger_name, passenger_birthdate, price) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(query)) {
            psmt.setInt(1, seat.getSeatNumber());
            psmt.setBoolean(2, seat.isReserved());
            psmt.setString(3, seat.getPassengerName());
            psmt.setDate(4, seat.getPassengerBirthDate());
            psmt.setDouble(5, seat.getPrice());

            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Seat successfully booked");
            } else {
                System.out.println("Could not book seat.");
            }

        } catch (SQLException e) {
            // log errors
            System.out.println(e.getMessage());
        }
    }

}
