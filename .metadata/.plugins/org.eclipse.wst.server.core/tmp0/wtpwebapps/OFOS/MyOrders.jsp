<%@ page import="java.sql.*,java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Orders</title>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body class="sub_page">

<div class="container mt-5">
    <h2 class="text-center mb-4">My Orders</h2>

    <table class="table table-bordered table-dark text-center">
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Pizza</th>
                <th>Burger</th>
                <th>Pasta</th>
                <th>Fries</th>
                <th>Salad</th>
                <th>Order Date</th>
            </tr>
        </thead>
        <tbody>

<%
    String email = (String) session.getAttribute("email");

    if(email != null) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","apple");

            String sql = "SELECT * FROM orders WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
%>
                <tr>
                    <td><%= rs.getInt("id") %></td>
                    <td><%= rs.getInt("pizza_qty") %></td>
                    <td><%= rs.getInt("burger_qty") %></td>
                    <td><%= rs.getInt("pasta_qty") %></td>
                    <td><%= rs.getInt("fries_qty") %></td>
                    <td><%= rs.getInt("salad_qty") %></td>
                    <td><%= rs.getTimestamp("order_date") %></td>
                </tr>
<%
            }
            rs.close();
            ps.close();
            conn.close();
        } catch(Exception e) {
            out.println("Error: " + e.getMessage());
        }
    } else {
%>
        <tr>
            <td colspan="7">You are not logged in.</td>
        </tr>
<%
    }
%>

        </tbody>
    </table>

    <div class="text-center mt-4">
        <a href="index.jsp" class="btn btn-primary">Back to Home</a>
    </div>
</div>

</body>
</html>
