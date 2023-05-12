package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateAccountPage extends JFrame {

    // Declare variables for password and confirm password fields
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField phoneField;

    private JButton signUpButton;


    public CreateAccountPage(boolean isAdmin) {
        setTitle(isAdmin ? "Create Admin Account" : "Create Customer Account");

        // Create layout for the whole window
        JPanel panel = isAdmin ? new JPanel(new GridLayout(4, 2, 5, 5)) : new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create and add labels, text fields
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField(10);
        panel.add(usernameField);

        // Add password field and label
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(10);
        panel.add(passwordField);

        // Add confirm password field and label
        panel.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField(10);
        panel.add(confirmPasswordField);

        if (!isAdmin) {
            panel.add(new JLabel("First Name:"));
            firstNameField = new JTextField(10);
            panel.add(firstNameField);

            panel.add(new JLabel("Last Name:"));
            lastNameField = new JTextField(10);
            panel.add(lastNameField);

            panel.add(new JLabel("Mailing Address:"));
            addressField = new JTextField(10);
            panel.add(addressField);

            panel.add(new JLabel("Email:"));
            emailField = new JTextField(10);
            panel.add(emailField);

            panel.add(new JLabel("Phone Number:"));
            phoneField = new JTextField(10);
            panel.add(phoneField);
        }


        // Create and add buttons
        signUpButton = new JButton("Sign Up");
        signUpButton.setPreferredSize(new Dimension(150, 30));
        panel.add(signUpButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(150, 30));
        cancelButton.addActionListener((ActionEvent ae) -> dispose());
        panel.add(cancelButton);

        // Set window properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public JButton getSignUpButton() {
        return signUpButton;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public char[] getConfirmPassword() {
        return confirmPasswordField.getPassword();
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPhoneNumber() {
        return phoneField.getText();
    }

}


