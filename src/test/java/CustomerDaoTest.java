import org.example.dao.CustomerDAO;
import org.example.database.DatabaseConnection;
import org.example.database.SchemaInitializer;
import org.example.model.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CustomerDaoTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
            // initialize schema
            SchemaInitializer.initializeSchema(connection);

            // create customerDao instance
            CustomerDAO customerDao = new CustomerDAO(connection);

            try {
                // test: add customer
                Customer customer = new Customer();

                // Name input
                System.out.println("Please enter your name:");
                String name = sc.nextLine();
                customer.setName(name);

                // Email input (validation)
                String email;
                while (true) {
                    System.out.println("Please enter your email:");
                    email = sc.nextLine();
                    if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        customer.setEmail(email);
                        break;
                    } else {
                        System.out.println("Invalid email format. Please try again.");
                    }
                }

                // Password input (validation)
                String password;
                while (true) {
                    System.out.println("Please enter your password:");
                    password = sc.nextLine();
                    if (!password.isEmpty()) {
                        customer.setPassword(password);
                        break;
                    } else {
                        System.out.println("Password cannot be empty. Please try again.");
                    }
                }

                // Birthdate input (validation)
                int birthYear, month, day;
                LocalDate birthDate = null;
                while (true) {
                    try {
                        System.out.println("Please enter your birth year:");
                        birthYear = sc.nextInt();
                        System.out.println("Please enter your birth month (1-12):");
                        month = sc.nextInt();
                        System.out.println("Please enter the day of the month:");
                        day = sc.nextInt();
                        sc.nextLine();  // Consume the remaining newline

                        // Validate the birthdate
                        birthDate = LocalDate.of(birthYear, month, day);
                        customer.setBirthDate(birthDate);
                        break; // If valid, break out of the loop
                    } catch (DateTimeException e) {
                        System.err.println("Invalid date provided. Please try again.");
                        sc.nextLine();  // Clear the buffer
                    } catch (Exception e) {
                        System.err.println("Invalid input. Please try again.");
                        sc.nextLine();  // Clear the buffer
                    }
                }

                // Add the customer to the database
                customerDao.insertCustomer(customer);
                System.out.println("Customer successfully added to the database.");

            } catch (SQLException e) {
                System.out.println("Could not add customer into DB.");
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            System.out.println(e.getMessage());
        }
    }
}
