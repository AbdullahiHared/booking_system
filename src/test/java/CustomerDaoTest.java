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

            // Test: delete customer by id
            customerDao.deleteCustomerById(1);
            customerDao.deleteCustomerById(2);
            customerDao.deleteCustomerById(3);
            customerDao.deleteCustomerById(4);
            customerDao.deleteCustomerById(5);
            customerDao.deleteCustomerById(6);
            customerDao.deleteCustomerById(7);
            customerDao.deleteCustomerById(8);
            customerDao.deleteCustomerById(9);
            customerDao.deleteCustomerById(10);

        } catch (SQLException e) {
            System.out.println("Could not add customer into db");
            System.out.println(e.getMessage());
        }
    }
}
