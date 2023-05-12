package Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CustomerTableModel extends AbstractTableModel {
    private List<User> customers; // list of customers
    private String[] columnNames; // column names for the table

    // Constructs a new CustomerTableModel with the given list of customers.
    public CustomerTableModel(List<User> customers) {
        this.customers = customers;
        columnNames = new String[] {"ID", "Username", "First Name", "Last Name", "Address", "Email", "Phone"};
    }

    @Override
    public int getRowCount() {
        return customers.size();// returns the number of rows in the table
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;// returns the number of columns in the table
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];// returns the name of the specified column
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User customer = customers.get(rowIndex);// gets the customer at the specified row index
        switch (columnIndex) {// returns the value of the specified column for the customer
            case 0:
                return customer.getId();
            case 1:
                return customer.getUserName();
            case 2:
                return customer.getFirstName();
            case 3:
                return customer.getLastName();
            case 4:
                return customer.getAddress();
            case 5:
                return customer.getEmail();
            default:
                return customer.getPhone();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;// disables cell editing
    }
}
