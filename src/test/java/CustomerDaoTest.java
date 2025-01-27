import org.example.dao.CustomerDAO;
import org.example.database.DatabaseConnection;
import org.example.model.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerDaoTest {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(connection);

            System.out.println("=== Testing CustomerDAO ===");

            // Test: Insert a new customer
            Customer newCustomer = new Customer();
            newCustomer.setName("John Doe");
            newCustomer.setBirthDate(LocalDate.of(1990, 5, 15));
            newCustomer.setEmail("john.doe@example.com");
            newCustomer.setPassword("password123");

            customerDAO.insertCustomer(newCustomer);
            System.out.println("Customer inserted with ID: " + newCustomer.getId());

            // Test: Login with the new customer
            Customer loggedInCustomer = customerDAO.login("john.doe@example.com", "password123");
            if (loggedInCustomer != null) {
                System.out.println("Login successful! Customer ID: " + loggedInCustomer.getId());
            } else {
                System.out.println("Login failed.");
            }

            // Test: Get all customers
            List<Customer> customers = customerDAO.getAllCustomers();
            System.out.println("\nAll Customers:");
            for (Customer customer : customers) {
                System.out.println("ID: " + customer.getId() +
                        ", Name: " + customer.getName() +
                        ", Email: " + customer.getEmail());
            }

            // Test: Delete the new customer
            customerDAO.deleteCustomer("john.doe@example.com", "password123");
            System.out.println("\nCustomer deleted.");

            // Verify deletion by trying to log in again
            Customer deletedCustomer = customerDAO.login("john.doe@example.com", "password123");
            if (deletedCustomer == null) {
                System.out.println("Customer deletion verified.");
            } else {
                System.out.println("Customer deletion failed.");
            }

        } catch (SQLException e) {
            System.err.println("Error during testing: " + e.getMessage());
        }
    }
}