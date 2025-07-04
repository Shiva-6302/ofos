<%@ page import="java.util.*" %>
<%@ page import="dao.OrderDao" %>
<%@ page import="model.Order" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Staff Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Staff Dashboard</h2>

    <!-- Search Section -->
    <form method="get" action="Staff.jsp" class="form-inline mb-3">
        <input type="text" class="form-control mr-2" name="search" placeholder="Search by Order ID">
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <!-- Orders Table -->
    <h4>Existing Orders</h4>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Order ID</th>
               
                <th>Product</th>
                <th>Quantity</th>
            </tr>
        </thead>
        <tbody>
        <%
            String search = request.getParameter("search");
            try {
                List<Order> orders = OrderDao.searchOrders(search);
                if (orders.isEmpty()) {
        %>
            <tr><td colspan="6">No orders found</td></tr>
        <%
                } else {
                    for (Order o : orders) {
        %>
            <tr>
                <td><%= o.getOrderId() %></td>
                
                <td><%= o.getProduct() %></td>
                <td><%= o.getQuantity() %></td>
            </tr>
        <%
                    }
                }
            } catch (Exception e) {
                out.println("<tr><td colspan='6'>Error loading orders</td></tr>");
                e.printStackTrace();
            }
        %>
        </tbody>
    </table>

    
</div>
</body>
</html>
