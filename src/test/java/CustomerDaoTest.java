import org.example.dao.CustomerDAO;
import org.example.database.DatabaseConnection;
import org.example.database.SchemaInitializer;
import org.example.model.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerDaoTest {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // initialize schema
            SchemaInitializer.initializeSchema(connection);

            // create customerDao instance
            CustomerDAO customerDao = new CustomerDAO(connection);

            // Test: add new user
            Customer customer = new Customer();
            String name = "TesName";
            LocalDate birthDate = LocalDate.of(1904, 3, 11);
            customer.setName(name);
            customer.setBirthDate(birthDate);
            System.out.println("Inserting customer: " + customer.getName() + " " + customer.getBirthDate());
            customerDao.insertCustomer(customer);
            System.out.println("Customer was added");
            // get All customers
            customerDao.getAllCustomers();
        } catch (SQLException e) {
            System.out.println("Could not add customer into db");
            System.out.println(e.getMessage());
        }
    }
}
