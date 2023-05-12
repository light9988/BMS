package GUI;

import Model.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OrderListPage extends JFrame {
    private JTable ordersTable;
    private List<Order> orders;

    private JButton backButton;

    public OrderListPage (boolean isAdmin,List<Order> orders){
        setTitle("Browse Orders");
        this.orders = orders;
        render(isAdmin);
    }

    private void render(boolean isAdmin){
        JPanel mainPanel = new JPanel(new BorderLayout(10, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create top panel with flow layout
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        topPanel.add(new JLabel("Order Details"));

        // Create form layout
        JPanel formPanel = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        formPanel.setBorder(border);


        OrderTableModel tableModel = new OrderTableModel(orders,isAdmin);

        ordersTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        formPanel.add(scrollPane);

        backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent ae) -> {
            dispose();
        });
        topPanel.add(backButton);
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
