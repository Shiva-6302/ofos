package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Order;

public class OrderDao {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "jdbc";
    private static final String DB_PASSWORD = "apple";

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAvailableMenuItems() {
        List<String> items = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement("SELECT FOOD_NAME FROM menu WHERE IS_AVAILABLE='Y'");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(rs.getString("FOOD_NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static List<Order> searchOrders(String orderId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        String query = "SELECT * FROM orders";
        if (orderId != null && !orderId.isEmpty()) {
            query += " WHERE ID = ?";
        }
        PreparedStatement ps = conn.prepareStatement(query);
        if (orderId != null && !orderId.isEmpty()) {
            ps.setInt(1, Integer.parseInt(orderId));
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");

            String[] items = {"Pizza", "Burger", "Pasta", "Fries", "Salad"};
            String[] cols = {"PIZZA_QTY", "BURGER_QTY", "PASTA_QTY", "FRIES_QTY", "SALAD_QTY"};

            for (int i = 0; i < items.length; i++) {
                int qty = rs.getInt(cols[i]);
                if (qty > 0) {
                    orders.add(new Order(id, items[i], qty));
                }
            }
        }

        rs.close();
        ps.close();
        conn.close();
        return orders;
    }
}
