package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.math.BigDecimal;

public class OrderItem {
  private int quantity;
  private String produceCode;
  private String productName;
  private int productSize;
  private String productVariety;
  private BigDecimal productPrice;

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getProduceCode() {
    return produceCode;
  }

  public void setProduceCode(String produceCode) {
    this.produceCode = produceCode;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getProductSize() {
    return productSize;
  }

  public void setProductSize(int productSize) {
    this.productSize = productSize;
  }

  public String getProductVariety() {
    return productVariety;
  }

  public void setProductVariety(String productVariety) {
    this.productVariety = productVariety;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "quantity=" + quantity +
        ", produceCode='" + produceCode + '\'' +
        ", productName='" + productName + '\'' +
        ", productSize=" + productSize +
        ", productVariety='" + productVariety + '\'' +
        ", productPrice=" + productPrice +
        '}';
  }
}
