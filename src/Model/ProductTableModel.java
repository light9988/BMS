package Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private List<Product> products;
    private String[] columnNames;
    private boolean isAdmin;

    public ProductTableModel(List<Product> products, boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.products = products;
        if(isAdmin) columnNames = new String[] {"ID", "Name", "Inventory", "Price", "Update"};
        else columnNames =new String[] {"ID", "Name", "Inventory", "Price", "Order"};
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getId();
            case 1:
                return product.getName();
            case 2:
                return product.getInventory();
            case 3:
                return product.getPrice();
            default:
                return isAdmin ? "Update" : "Order";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;// The last column is editable. If the user is an admin, the last column is "Update" and can be edited. If the user is not an admin, the last column is "Order" and can be edited.
    }

    public List<Product> getProducts() {
        return products;
    }
}
