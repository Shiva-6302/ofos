package removeProduct;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import LoginServlet.DbConnection;

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String foodName = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String imageLocation = request.getParameter("imageLocation");

        try {
            double price = Double.parseDouble(priceStr);

            try (Connection conn = DbConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO menu (food_name, price, image_url) VALUES (?, ?, ?)");
                ps.setString(1, foodName);
                ps.setDouble(2, price);
                ps.setString(3, imageLocation);
                ps.executeUpdate();
            }

            response.sendRedirect("AdminDashBoard.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
