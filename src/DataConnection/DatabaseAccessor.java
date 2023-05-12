package DataConnection;

import Model.User;
import Model.Order;
import Model.Product;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessor {
    // SQL queries for creating tables and inserting data
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS %s " + "(%s)";
    private static final String INSERT_ADMIN_USER_QUERY = "INSERT INTO %s (id,username,password,isAdmin,salt) " + "VALUES (NULL, '%s', '%s', %s,'%s');";
    private static final String INSERT_CUSTOMER_USER_QUERY = "INSERT INTO %s (id,username,password,isAdmin,salt,firstName, lastName, mailingAddress, email, phoneNumber) " + "VALUES (NULL, '%s', '%s', %s,'%s','%s','%s','%s','%s','%s');";
    private static final String INSERT_PRODUCT_QUERY = "INSERT INTO %s (id,name,inventory,price) " + "VALUES (NULL, '%s', %s, %s);";
    private static final String INSERT_ORDER_QUERY = "INSERT INTO %s (ID,productId,productName,customerId,customerName,Quantity,price,totalPrice,isComplete) " + "VALUES (NULL, %s, '%s', %s, '%s', %s, %s, %s, %s);";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE PRODUCT SET NAME = '%s', INVENTORY = %s, PRICE = %s where ID = %s";
    private static final String UPDATE_INVENTORY_QUERY = "UPDATE PRODUCT SET INVENTORY = %s where ID = %s";
    private static final String SQL_SELECT_ALL_PRODUCT = "SELECT * FROM PRODUCT;";
    private static final String SQL_SELECT_ALL_ACTIVE_PRODUCT = "SELECT * FROM PRODUCT where INVENTORY > 0;";
    private static final String SQL_SELECT_ALL_ORDER = "SELECT * FROM ORDERS;";
    private static final String SQL_SELECT_CUSTOMER_ORDER = "SELECT * FROM ORDERS WHERE CustomerId = %s;";
    private static final String SQL_SELECT_ADMIN_USER = "SELECT * FROM USER WHERE isAdmin = 1";
    private static final String SQL_SELECT_CUSTOMER_USER = "SELECT * FROM USER WHERE isAdmin = 0";
    // Fields for managing database connections
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DatabaseAccessor(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            // Define column names and data types for each table
            String[] userColumns = new String[]{
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL",
                    "username TEXT NOT NULL",
                    "password TEXT NOT NULL",
                    "salt TEXT",
                    "isAdmin BOOLEAN NOT NULL",
                    "firstName TEXT",
                    "lastName TEXT",
                    "mailingAddress TEXT",
                    "email TEXT ",
                    "phoneNumber TEXT"
            };
            String[] productColumns = new String[]{
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL",
                    "name TEXT NOT NULL",
                    "inventory INT NOT NULL",
                    "price REAL"
            };
            String[] orderColumns = new String[]{
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL",
                    "productId INT NOT NULL",
                    "productName TEXT NOT NULL",
                    "customerId INT NOT NULL",
                    "customerName TEXT NOT NULL",
                    "quantity INT NOT NULL",
                    "price REAL",
                    "totalPrice REAL",
                    "isComplete BOOLEAN"
            };
            // Create the USER, PRODUCT, and ORDERS tables if they don't already exist
            createTableIfNotExists("USER", userColumns);
            createTableIfNotExists("PRODUCT", productColumns);
            createTableIfNotExists("ORDERS", orderColumns);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("DatabaseAccessor connection opened successfully");
    }

    // This method creates a new table in the database if it doesn't already exist.
    // The tableName parameter specifies the name of the table to be created, and
    // the columns parameter is an array of column names and data types.
    public void createTableIfNotExists(String tableName, String[] columns) {
        try {
            statement = connection.createStatement();
            String sql = String.format(CREATE, tableName, String.join(",", columns));
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    // This method checks if there is at least one admin user in the database.
    // It returns true if there is an admin user, false otherwise.
    public boolean hasAdmin() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ADMIN_USER);
            if (resultSet.next()) {
                statement.close();
                return true;
            }
            statement.close();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Checks if a given username is valid
    public boolean usernameValid(String username) {
        User user = getUserFromDB(username);
        return user == null;
    }

    // Adds a new user to the database
    public void addUser(User user) {
        String sql = getInsertUserQuery(user);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Add user successfully");
    }

    // Generates and returns the SQL query for inserting a new user into the database
    private String getInsertUserQuery(User user) {
        return user.isAdmin() ? String.format(INSERT_ADMIN_USER_QUERY, "USER", user.getUserName(), user.getEncryptedPassword(), user.isAdmin(),user.getSalt()) :
                String.format(INSERT_CUSTOMER_USER_QUERY, "USER", user.getUserName(), user.getEncryptedPassword(), user.isAdmin(),user.getSalt(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getEmail(), user.getPhone());
    }

    // Retrieves a user from the database by their username
    public User getUserFromDB(String userName) {
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM USER WHERE USERNAME = '" + userName + "';";
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                User user = new User(resultSet);
                statement.close();
                return user;
            }
            statement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Loads all customer users from the database
    public List<User> loadCustomersFromDB() {
        try {
            System.out.println("Loading customers from table...");
            List<User> customers = new ArrayList<>();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_CUSTOMER_USER);

            while (resultSet.next()) {
                customers.add(new User(resultSet));
            }
            System.out.println("Loaded " + customers.size() + " records.");
            statement.close();
            return customers;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getErrorCode());
            JOptionPane.showMessageDialog(null, "Database error : " + e.getMessage());
        }
        return null;
    }

    // Adds a new product to the database
    public void addProduct(Product p) {
        String sql = getInsertProductQuery(p);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Add product successfully");

    }

    // Generates an SQL query string for inserting a new product into the "PRODUCT" table
    private static String getInsertProductQuery(Product product) {
        return String.format(INSERT_PRODUCT_QUERY, "PRODUCT", product.getName(), product.getInventory(), product.getPrice());

    }

    // Updates a product in the database
    public void updateProduct(Product product) {
        try {
            statement = connection.createStatement();
            String sql = String.format(UPDATE_PRODUCT_QUERY, product.getName(), product.getInventory(), product.getPrice(), product.getId());
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Product update successfully");

    }

    // Loads all products from the database
    public ArrayList<Product> loadProductsFromDB(boolean isAdmin) {
        try {
            System.out.println("Loading data from table...");
            ArrayList<Product> products = new ArrayList<>();

            statement = connection.createStatement();
            resultSet = isAdmin ? statement.executeQuery(SQL_SELECT_ALL_PRODUCT) : statement.executeQuery(SQL_SELECT_ALL_ACTIVE_PRODUCT);

            while (resultSet.next()) {
                products.add(new Product(resultSet));
            }
            System.out.println("Loaded " + products.size() + " records.");
            return products;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getErrorCode());
            JOptionPane.showMessageDialog(null, "Database error : " + e.getMessage());
        }
        return null;
    }

    // Updates the inventory of a given product by subtracting the given quantity from its current inventory
    public void reduceInventory(Product product, int qty) {
        try {
            statement = connection.createStatement();
            String sql = String.format(UPDATE_INVENTORY_QUERY, product.getInventory() - qty, product.getId());
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Product Inventory update successfully");

    }

    // Inserts a new order into the database
    public void addOrder(Order order) {
        String sql = getInsertOrderQuery(order);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Add order successfully");
    }

    // Returns the SQL query string for inserting a new order into the database
    private static String getInsertOrderQuery(Order order) {
        return String.format(INSERT_ORDER_QUERY, "ORDERS", order.getProductId(), order.getProductName(), order.getCustomerId(), order.getCustomerName(), order.getQty(), order.getPrice(), order.getTotalPrice(), order.isComplete());
    }

    // Loads all orders from the database
    public List<Order> loadOrdersFromDB() {
        try {
            System.out.println("Loading data from table...");
            ArrayList<Order> orders = new ArrayList<>();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER);

            while (resultSet.next()) {
                orders.add(new Order(resultSet));
            }
            System.out.println("Loaded " + orders.size() + " records.");
            return orders;
        } catch (SQLException e) {

            System.err.println(e.getClass().getName() + ": " + e.getErrorCode());
            JOptionPane.showMessageDialog(null, "Database error : " + e.getMessage());

        }
        return null;
    }

   // Loads all orders from the database for a specific user
    public List<Order> loadOrdersFromDB(User user) {
        try {
            System.out.println("Loading data from table...");
            ArrayList<Order> orders = new ArrayList<>();

            String sql = String.format(SQL_SELECT_CUSTOMER_ORDER, user.getId());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                orders.add(new Order(resultSet));
            }
            System.out.println("Loaded " + orders.size() + " records.");
            return orders;
        } catch (SQLException e) {

            System.err.println(e.getClass().getName() + ": " + e.getErrorCode());
            JOptionPane.showMessageDialog(null, "Database error : " + e.getMessage());

        }
        return null;
    }

    // Closes the connection to the database
    public void close() {
        try {
            statement.close();
            connection.close();
            System.out.println("DatabaseAccessor successfully closed.");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            JOptionPane.showMessageDialog(null, "DatabaseAccessor error : " + e.getMessage());
        }
    }


}
