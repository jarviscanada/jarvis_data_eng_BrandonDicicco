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
      OrderDAO orderDAO = new OrderDAO(connection);

      Order order = orderDAO.findById(1000);
      executor.logger.debug(order.toString());


    } catch (SQLException ex) {
        executor.logger.error("Error when creating connection and executing SQL statement", ex);
    }

  }
}
