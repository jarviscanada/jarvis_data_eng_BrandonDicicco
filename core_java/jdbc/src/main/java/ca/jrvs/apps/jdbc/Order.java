package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Order implements DataTransferObject {
  private long id;
  private String customerFirstName;
  private String customerLastName;
  private String customerEmail;
  private Date creationDate;
  private BigDecimal totalDue;
  private String status;
  private String salesPersonFirstName;
  private String salesPersonLastName;
  private String salesPersonEmail;
  private List<OrderItem> orderItems;

  @Override
  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public String getCustomerLastName() {
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public BigDecimal getTotalDue() {
    return totalDue;
  }

  public void setTotalDue(BigDecimal totalDue) {
    this.totalDue = totalDue;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSalesPersonFirstName() {
    return salesPersonFirstName;
  }

  public void setSalesPersonFirstName(String salesPersonFirstName) {
    this.salesPersonFirstName = salesPersonFirstName;
  }

  public String getSalesPersonLastName() {
    return salesPersonLastName;
  }

  public void setSalesPersonLastName(String salesPersonLastName) {
    this.salesPersonLastName = salesPersonLastName;
  }

  public String getSalesPersonEmail() {
    return salesPersonEmail;
  }

  public void setSalesPersonEmail(String salesPersonEmail) {
    this.salesPersonEmail = salesPersonEmail;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", customerFirstName='" + customerFirstName + '\'' +
        ", customerLastName='" + customerLastName + '\'' +
        ", customerEmail='" + customerEmail + '\'' +
        ", creationDate=" + creationDate +
        ", totalDue=" + totalDue +
        ", status='" + status + '\'' +
        ", salesPersonFirstName='" + salesPersonFirstName + '\'' +
        ", salesPersonLastName='" + salesPersonLastName + '\'' +
        ", salesPersonEmail='" + salesPersonEmail + '\'' +
        ", orderItems=" + orderItems +
        '}';
  }
}
