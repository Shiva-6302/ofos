package removeProduct;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import LoginServlet.DbConnection;

@WebServlet("/RemoveProduct")
public class RemoveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String foodIdStr = request.getParameter("productID");

        try {
            int foodId = Integer.parseInt(foodIdStr);

            try (Connection conn = DbConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement("DELETE FROM menu WHERE food_id = ?");
                ps.setInt(1, foodId);
                ps.executeUpdate();
            }

            response.sendRedirect("AdminDashBoard.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
