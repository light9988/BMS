package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Order {
    // Fields to store information about the order
    private int id;
    private int productId;
    private String productName;
    private int customerId;
    private String customerName;
    private int qty;
    private double price;
    private double totalPrice;
    private boolean isComplete;

    // Constructor to create an Order object from a User object, Product object, and quantity
    public Order(User user, Product product, int qty){
        productId = product.getId();
        productName = product.getName();
        this.customerId = user.getId();
        this.customerName = user.getUserName();
        this.qty = qty;
        this.price = product.getPrice();
        totalPrice = price * qty;
        isComplete = false;
    }

    // Constructor to create an Order object from a ResultSet object
    public Order(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("ID");
        productId = resultSet.getInt("ProductID");
        productName = resultSet.getString("ProductName");
        customerId = resultSet.getInt("CustomerId");
        customerName = resultSet.getString("CustomerName");
        qty = resultSet.getInt("Quantity");
        price =resultSet.getDouble("Price");
        totalPrice = resultSet.getDouble("TotalPrice");
        isComplete = resultSet.getBoolean("isComplete");
    }


    // Getter methods for the fields of the Order object
    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isComplete() {
        return isComplete;
    }

}


