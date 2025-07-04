<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%
if (session.getAttribute("email") == null) {
    response.sendRedirect("log.jsp");
    return;
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/UprofileStyle.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container light-style flex-grow-1 container-p-y">
    <h4 class="font-weight-bold py-3 mb-4">Admin Dashboard</h4>
    <div class="row">
        <nav id="sidebar" class="col-md-3 pt-0">
            <div class="list-group list-group-flush account-settings-links">
                <a class="list-group-item list-group-item-action active" data-toggle="list" href="#products">Products</a>
                <a class="list-group-item list-group-item-action" data-toggle="list" href="#add-product">Add Product</a>
            </div>
        </nav>
        <div class="col-md-9">
            <div class="tab-content">
                <!-- Products Tab -->
                <div class="tab-pane fade active show" id="products">
                    <h4 class="font-weight-bold py-3 mb-4">Product Management</h4>
                    <div class="form-group">
                        <form method="post" action="">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Search by Food ID" name="searchProductID">
                                <div class="input-group-append">
                                    <button class="btn btn-primary" type="submit">Search</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- Menu Table -->
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Food ID</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Image URL</th>
                            <th>Remove</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            Connection connection = null;
                            try {
                                Class.forName("oracle.jdbc.driver.OracleDriver");
                                connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "jdbc", "apple");

                                String searchProductID = request.getParameter("searchProductID");
                                String query = "SELECT * FROM menu";
                                if (searchProductID != null && !searchProductID.trim().isEmpty()) {
                                    query += " WHERE food_id = ?";
                                }

                                PreparedStatement stmt = connection.prepareStatement(query);
                                if (searchProductID != null && !searchProductID.trim().isEmpty()) {
                                    stmt.setInt(1, Integer.parseInt(searchProductID));
                                }

                                ResultSet rs = stmt.executeQuery();

                                while (rs.next()) {
                        %>
                        <tr>
                            <td><%= rs.getInt("food_id") %></td>
                            <td><%= rs.getString("food_name") %></td>
                            <td><%= rs.getDouble("price") %></td>
                            <td><%= rs.getString("image_url") %></td>
                            <td>
                                <form method="post" action="RemoveProduct">
                                    <input type="hidden" name="productID" value="<%= rs.getInt("food_id") %>">
                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                </form>
                            </td>
                        </tr>
                        <%
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (connection != null) connection.close();
                            }
                        %>
                        </tbody>
                    </table>
                </div>

                <!-- Add Product Tab -->
                <div class="tab-pane fade" id="add-product">
                    <form method="post" action="AddProductServlet">
                        <div class="form-group">
                            <label>Product Name</label>
                            <input type="text" class="form-control" name="name" required>
                        </div>
                        <div class="form-group">
                            <label>Price</label>
                            <input type="number" step="0.01" class="form-control" name="price" required>
                        </div>
                        <div class="form-group">
                            <label>Image URL</label>
                            <input type="text" class="form-control" name="imageLocation" required>
                        </div>
                        <div class="text-right">
                            <button type="submit" class="btn btn-success">Add Product</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Logout Button -->
    <div class="text-right mt-3">
        <form action="Logout" method="post">
            <button class="btn btn-danger">Logout</button>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
