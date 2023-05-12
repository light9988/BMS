package GUI;

import Model.Product;
import Model.ProductTableModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ProductListPage extends JFrame {
    private JTable productsTable;
    private List<Product> products;

    private JButton addButton;
    private JButton backButton;

    private TableColumn updateColumn;

    private boolean isAdmin;

    public ProductListPage(List<Product> products, boolean isAdmin) {
        setTitle(isAdmin ? "Manage Products" : "Browse Products");

        this.products = products;
        this.isAdmin = isAdmin;
        // Create layout for the whole window

        render(isAdmin);

    }

    private void render(boolean isAdmin) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create top panel with flow layout
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        topPanel.add(new JLabel("Product Information"));

        addButton = new JButton("Add Product");
        topPanel.add(addButton, BorderLayout.EAST);
        addButton.setVisible(isAdmin);

        backButton = new JButton("Back to HomePage");
        topPanel.add(backButton, BorderLayout.WEST);
        backButton.addActionListener((ActionEvent ae) -> {
            dispose();
        });


        // Add labels and text fields to form
        String[] labels = {"ID:", "Name:", "Price:", "Inventory:", "Status:", "", ""};

        // Create form layout
        JPanel formPanel = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        formPanel.setBorder(border);

        ProductTableModel tableModel = new ProductTableModel(products, isAdmin);

        productsTable = new JTable(tableModel);
        updateColumn = productsTable.getColumnModel().getColumn(4);
        updateColumn.setCellRenderer(new UpdateButtonRenderer());

        JScrollPane scrollPane = new JScrollPane(productsTable);
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

    public JButton getAddButton() {
        return addButton;
    }

    class UpdateButtonRenderer extends JButton implements TableCellRenderer {
        public UpdateButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    public TableColumn getUpdateColumn() {
        return updateColumn;
    }

    public void updateProductList(List<Product> products) {
        this.products = products;
        render(isAdmin);
    }

    public JButton getBackButton() {
        return backButton;
    }

}
