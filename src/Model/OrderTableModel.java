package Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrderTableModel extends AbstractTableModel {
    private List<Order> orders;
    private String[] columnNames;

    private boolean isAdmin;

    public OrderTableModel(List<Order> orders, boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.orders = orders;
        if (isAdmin)   // Set column names based on whether the user is an admin or not
            columnNames = new String[]{"OrderID", "ProductName", "CustomerName", "Price", "Quantity", "TotalPrice", "Status"};
        else columnNames = new String[]{"OrderID", "ProductName", "Price", "Quantity", "TotalPrice", "Status"};
    }

    // Get the number of rows in the table
    public int getRowCount() {
        return orders.size();
    }

    // Get the number of columns in the table
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // Get the name of a column
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    // Determine if a cell is editable
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    // Get the value of a cell
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        // If the user is an admin, return different values based on the column index
        if (isAdmin) {
            switch (columnIndex) {
                case 0:
                    return order.getId();
                case 1:
                    return order.getProductName();
                case 2:
                    return order.getCustomerName();
                case 3:
                    return order.getPrice();
                case 4:
                    return order.getQty();
                case 5:
                    return order.getTotalPrice();
                default:
                    return order.isComplete() ? "Complete" : "Incomplete";
            }
        } else {// If the user is not an admin, return different values based on the column index
            switch (columnIndex) {
                case 0:
                    return order.getId();
                case 1:
                    return order.getProductName();
                case 2:
                    return order.getPrice();
                case 3:
                    return order.getQty();
                case 4:
                    return order.getTotalPrice();
                default:
                    return order.isComplete() ? "Complete" : "Incomplete";
            }
        }
    }
}
