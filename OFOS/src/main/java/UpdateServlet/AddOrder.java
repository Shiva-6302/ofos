package UpdateServlet;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/AddOrderServlet")
public class AddOrder extends HttpServlet {
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        
	        String deliveryAddress = request.getParameter("deliveryAddress");
	        String phoneNumber = request.getParameter("phoneNumber");
	        String orderStatus = "Pending"; 
	        String[] productIDs = request.getParameterValues("productID[]");
	        String[] quantities = request.getParameterValues("quantity[]");

	        Connection connection = null;
	        try {
	        	String url="jdbc:oracle:thin:@localhost:1521:xe";
	    		String username = "jdbc";
	    		String password = "apple";
	    		Class.forName("oracle.jdbc.driver.OracleDriver");
	    		 connection = DriverManager.getConnection(url, username, password);
	    		String insertOrderQuery = "insert into orders (user_id,DeliveryAddress,PhoneNumber,OrderStatus) values(?,?,?,?)";
	    		int user_id = 8;
	    		PreparedStatement pstmt = connection.prepareStatement(insertOrderQuery);
	    		 pstmt.setInt(1, user_id);
	    		 pstmt.setString(2, deliveryAddress);
	    		 pstmt.setString(3, phoneNumber);
	    		 pstmt.setString(4, orderStatus);
	             
	             int orderInserted = pstmt.executeUpdate();
	             
	             if (orderInserted > 0) {
	            String selectOrderIDQuery = "select last_insert_id() as LastId";
	            PreparedStatement lastIDStatement = connection.prepareStatement(selectOrderIDQuery);
                ResultSet lastIDResult = lastIDStatement.executeQuery();
                
                int orderID = 0;
                if (lastIDResult.next()) {
                    orderID = lastIDResult.getInt("LastId");
                }
                String insertOrderItemsQuery = "INSERT INTO OrderItems (OrderID, ProductID, Quantity) VALUES (?, ?, ?)";
                PreparedStatement orderItemsStatement = connection.prepareStatement(insertOrderItemsQuery);
	             
	             for (int i = 0; i < productIDs.length; i++) {
	                    int productID = Integer.parseInt(productIDs[i]);
	                    int quantity = Integer.parseInt(quantities[i]);
	                    
	                    orderItemsStatement.setInt(1, orderID);
	                    orderItemsStatement.setInt(2, productID);
	                    orderItemsStatement.setInt(3, quantity);
	                    
	                    orderItemsStatement.addBatch();
	                }
	                
	                int[] itemsInserted = orderItemsStatement.executeBatch();
	                
	                boolean allItemsInserted = true;
	                for (int inserted : itemsInserted) {
	                    if (inserted <= 0) {
	                        allItemsInserted = false;
	                        break;
	                    }
	                }
	                if (allItemsInserted) {
	                    
	                    response.sendRedirect("Staff.jsp"); 
	                } else {
	                    
	                    response.getWriter().write("Failed to insert order items.");
	                }
	            } else {
	                
	                response.getWriter().write("Failed to insert order.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().write("Error: " + e.getMessage());
	        } finally {
	            if (connection != null) {
	                try {
	                    connection.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	        }


}
}
}