<%@ page import="java.sql.*, java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
String email = (String) session.getAttribute("email");
if (email == null) {
    response.sendRedirect("log.jsp");
    return;
}

Connection conn = LoginServlet.DbConnection.getConnection();
PreparedStatement ps = conn.prepareStatement("SELECT * FROM cart WHERE email = ?");
ps.setString(1, email);
ResultSet rs = ps.executeQuery();
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Cart</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Shopping Cart</h2>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Food Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
            double totalPrice = 0;
            while (rs.next()) {
                totalPrice += rs.getDouble("price") * rs.getInt("quantity");
            %>
            <tr>
                <td><%= rs.getString("food_name") %></td>
                <td><%= rs.getDouble("price") %></td>
                <td><%= rs.getInt("quantity") %></td>
                <td>
                    <a href="RemoveCartItem?itemId=<%= rs.getInt("id") %>" class="btn btn-danger btn-sm">Remove</a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <h4>Total Price: <%= totalPrice %></h4>

<form action="CheckOut" method="post">
    <button type="submit" class="btn btn-success">Proceed to Checkout</button>
</form>
</div>
</body>
</html>
