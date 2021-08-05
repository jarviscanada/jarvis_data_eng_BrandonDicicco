package ca.jrvs.apps.jdbc;

import java.sql.SQLException;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {

  final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();

    JDBCExecutor executor = new JDBCExecutor();

    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");

    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = new Customer();
      customer.setFirstName("George");
      customer.setLastName("Washington");
      customer.setEmail("george.washington@wh.gov");
      customer.setPhone("(555) 555-6543");
      customer.setAddress("1234 Main St");
      customer.setCity("Mount Vernon");
      customer.setState("VA");
      customer.setZipCode("22121");

      customerDAO.create(customer);

    } catch (SQLException ex) {
        executor.logger.error("Error when creating connection and executing SQL statement", ex);
    }

  }
}
