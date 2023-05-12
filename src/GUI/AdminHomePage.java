package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHomePage extends JFrame {
    // Declare the components that will be used in this page
    private JButton productsButton;
    private JButton ordersButton;

    private JButton customersButton;
    private JButton backToLoginButton;



    public AdminHomePage() {
        setTitle("Admin Home Page");

        // Create panel and set layout to GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Border border = BorderFactory.createEmptyBorder(30, 30, 30, 30);
        panel.setBorder(border);

        // Set preferred button size
        Dimension buttonSize = new Dimension(200, 50);

        // Create and add "Manage Products" button to first row
        productsButton = new JButton("Manage Products");
        productsButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // add some padding
        panel.add(productsButton, gbc);


        // Create and add "Manage Orders" button to second row
        ordersButton = new JButton("Manage Orders");
        ordersButton.setPreferredSize(buttonSize);
        gbc.gridy = 1;
        panel.add(ordersButton, gbc);

        // Create and add "Manage Customers" button to third row
        customersButton = new JButton("Manage Customers");
        customersButton.setPreferredSize(buttonSize);
        gbc.gridy = 2;
        panel.add(customersButton, gbc);

        // Create and add "Back To Homepage" button to fourth row
        backToLoginButton = new JButton("Back To Login");
        backToLoginButton.setPreferredSize(buttonSize);
        gbc.gridy = 3;
        panel.add(backToLoginButton,gbc);

        // Add panel to frame
        getContentPane().add(panel);

        // Set window properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Getter methods for the buttons
    public JButton getProductsButton() {
        return productsButton;
    }

    public JButton getOrdersButton() {
        return ordersButton;
    }

    public JButton getBackToLoginButton(){
        return backToLoginButton;
    }

    public JButton getCustomersButton() {
        return customersButton;
    }

}


