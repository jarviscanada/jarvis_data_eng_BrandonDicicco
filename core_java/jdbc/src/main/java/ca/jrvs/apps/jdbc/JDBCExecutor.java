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
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");

      while (resultSet.next()) {
        executor.logger.debug(String.valueOf(resultSet.getInt(1)));
      }

    } catch (SQLException ex) {
        executor.logger.error("Error when creating connection and execution SQL statement", ex);
    }

  }
}
