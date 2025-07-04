<%@ page import="java.sql.*, java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <title>Search Food</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Search Food</h2>

    <form method="get" action="">
        <div class="form-group">
            <input type="text" name="keyword" class="form-control" placeholder="Enter food name to search..." required>
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <%
    String keyword = request.getParameter("keyword");
    if (keyword != null && !keyword.trim().isEmpty()) {
        Connection conn = LoginServlet.DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM menu WHERE LOWER(food_name) LIKE ?");
        ps.setString(1, "%" + keyword.toLowerCase() + "%");
        ResultSet rs = ps.executeQuery();
    %>

    <table class="table table-striped mt-4">
        <thead>
            <tr>
                <th>Food Image</th>
                <th>Food Name</th>
                <th>Price</th>
                <th>Order</th>
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
                <td><%= rs.getDouble("price") %></td>
<a href="AddToCart?foodId=<%= rs.getInt("food_id") %>" class="btn btn-success btn-sm">
    Add to Cart (ID: <%= rs.getInt("food_id") %>)
</a>
            </tr>
            <% }
            if (!found) { %>
                <tr><td colspan="4">No food found matching "<%= keyword %>".</td></tr>
            <% } %>
        </tbody>
    </table>

    <% } %>

</div>
</body>
</html>
