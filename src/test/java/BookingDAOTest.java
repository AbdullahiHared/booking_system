import org.example.database.DatabaseConnection;
import org.example.model.Booking;
import org.example.dao.BookingDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BookingDAOTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try(Connection connection = DatabaseConnection.getConnection()) {
            BookingDAO bookingDao = new BookingDAO(connection);

            System.out.println("=== Testing BookingDAO ===");

            // test. GET BOOKING BY ID

            Booking retrievedBooking = bookingDao.getBookingById(12);
            int seat = retrievedBooking.getSeat();
            System.out.println("The previous seat number is: " + seat);

            int newSeat = 25;
            System.out.println("The new seat number is: " + newSeat);

            retrievedBooking.setSeatNumber(newSeat);
            retrievedBooking.setId(12);

            try {
                bookingDao.updateBooking(retrievedBooking, retrievedBooking.getId());
                System.out.println("Successfully updated  booking.");
            } catch (SQLException e) {
                System.out.println("Could not update the booking:");
            }

            // update booking
            bookingDao.updateBooking(retrievedBooking, retrievedBooking.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
