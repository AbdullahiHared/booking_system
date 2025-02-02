import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BookingDAOTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("--- Testing BookingDAO ---");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}