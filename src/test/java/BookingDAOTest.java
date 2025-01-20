import org.example.dao.CustomerDAO;
import org.example.database.DatabaseConnection;
import org.example.database.SchemaInitializer;
import org.example.model.Booking;
import org.example.model.Customer;
import org.example.dao.BookingDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BookingDAOTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try(Connection connection = DatabaseConnection.getConnection()) {
            BookingDAO bookingDao = new BookingDAO(connection);

            System.out.println("=== Testing BookingDAO ===");

            /*
            // 1. Add a new booking
            Booking newBooking = new Booking();
            newBooking.setId(12); //
            newBooking.setSeatNumber(5);
            bookingDao.addBooking(newBooking);
            if(newBooking != null) {
                System.out.println("Booking added with ID: " + newBooking.getId());
            } else {
                System.out.println("Booking not added.");
            }  */

            // test. GET BOOKING BY ID
            int testing_id = 12;
            Booking booking = bookingDao.getBookingById(testing_id);
            int seat = booking.getSeat();
            System.out.println("Booking ID: " + testing_id + " has seat number: " + seat);
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            System.out.println(e.getMessage());
        }
    }
}
