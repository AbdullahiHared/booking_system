import org.example.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
public class CustomerDaoTest {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("--- Testing CustomerDAO ---");
        } catch (SQLException e) {
            System.err.println("Error during testing: " + e.getMessage());
        }
    }
}