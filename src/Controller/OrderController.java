package Controller;

import DataConnection.DatabaseAccessor;
import GUI.OrderListPage;
import Model.Order;
import Model.User;

import java.sql.SQLException;
import java.util.List;

public class OrderController {

    private OrderListPage orderListPage;

    public OrderController(DatabaseAccessor dba, User user) throws SQLException {
        // loads orders from the database using the DatabaseAccessor object, either for all users if the user is an admin, or just for the specific user if the user is not an admin
        List<Order> orders = user.isAdmin() ? dba.loadOrdersFromDB() : dba.loadOrdersFromDB(user);
        orderListPage = new OrderListPage(user.isAdmin(),orders);
        orderListPage.setVisible(true); // sets the OrderListPage object as visible
    }

}
