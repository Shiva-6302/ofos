package UpdateServlet;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Update")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public UpdateProfile() {
        super();
    }
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 String newName = request.getParameter("name");
	        String newEmail = request.getParameter("email");
	        String newPhoneNumber = request.getParameter("phone_number");
	        String url="jdbc:oracle:thin:@localhost:1521:xe";
    		String username = "jdbc";
    		String password = "apple";

		
	        Connection con = null;
	        PreparedStatement pstmt = null;
	        HttpSession session = request.getSession();
	        String email = (String) session.getAttribute("email");
	        try {
	        	
	    		Class.forName("oracle.jdbc.driver.OracleDriver");
	    		 con= DriverManager.getConnection(url, username, password);
	    		String updateQuery = "update customer set name = ?,email = ?,phone_number = ?  where user_id in(select user_id from users where email = ? )";
	    		
	    		 pstmt = con.prepareStatement(updateQuery);
	    		 pstmt.setString(1, newName); 
	    		 pstmt.setString(2, newEmail);
	             pstmt.setString(3, newPhoneNumber);
	             pstmt.setString(4, email);

	             
	             int rowsAffected = pstmt.executeUpdate();

	             if (rowsAffected > 0) {
	                 
	                 session.setAttribute("name", newName);
	                 session.setAttribute("email", newEmail);
	                 session.setAttribute("phone_number", newPhoneNumber);
	                 
	                 
	                 response.setContentType("text/html");
	                 PrintWriter out = response.getWriter();
	                 out.println("<script type=\"text/javascript\">");
	                 out.println("alert('Your profile has been updated!');");
	                 out.println("window.location.href='UserProfile.jsp';");
	                 out.println("</script>");
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	           
	         } finally {
	        	 try {
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
	 
	             
	            

