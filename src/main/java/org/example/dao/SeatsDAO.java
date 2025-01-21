package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatsDAO {

    private final Connection connection;

    public SeatsDAO(Connection connection) {
        this.connection = connection;
    }

    // add a seat
    public void addSeat(String seatNumber) {
        String query = "INSERT INTO booked_seats (seat_number) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, seatNumber);
            ps.executeUpdate();
            System.out.println("Successfully added seat: "  + seatNumber);
        } catch (SQLException e) {
            System.out.println("Error adding seat to db" + e.getMessage());
        }
    }

    // delete a seat:
    public void deleteSeat(String seat) throws SQLException{
        String query = "DELETE FROM booked_seats WHERE seat_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, seat);
            ps.executeUpdate();
            System.out.println("Successfully deleted seat number: " + seat);
        } catch (SQLException e) {
            System.err.println("Error deleting seat: " + e.getMessage());
            throw e;
        }
    }

    // get all saved seats
    public List<String> getAllSeats() {
        List<String> seats = new ArrayList<>();
        String query = "SELECT seat_number FROM booked_seats";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                seats.add(rs.getString("seat_number")); // Add each seat_number to the list
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving seats from db: " + e.getMessage());
        }

        return seats;
    }

}
