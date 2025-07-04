import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReservationServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>Direct access to ReservationServlet is not allowed. Please book through the form.</h3>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            response.sendRedirect("log.jsp");
            return;
        }

        String name = request.getParameter("name");
        String reservationDateStr = request.getParameter("reservation_date");
        
        java.sql.Date reservationDate = null;
        try {
            reservationDate = java.sql.Date.valueOf(reservationDateStr);
        } catch (IllegalArgumentException e) {
            out.println("<h3>Invalid date format. Please enter date as yyyy-mm-dd.</h3>");
            return;
        }

        int persons = Integer.parseInt(request.getParameter("persons"));
        String phone = request.getParameter("phone");

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUsername = "jdbc";
        String dbPassword = "apple";

        Connection con = null;
        PreparedStatement selectStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet resultSet = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, dbUsername, dbPassword);

          
            String selectUserQuery = "SELECT USER_ID FROM users WHERE EMAIL = ?";
            selectStmt = con.prepareStatement(selectUserQuery);
            selectStmt.setString(1, email);
            resultSet = selectStmt.executeQuery();

            int userUID = 0;
            if (resultSet.next()) {
                userUID = resultSet.getInt("USER_ID");
            } else {
                out.println("<script>alert('User not found! Please login again.');window.location.href='log.jsp';</script>");
                return;
            }

            
            String insertQuery = "INSERT INTO reservations (reservation_id, name, phone_number, email, persons, reservation_date, user_id) " +
                                 "VALUES (reservation_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
            insertStmt = con.prepareStatement(insertQuery);
            insertStmt.setString(1, name);
            insertStmt.setString(2, phone);
            insertStmt.setString(3, email);
            insertStmt.setInt(4, persons);
            insertStmt.setDate(5, reservationDate);
            insertStmt.setInt(6, userUID);

            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected > 0) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Booking confirmed!');");
                out.println("window.history.back();");
                out.println("</script>");
            } else {
                response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStmt != null) selectStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
