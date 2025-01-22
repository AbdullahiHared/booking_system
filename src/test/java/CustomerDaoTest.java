import org.example.dao.CustomerDAO;
import org.example.database.DatabaseConnection;
import org.example.database.SchemaInitializer;
import org.example.model.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CustomerDaoTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
            // create customerDao instance
            CustomerDAO customerDao = new CustomerDAO(connection);
            List<Customer> customers = customerDao.getAllCustomers();

            // test: get all customers : name and birthdate.
            customers.forEach(customer ->
                    System.out.println("Name: " + customer.getName() + ", Birthdate: " + customer.getBirthDate())
            );


        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            System.out.println(e.getMessage());
        }
    }
}
