

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ChangePassword")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ChangePasswordServlet () {
        super();
    }
	@SuppressWarnings("resource")
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String currentPassword = request.getParameter("current_password");
        String newPassword = request.getParameter("new_password");
        String repeatNewPassword = request.getParameter("repeat_new_password");

	        String url="jdbc:oracle:thin:@localhost:1521:xe";
    		String username = "jdbc";
    		String password = "apple";

		
	        Connection con = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        try {
	        	
	    		Class.forName("oracle.jdbc.driver.OracleDriver");
	    		 con= DriverManager.getConnection(url, username, password);
	    		 String sql = "SELECT password FROM users WHERE email = ?";
	             pstmt = con.prepareStatement(sql);
	             pstmt.setString(1, email);

	              rs = pstmt.executeQuery();
	             
	             if (rs.next()) {
	            	 String storedPassword = rs.getString("password");
	            	 if (currentPassword.equals(storedPassword) && newPassword.equals(repeatNewPassword)) {
	                     
	                     String updateQuery = "UPDATE Users SET password = ? WHERE email = ?";
	                     pstmt = con.prepareStatement(updateQuery);
	                     pstmt.setString(1, newPassword);
	                     pstmt.setString(2, email);

	                     pstmt.executeUpdate();

	                     
	                     response.setContentType("text/html");
	                     PrintWriter out = response.getWriter();
	                     out.println("<script>alert('Password changed successfully!'); window.location='UserProfile.jsp';</script>");
	                 } else {
	               
	                     response.setContentType("text/html");
	                     PrintWriter out = response.getWriter();
	                     out.println("<script>alert('Password change failed. Please make sure the new passwords match and the current password is correct.'); window.location='UpdateProfile.html#account-change-password';</script>");
	                 }
	             } else {
	                 
	                 response.sendRedirect("log.jsp"); 
	             }
	             

	             
	            
	             
	            
	         } catch (Exception e) {
	             e.printStackTrace();
	           
	         } finally {
	        	 try {
	        		 if (rs != null)
	                     rs.close();
	        		 if (pstmt != null)
	                     pstmt.close();
	                 if (con != null)
	                     con.close();
	        	 
	             
	                 } catch (SQLException e) {
	                     e.printStackTrace();
	                 }
	             }
	         }
	     }
	 
	             
	            




