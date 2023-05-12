package GUI;

import Model.CustomerTableModel;
import Model.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CustomerListPage extends JFrame {
    private JTable customersTable;
    private List<User> customers;

    private JButton backButton;


    public CustomerListPage(List<User> customers) {
        setTitle("Customer Management");

        this.customers = customers;
        render();

    }

    private void render(){
        JPanel mainPanel = new JPanel(new BorderLayout(10, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create top panel with flow layout
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        topPanel.add(new JLabel("Customer Information"));


        backButton = new JButton("Back to HomePage");
        topPanel.add(backButton,BorderLayout.WEST);
        backButton.addActionListener((ActionEvent ae) -> {
            dispose();
        });


        // Create form layout
        JPanel formPanel = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        formPanel.setBorder(border);


        CustomerTableModel tableModel = new CustomerTableModel(customers);

        customersTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(customersTable);
        formPanel.add(scrollPane);

        // Create layout for the whole window
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Set window properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
