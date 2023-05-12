package Controller;

import GUI.ProductPage;
import DataConnection.DatabaseAccessor;
import GUI.ProductListPage;
import Model.Order;
import Model.Product;
import Model.ProductTableModel;
import Model.User;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;


public class ProductController {


    private final ProductListPage browseProductPage;
    private final DatabaseAccessor dba;

    private final boolean isAdmin;

    private final User user;

    public ProductController(DatabaseAccessor dba, User user) throws SQLException {
        this.dba = dba;
        isAdmin = user.isAdmin();
        this.user = user;
        // Loads the list of products from the database and creates a new ProductListPage GUI
        List<Product> products = dba.loadProductsFromDB(user.isAdmin());
        browseProductPage = new ProductListPage(products,isAdmin);
        browseProductPage.setVisible(true);
        browseProductPage.getUpdateColumn().setCellEditor(new UpdateButtonEditor(new JCheckBox()));
        initListeners();
    }

    // Sets up the listeners for the buttons in the GUI
    private void initListeners() {
        browseProductPage.getAddButton().addActionListener((ActionEvent ae) -> {
            // Creates a new ProductPage GUI for adding a new product
            final ProductPage addProductPage = new ProductPage("Add");
            addProductPage.setVisible(true);
            addProductPage.getConfirmButton().addActionListener((ActionEvent event) -> {
                // Creates a new Product object using the information from GUI and adds that to the database
                Product product = readFromAddUpdatePage(addProductPage);
                dba.addProduct(product);
                addProductPage.dispose();
                // Updates the ProductListPage GUI and re-initializes event listeners with the new list of products
                browseProductPage.updateProductList(dba.loadProductsFromDB(user.isAdmin()));
                initListeners();
            });
        });
        // Event listener for the "Edit" button in the ProductListPage GUI
        TableColumn updateColumn = browseProductPage.getUpdateColumn();
        updateColumn.setCellEditor(new UpdateButtonEditor(new JCheckBox()));

    }

    // Reads the information enterred in a ProductPage GUI for adding/editing a product and returns a new Product object.
    private Product readFromAddUpdatePage(ProductPage page) {
        int id = page.getId();
        String name = page.getProductName();
        int inventory = page.getInventory();
        double price = page.getPrice();

        return new Product(id, name, inventory, price);
    }

    // Sets the fields in a ProductPage GUI to display the information for a given Product object.
    private void setProductPage(ProductPage page, Product product) {
        page.setId(product.getId());
        page.setProductName(product.getName());
        page.setPrice(product.getPrice());
        page.setInventory(product.getInventory());
    }

    // a custom cell editor for the update button in the browse product page
    class UpdateButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private Product product;
        int row;

        // constructor for the UpdateButtonEditor
        public UpdateButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                System.out.println(isAdmin ? "Edit" : "Order");
                // If user is admin, create a new ProductPage for editing the product, Otherwise, create a new ProductPage for ordering the product
                ProductPage productPage = new ProductPage(isAdmin ? "Edit" : "Order");
                setProductPage(productPage,product);
                productPage.getConfirmButton().addActionListener((ActionEvent ae) -> {
                    if(isAdmin){
                        // If the user is an admin, update the product in the database
                        Product product = readFromAddUpdatePage(productPage);
                        dba.updateProduct(product);
                    } else {
                        // If the user is not an admin, create an order for the product
                        int qty = productPage.getQty();
                        if(product.getInventory() < qty){
                            JOptionPane.showMessageDialog(productPage, "There's not enough inventory for your order.");
                        } else {
                            // Create the order and reduce the product inventory in the database
                            Order order = new Order(user,product,qty);
                            dba.reduceInventory(product,qty);
                            dba.addOrder(order);
                        }
                    }
                    // Update the product list in the browseProductPage and dispose the productPage
                    browseProductPage.updateProductList(dba.loadProductsFromDB(isAdmin));
                    productPage.dispose();
                    initListeners();
                });
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            // Get the selected product from the table
            product = ((ProductTableModel) table.getModel()).getProducts().get(row);
            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}
