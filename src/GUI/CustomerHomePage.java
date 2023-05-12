package GUI;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.Border;

public class CustomerHomePage extends JFrame {

    private JButton viewProductsButton;
    private JButton viewOrdersButton;
    private JButton backToLoginButton;

    public CustomerHomePage(String username) {
        setTitle("User Homepage");

        // Create panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Border border = BorderFactory.createEmptyBorder(30, 30, 30, 30);
        panel.setBorder(border);

        // Create welcome label
        JLabel welcomeLabel = new JLabel("Welcome " + username + "!", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // add some padding
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(welcomeLabel, gbc);

        // Set preferred button size
        Dimension buttonSize = new Dimension(200, 50);

        // Create View Products button
        viewProductsButton = new JButton("View Products");
        gbc.gridy = 1;
        viewProductsButton.setPreferredSize(buttonSize);
        viewProductsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(viewProductsButton, gbc);

        // Create View My Orders button
        viewOrdersButton = new JButton("View My Orders");
        gbc.gridy = 2;
        viewOrdersButton.setPreferredSize(buttonSize);
        viewOrdersButton.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(viewOrdersButton, gbc);

        // Create Back to Homepage button
        backToLoginButton = new JButton("Back to Login");
        gbc.gridy = 3;
        backToLoginButton.setPreferredSize(buttonSize);
        backToLoginButton.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(backToLoginButton, gbc);

        // Set window properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton getViewProductsButton() {
        return viewProductsButton;
    }

    public JButton getViewOrdersButton() {
        return viewOrdersButton;
    }

    public JButton getBackToLoginButton() {
        return backToLoginButton;
    }

}


