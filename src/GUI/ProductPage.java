package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;


public class ProductPage extends JFrame {
    // Class fields to keep track of the current operation
    private boolean isAdd = false;
    private boolean isEdit = false;
    private boolean isOrder = false;
    // Text fields and buttons used in the GUI
    private JTextField idField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField inventoryField;
    private JTextField activeField;
    private JTextField qtyField;
    private JButton cancelButton;
    private JButton confirmButton;

    public ProductPage(String function) {
        // Set the title of the window based on the operation being performed
        setTitle(function.equals("Add") ? "Add Product" :(function.equals("Edit")?"Update Product":"Order Product"));
        // Determine which operation is being performed
        if(function.equals("Add")){
            isAdd = true;
        } else if(function.equals("Edit")){
            isEdit = true;
        } else {
            isOrder = true;
        }

        // Create form layout
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        // Add ID label and field to the form panel

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10){
            @Override public void setBorder(Border border) {
            }
        };

        formPanel.add(idLabel);
        formPanel.add(idField);
        idField.setEditable(false);
        idField.setBackground( new Color(0, 0, 0, 0));

        // Add product name label and field to the form panel
        formPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField(10);
        formPanel.add(nameField);
        nameField.setEditable(!isOrder);

        // Add price label and field to the form panel
        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField(10);
        formPanel.add(priceField);
        priceField.setEditable(!isOrder);

        // Add inventory label and field to the form panel
        formPanel.add(new JLabel("Inventory:"));
        inventoryField = new JTextField(10);
        formPanel.add(inventoryField);
        inventoryField.setEditable(!isOrder);

        if(isOrder) {
            // Add quantity label and field to the form panel, only for "Order" operation
            formPanel.add(new JLabel("Quantity:"));
            qtyField = new JTextField(10);
            formPanel.add(qtyField);
        }

        // Create buttons layout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 50));
        confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(100, 50));
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        // Create layout for the whole window
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setPreferredSize(new Dimension(800, 500));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.add(new JLabel(isAdd ? "Add Product" :(isEdit ? "Update Product" : "Order Product"), SwingConstants.CENTER), BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set window properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        cancelButton.addActionListener((ActionEvent ae) -> {
            dispose();
        });
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }


    public String getProductName(){
        return nameField.getText();
    }
    public int getId(){
        String s = idField.getText();
        if(s.length() == 0) return 0;
        return Integer.parseInt(s);
    }
    public double getPrice(){
        return Double.parseDouble(priceField.getText());
    }

    public int getInventory(){
        return Integer.parseInt(inventoryField.getText());
    }

    public int getQty(){
        return Integer.parseInt(qtyField.getText());
    }

    public void setId(int id){
        idField.setText(String.valueOf(id));
    }

    public void setProductName(String name){
        nameField.setText(name);
    }

    public void setPrice(double price){
        priceField.setText(String.valueOf(price));
    }

    public void setInventory(int inventory){
        inventoryField.setText(String.valueOf(inventory));
    }

}
