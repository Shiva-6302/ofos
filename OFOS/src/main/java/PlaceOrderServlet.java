
  

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/PlaceOrderServlet")
public class PlaceOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("email");

        String pizzaQty = request.getParameter("item_Pizza");
        String burgerQty = request.getParameter("item_Burger");
        String pastaQty = request.getParameter("item_Pasta");
        String friesQty = request.getParameter("item_Fries");
        String saladQty = request.getParameter("item_Salad");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","apple");

            String sql = "INSERT INTO orders (email, pizza_qty, burger_qty, pasta_qty, fries_qty, salad_qty) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userEmail);
            ps.setString(2, pizzaQty != null && !pizzaQty.isEmpty() ? pizzaQty : "0");
            ps.setString(3, burgerQty != null && !burgerQty.isEmpty() ? burgerQty : "0");
            ps.setString(4, pastaQty != null && !pastaQty.isEmpty() ? pastaQty : "0");
            ps.setString(5, friesQty != null && !friesQty.isEmpty() ? friesQty : "0");
            ps.setString(6, saladQty != null && !saladQty.isEmpty() ? saladQty : "0");

            int i = ps.executeUpdate();

            if (i > 0) {
                response.sendRedirect("OrderSuccess.jsp");
            } else {
                response.getWriter().println("Order Failed. Please try again.");
            }

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}

