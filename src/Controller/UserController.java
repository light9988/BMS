package Controller;

import DataConnection.DatabaseAccessor;
import GUI.*;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

public class UserController {
    private final LogInPage logInPage;
    private final DatabaseAccessor dba;

    public UserController() {
        logInPage = new LogInPage();
        dba = new DatabaseAccessor("business.db");
        // Check if there is an admin user in the database
        // If not, show the create account page for the admin user to create an account
        if (!dba.hasAdmin()) {
            CreateAccountPage createAccountPage = new CreateAccountPage(true);
            initCreateAccountListeners(createAccountPage, true);
        }
        initLoginPageListeners();
    }

    // Set up event listeners for the login page
    private void initLoginPageListeners() {

        // When the "Sign up" button is clicked, show the create account page for a regular user to create an account
        logInPage.getSignUpButton().addActionListener((ActionEvent ae) -> {
            CreateAccountPage createAccountPage = new CreateAccountPage(false);// Instantiate the create account page with isAdmin set to false (regular user account)
            initCreateAccountListeners(createAccountPage, false);// Set up event listeners for the create account page
        });

        // When the "Login" button is clicked, verify the username and password and log the user in if they are valid
        logInPage.getLoginButton().addActionListener((ActionEvent ae) -> {
            String userName = logInPage.getUserName();
            User user = dba.getUserFromDB(userName);// Retrieve the user from the database based on their username
            if (user == null) {
                JOptionPane.showMessageDialog(logInPage, "User doesn't exist");// Show an error message if the user doesn't exist
            } else {
                char[] inputPassword = logInPage.getPassword();
                if (user.getEncryptedPassword().equals(encrypt(inputPassword,user.getSalt()))) {// Check if the password is correct
                    logInPage.setVisible(false);
                    newAdminPage(user);
                } else {
                    JOptionPane.showMessageDialog(logInPage, "Username and Password doesn't match");
                }
            }
        });
    }

    // Set up event listeners for the create account page
    private void initCreateAccountListeners(CreateAccountPage page, boolean isAdmin) {
        page.getSignUpButton().addActionListener((ActionEvent ae) -> {
            char[] password = page.getPassword();
            char[] confirmPassword = page.getConfirmPassword();
            if (Arrays.equals(password, confirmPassword)) { // Check if the password and password confirmation match
                String username = page.getUsername();
                if (dba.usernameValid(username)) { // Check if the username is available
                    String salt = generateSalt();
                    User user = new User(username, encrypt(password,salt), isAdmin); // Create a new User with the entered username, encrypted password, and account type
                    user.setSalt(salt);
                    if (!isAdmin) { // If this is a regular user account, set additional user information
                        user.setFirstName(page.getFirstName());
                        user.setLastName(page.getLastName());
                        user.setAddress(page.getAddress());
                        user.setEmail(page.getEmail());
                        user.setPhone(page.getPhoneNumber());
                    }
                    dba.addUser(user);
                    page.dispose();
                } else {
                    JOptionPane.showMessageDialog(page, "Username already exists, please change the username.");
                }
            } else {
                JOptionPane.showMessageDialog(page, "Confirm password doesn't match Password!");
            }
        });
    }

    // Method to encrypt a password
    private String encrypt(char[] password,String salt) {
        byte[] s = (new String(password) + salt).getBytes();
        for(int i = 0; i < 2; i++){
            s = Base64.getEncoder().encode(s);
        }

        return new String(s);
    }

    private String generateSalt(){
        StringBuilder salt = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 16; i++){
            salt.append((char)(random.nextInt(26) + 'a'));
        }
        return salt.toString();
    }

    // Method to create a new admin page
    private void newAdminPage(User user) {
        boolean isAdmin = user.isAdmin();
        if (isAdmin) {
            AdminHomePage adminHomePage = new AdminHomePage();// If the user is an admin, create a new AdminHomePage
            initAdminHomePageListeners(adminHomePage, user);// Call the initAdminHomePageListeners method to set the listeners for the buttons
        } else {
            CustomerHomePage customerHomePage = new CustomerHomePage(user.getUserName());// If the user is not an admin, create a new CustomerHomePage
            initCustomerHomePageListeners(customerHomePage, user);// Call the initCustomerHomePageListeners method to set the listeners for the buttons
        }
    }

    private void initAdminHomePageListeners(AdminHomePage adminHomePage, User user) {
        // Method to set the listeners for the buttons on the admin home page
        adminHomePage.getProductsButton().addActionListener((ActionEvent event) -> {
            // Create a new product controller for the admin user
            try {
                new ProductController(dba, user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        adminHomePage.getOrdersButton().addActionListener((ActionEvent event) -> {
            try {
                // Create a new order controller for the admin user
                new OrderController(dba, user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        adminHomePage.getCustomersButton().addActionListener((ActionEvent event) -> {
            // Load the list of customers from the database and create a new CustomerListPage
            List<User> customers = dba.loadCustomersFromDB();
            new CustomerListPage(customers);
        });

        adminHomePage.getBackToLoginButton().addActionListener((ActionEvent event) -> {
            // When "Back" button is clicked, dispose of admin home page and show login page again
            adminHomePage.dispose();
            logInPage.setVisible(true);
        });

    }

    private void initCustomerHomePageListeners(CustomerHomePage customerHomePage, User user) {
        // Method to set the listeners for the buttons on the customer home page
        customerHomePage.getViewProductsButton().addActionListener((ActionEvent event) -> {
            try {
                // Create a new product controller for the customer user
                new ProductController(dba, user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        customerHomePage.getViewOrdersButton().addActionListener((ActionEvent event) -> {
            try {
                // Create a new order controller for the customer user
                new OrderController(dba, user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        customerHomePage.getBackToLoginButton().addActionListener((ActionEvent event) -> {
            // When "Back" button is clicked, dispose of customer home page and show login page again
            customerHomePage.dispose();
            logInPage.setVisible(true);
        });
    }

}