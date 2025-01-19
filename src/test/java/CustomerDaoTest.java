import org.example.dao.CustomerDAO;
import org.example.database.DatabaseConnection;
import org.example.database.SchemaInitializer;
import org.example.model.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class CustomerDaoTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
            // initialize schema
            SchemaInitializer.initializeSchema(connection);

            // create customerDao instance
            CustomerDAO customerDao = new CustomerDAO(connection);

            // test: add customer
            Customer customer = new Customer();
            System.out.println("Please enter your name:");
            String name = sc.nextLine();

            System.out.println("Please enter your email:");
            String email = sc.nextLine();

            System.out.println("Please enter your password:");
            String password = sc.nextLine();

            System.out.println("Please enter your birth year:");
            int birthYear = sc.nextInt();
            System.out.println("Please enter your birth month (1-12):");
            int month = sc.nextInt();
            System.out.println("Please enter the day of the month:");
            int day = sc.nextInt();
            sc.nextLine();

            // Validate and create LocalDate
            LocalDate birthDate;
            try {
                birthDate = LocalDate.of(birthYear, month, day);
            } catch (DateTimeException e) {
                System.err.println("Invalid date provided. Please try again.");
                return;
            }

            customer.setName(name);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setBirthDate(birthDate);

            // add the customer to the database:
            customerDao.insertCustomer(customer);

        } catch (SQLException e) {
            System.out.println("Could not add customer into db");
            System.out.println(e.getMessage());
        }
    }
}
