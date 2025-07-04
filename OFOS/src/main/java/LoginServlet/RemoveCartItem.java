package LoginServlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RemoveCartItem")
public class RemoveCartItem extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection conn = DbConnection.getConnection();
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cart WHERE id = ?");
            ps.setInt(1, itemId);
            ps.executeUpdate();
            
            response.sendRedirect("cart.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}

