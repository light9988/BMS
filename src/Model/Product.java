package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private int id; // unique identifier for product
    private String name;
    private int inventory;
    private double price;

    // Constructor that takes in all necessary information
    public Product(int id, String name, int inventory, double price) {
        this.id = id;
        this.name = name;
        this.inventory = inventory;
        this.price = price;
    }

    // Constructor that takes a result set and loads data from it
    public Product(ResultSet rs) throws SQLException {
        loadProductFromSQL(rs);
    }

    // Private helper method to load product data from a result set
    private void loadProductFromSQL(ResultSet rs) throws SQLException {
        id = rs.getInt("ID");
        name = rs.getString("NAME");
        inventory = rs.getInt("INVENTORY");
        price = rs.getDouble("PRICE");
    }

    // Getters and setters for each attribute

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }


    public int getInventory() {
        return inventory;
    }

}
