package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInPage extends JFrame {

    // Declare the components that will be used in the login page
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JButton signUpButton;
    private JLabel errorMessageLabel;


    public LogInPage() {
        setTitle("Log In Page");

        // Create the panel
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create the label and text field for the username input
        JLabel userLabel = new JLabel("User Name:");
        userField = new JTextField(10);
        userField.setMaximumSize(userField.getPreferredSize());

        // Create the label and password field for the password input
        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField(10);
        passField.setMaximumSize(passField.getPreferredSize());

        // Create the login button and attach an action listener to it
        loginButton = new JButton("Log In");
        loginButton.setPreferredSize(new Dimension(50, 30)); // set the size of the login button

        // Create the sign up button and attach an action listener to it
        signUpButton = new JButton("Sign Up");
        signUpButton.setPreferredSize(new Dimension(50, 30));

        // Create the label for displaying error messages
        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add the components to panel
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);
        panel.add(signUpButton);
        panel.add(errorMessageLabel);
        panel.add(new JLabel(""));

        // Set window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // This method returns the text in the username field
    public String getUserName() {
        return userField.getText();
    }

    public char[] getPassword() {
        return passField.getPassword();
    }

    // This method returns the login button
    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

}
