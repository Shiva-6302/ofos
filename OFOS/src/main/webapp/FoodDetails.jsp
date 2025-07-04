<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, LoginServlet.DbConnection" %>

<%
Connection conn = null;
PreparedStatement ps = null;
ResultSet rs = null;

try {
    conn = DbConnection.getConnection();
    String query = "SELECT * FROM MENU WHERE IS_AVAILABLE = 'Y'";
    ps = conn.prepareStatement(query);
    rs = ps.executeQuery();

    while (rs.next()) {
%>
        <!-- Display food item details -->
        <div>
            <h3><%= rs.getString("FOOD_NAME") %></h3>
            <p><%= rs.getString("DESCRIPTION") %></p>
            <p>Price: ₹<%= rs.getDouble("PRICE") %></p>
            <img src="<%= rs.getString("IMAGE_URL") %>" width="150px" height="100px"/>
        </div>
<%
    }
} catch (Exception e) {
    e.printStackTrace();
} finally {
    if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
    if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
    if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
}
%>

<a href="Home">Back to Menu</a>
