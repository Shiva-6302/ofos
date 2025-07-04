package LoginServlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.*;

@WebServlet("/CheckOut")
public class CheckOutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            response.sendRedirect("log.jsp");
            return;
        }

        int pizzaQty = 0, burgerQty = 0, pastaQty = 0, friesQty = 0, saladQty = 0;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "jdbc", "apple");

            System.out.println("Database connected inside CheckOutServlet!");

          
            ps = conn.prepareStatement("SELECT food_name, quantity FROM cart WHERE email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();

            boolean cartNotEmpty = false;
            while (rs.next()) {
                cartNotEmpty = true;
                String foodName = rs.getString("food_name");
                int quantity = rs.getInt("quantity");

                System.out.println("Cart Item: " + foodName + " x " + quantity);

                switch (foodName.toLowerCase().trim()) {
                    case "pizza": pizzaQty += quantity; break;
                    case "burger": burgerQty += quantity; break;
                    case "pasta": pastaQty += quantity; break;
                    case "fries": friesQty += quantity; break;
                    case "salad": saladQty += quantity; break;
                    default:
                        System.out.println("Unknown food item: " + foodName);
                        break;
                }
            }

            if (!cartNotEmpty) {
                System.out.println("Cart is empty in database! Redirecting to cart.jsp");
                response.sendRedirect("cart.jsp");
                return;
            }

         
            String insertOrder = "INSERT INTO orders (id, email, pizza_qty, burger_qty, pasta_qty, fries_qty, salad_qty, order_date) " +
                                 "VALUES (orders_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, SYSDATE)";

            ps = conn.prepareStatement(insertOrder);
            ps.setString(1, email);
            ps.setInt(2, pizzaQty);
            ps.setInt(3, burgerQty);
            ps.setInt(4, pastaQty);
            ps.setInt(5, friesQty);
            ps.setInt(6, saladQty);

            int rows = ps.executeUpdate();
            System.out.println("Order inserted! Rows affected: " + rows);

         
            ps = conn.prepareStatement("DELETE FROM cart WHERE email = ?");
            ps.setString(1, email);
            ps.executeUpdate();
            System.out.println("Cart cleared for user: " + email);

            
            response.sendRedirect("OrderSuccess.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error placing order: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
