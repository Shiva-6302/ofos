import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public Logout() {
	        super();
	 }
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			
			    HttpSession session = request.getSession();
			    session.invalidate(); 
			    response.sendRedirect("log.jsp"); 
			

		}
}
