package LoginServlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String foodIdStr = request.getParameter("foodId");
        String email = (String) request.getSession().getAttribute("email");

        if (foodIdStr == null || email == null) {
            response.sendRedirect("log.jsp");
            return;
        }

        int foodId = Integer.parseInt(foodIdStr);

        try (Connection conn = DbConnection.getConnection()) {
            if (conn != null) {
                // Fetch food details
                PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM menu WHERE food_id = ?");
                ps1.setInt(1, foodId);
                ResultSet rs = ps1.executeQuery();

                if (rs.next()) {
                    String foodName = rs.getString("food_name");
                    double price = rs.getDouble("price");

                    // Insert into cart table
                    PreparedStatement ps2 = conn.prepareStatement("INSERT INTO cart (email, food_name, price, quantity) VALUES (?, ?, ?, 1)");
                    ps2.setString(1, email);
                    ps2.setString(2, foodName);
                    ps2.setDouble(3, price);
                    ps2.executeUpdate();

                    // Update session cart list
                    HttpSession session = request.getSession();
                    List<Integer> cart = (List<Integer>) session.getAttribute("cart");
                    if (cart == null) {
                        cart = new ArrayList<>();
                    }
                    cart.add(foodId);
                    session.setAttribute("cart", cart);
                } else {
                    System.out.println("Invalid foodId: " + foodId);
                    response.sendRedirect("menu.jsp?error=invalidFood");
                    return;
                }
            }

            response.sendRedirect("cart.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
