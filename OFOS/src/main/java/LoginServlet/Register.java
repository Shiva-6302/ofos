package LoginServlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Register() {
        super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.getWriter().append("Served at :").append(request.getContextPath());
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname=request.getParameter("name");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String password=request.getParameter("password");
		String address=request.getParameter("address");
		
		Member member=new Member(uname,email, phone,password,address);
		RegisterDao rdao=new RegisterDao();
		String result = rdao.insert(member);

		if (result.equals("Data Entered Successfully")) {
			response.sendRedirect(request.getContextPath() + "/log.jsp");
  // now this works with servlet context
		} else {
		    response.getWriter().println(result);
		}

		
	}

}
