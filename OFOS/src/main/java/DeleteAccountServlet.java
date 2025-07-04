import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteAccount")
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public DeleteAccountServlet() {
		super();
	}
	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String username = "jdbc";
		String password = "apple";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,username,password);
			con.setAutoCommit(false);
			String deleteQueryCustomer = "delete from customer where user_id in (select user_id from users where email = ?)";
			pstmt = con.prepareStatement(deleteQueryCustomer);
			pstmt.setString(1, email);
			int rowsAffectedCustomer = pstmt.executeUpdate();
			con.commit();
			if(rowsAffectedCustomer > 0) {
				String deleteQueryUsers = "delete from users where email = ?";
				pstmt = con.prepareStatement(deleteQueryUsers);
				pstmt.setString(1, email);
				int rowsAffectedUsers = pstmt.executeUpdate();
				con.commit();
				if(rowsAffectedUsers > 0) {
					session.invalidate();
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
				    out.println("<script type = \"text/javascript\">");	
				    out.println("alert('your account has been deleted');");
				    out.println("window.location.href='registration.jsp';");
				    out.println("</script>");
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		
					
		}finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(con != null)
					con.close();
				con.setAutoCommit(true);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}