<%@ page import="java.sql.*, java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <title>Menu</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Menu</h2>

    <form method="get" action="">
        <div class="form-group">
            <input type="text" name="keyword" class="form-control" placeholder="Enter food name to search...">
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <%
    Connection conn = LoginServlet.DbConnection.getConnection();
    String keyword = request.getParameter("keyword");
    PreparedStatement ps;
    if (keyword != null && !keyword.trim().isEmpty()) {
        ps = conn.prepareStatement("SELECT * FROM menu WHERE LOWER(food_name) LIKE ?");
        ps.setString(1, "%" + keyword.toLowerCase() + "%");
    } else {
        ps = conn.prepareStatement("SELECT * FROM menu");
    }
    ResultSet rs = ps.executeQuery();
    %>

    <table class="table table-striped mt-4">
        <thead>
            <tr>
                <th>Image</th>
                <th>Food Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
            boolean found = false;
            while (rs.next()) {
                found = true;
            %>
            <tr>
                <td><img src="<%= rs.getString("image_url") %>" width="80" height="80"></td>
                <td><%= rs.getString("food_name") %></td>
                <td><%= rs.getString("description") %></td>
                <td>&#8377;<%= rs.getDouble("price") %></td>
                <td><a href="AddToCart?foodId=<%= rs.getInt("food_id") %>" class="btn btn-success btn-sm">Add to Cart</a></td>
            </tr>
            <% }
            if (!found) { %>
                <tr><td colspan="5">No food found.</td></tr>
            <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
