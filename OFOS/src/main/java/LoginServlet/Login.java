package LoginServlet;
import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Login() {
		super();
	}
	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		 
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String username = "jdbc";
		String pass = "apple";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,username,pass);
			
			String sql = "select user_id ,type from users where email = ? and password = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2,password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String userType = rs.getString("type");
				HttpSession session = request.getSession();
				session.setAttribute("email", email);
				session.setAttribute("userType", userType);
				if("customer".equals(userType)) {
					String customerInfoQuery = "SELECT u.email, u.password, c.name, c.phone_number " +
	                           "FROM users u JOIN customer c ON u.user_id = c.user_id " +
	                           "WHERE u.email = ?";
					pstmt = con.prepareStatement(customerInfoQuery);
					pstmt.setString(1,email);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						String customerName = rs.getString("name");
						String customerEmail = rs.getString("email");
						String customerPhoneNumber = rs.getString("phone_number");
						String customerPassword = rs.getString("password");
						session.setAttribute("name", customerName);
                        
                        session.setAttribute("phone_number", customerPhoneNumber);
                        session.setAttribute("password", customerPassword);
                        response.sendRedirect("index.jsp");

						
					}else {
						
					}
					
					
					
					
					
				}else if ("admin".equals(userType)) {
					response.sendRedirect("Admin.jsp");
				}else if ("staff".equals(userType)) {
                    response.sendRedirect("Staff.jsp");
                } else if ("deliverer".equals(userType)) {
                    response.sendRedirect("delivery.jsp");
                } else {
                   
                }
            } else {
               
                response.sendRedirect("log.jsp"); 
            }
			
			
		}catch(Exception e) {
			e.printStackTrace();
					
		}finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(con != null)
					con.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
