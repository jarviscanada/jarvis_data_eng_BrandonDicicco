package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAO extends DataAccessObject<Order> {

  final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

  private final static String GET_ONE = "SELECT"
      + "  c.first_name, c.last_name, c.email, o.order_id,"
      + "  o.creation_date, o.total_due, o.status,"
      + "  s.first_name, s.last_name, s.email,"
      + "  oi.quantity,"
      + "  p.code, p.name, p.size, p.variety, p.price"
      + " from orders o"
      + "  join customer c on o.customer_id = c.customer_id"
      + "  join salesperson s on o.salesperson_id=s.salesperson_id"
      + "  join order_item oi on oi.order_id=o.order_id"
      + "  join product p on oi.product_id = p.product_id"
      + " where o.order_id = ?";

  public OrderDAO(Connection connection) {
    super(connection);
  }

  @Override
  public Order findById(long id) {
    Order order = new Order();

    try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE);) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();

      long orderId = 0;
      List<OrderItem> orderItems = new ArrayList<>();

      while (resultSet.next()) {
        // Only update order information once. Not for each time an order item is retrieved
        if (orderId == 0) {
          order.setCustomerFirstName(resultSet.getString(1));
          order.setCustomerLastName(resultSet.getString(2));
          order.setCustomerEmail(resultSet.getString(3));
          order.setId(resultSet.getLong(4));
          order.setCreationDate(new Date(resultSet.getDate(5).getTime()));
          order.setTotalDue(resultSet.getBigDecimal(6));
          order.setStatus(resultSet.getString(7));
          order.setSalesPersonFirstName(resultSet.getString(8));
          order.setSalesPersonLastName(resultSet.getString(9));
          order.setSalesPersonEmail(resultSet.getString(10));

          orderId = order.getId();
        }
        // Create each item that is in an order
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(resultSet.getInt(11));
        orderItem.setProduceCode(resultSet.getString(12));
        orderItem.setProductName(resultSet.getString(13));
        orderItem.setProductSize(resultSet.getInt(14));
        orderItem.setProductVariety(resultSet.getString(15));
        orderItem.setProductPrice(resultSet.getBigDecimal(16));

        orderItems.add(orderItem);
      }

      order.setOrderItems(orderItems);

    } catch (SQLException ex) {
      logger.debug("Error when finding the order", ex);
      throw new RuntimeException();
    }

    return order;
  }

  @Override
  public List<Order> findAll() {
    return null;
  }

  @Override
  public Order update(Order dto) {
    return null;
  }

  @Override
  public Order create(Order dto) {
    return null;
  }

  @Override
  public void delete(long id) {

  }

}
