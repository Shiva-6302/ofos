package LoginServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("log.jsp");
            return;
        }
        
        int userId = (int) session.getAttribute("userId");
        
        ArrayList<FoodItem> foodList = new ArrayList<>();
        
        try {
            Connection conn = DbConnection.getConnection();
            String sql = "SELECT food_name, price, image_url FROM menu LIMIT 10"; // first 10 items
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                foodList.add(new FoodItem(
                    rs.getString("food_name"),
                    rs.getDouble("price"),
                    rs.getString("image_url")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("foodList", foodList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("log.jsp");
        dispatcher.forward(request, response);
    }
}

