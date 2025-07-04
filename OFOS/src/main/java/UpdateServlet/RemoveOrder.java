package UpdateServlet;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/RemoveOrderServlet")
public class RemoveOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        
		 String orderID = request.getParameter("orderID");

	        Connection connection = null;
	        try {
	        	String url="jdbc:oracle:thin:@localhost:1521:xe";
	    		String username = "jdbc";
	    		String password = "apple";
	    		Class.forName("oracle.jdbc.driver.OracleDriver");
	    		 connection = DriverManager.getConnection(url, username, password);
	    		String deleteQuery = "delete from orders where OrderId = ?";
	    		
	    		PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
	    		 pstmt.setInt(1, Integer.parseInt(orderID));
	    		 
	             
	             int rowsDeleted = pstmt.executeUpdate();
	             
	             if (rowsDeleted > 0) {
	            	 response.sendRedirect("Staff.jsp"); 
	             } else {
	                 
	                 response.getWriter().write("error");
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
	             
	            
