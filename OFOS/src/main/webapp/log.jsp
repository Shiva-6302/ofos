<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="LoginServlet.FoodItem" %>

<%
String userType = (String) session.getAttribute("userType");

if ("customer".equals(userType)) {
    response.sendRedirect("log.jsp");
} else if ("admin".equals(userType)) {
    response.sendRedirect("log.jsp");
} else if ("vendor".equals(userType)) {
    response.sendRedirect("log.jsp");
}
%>

<!DOCTYPE html>
<html>
<head>
	<title>Login Form</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!-- CSS Links -->
	<link rel="stylesheet" type="text/css" href="css/loginStyle.css">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

	<!-- Optional JS Links -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>

	<style>
		.menu-container { padding: 20px; display: flex; flex-wrap: wrap; gap: 20px; justify-content: center; }
		.food-card { border: 1px solid #ccc; border-radius: 10px; width: 250px; padding: 10px; text-align: center; box-shadow: 2px 2px 8px rgba(0,0,0,0.1); margin-top:20px; }
		.food-card img { width: 100%; height: 150px; object-fit: cover; border-radius: 10px; }
		.footer { background-color: #333; color: white; text-align: center; padding: 10px; margin-top: 20px; }
	</style>
</head>

<body>

<!-- Login Form Section -->
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-6 col-md-6 d-none d-md-block image-container"></div>

		<div class="col-lg-6 col-md-6 form-container">
			<div class="col-lg-8 col-md-12 col-sm-9 col-xs-12 form-box text-center">
				<div class="logo mb-3">
					<img src="images/favicon.png" width="50px">
				</div>
				<div class="heading mb-4">
					<h4>Login into your account</h4>
				</div>

				<form method="post" action="LoginServlet">
					<div class="form-input">
						<span><i class="fa fa-envelope"></i></span>
						<input type="email" name="email" placeholder="Email Address" required>
					</div>
					<div class="form-input">
						<span><i class="fa fa-lock"></i></span>
						<input type="password" name="password" placeholder="Password" required>
					</div>

					<div class="row mb-3">
						<div class="col-6 d-flex">
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="cb1">
								<label class="custom-control-label text-white" for="cb1">Remember me</label>
							</div>
						</div>
						<div class="col-6 text-right">
							<a href="forget.html" class="forget-link">Forget Password?</a>
						</div>
					</div>

					<div class="text-left mb-3">
						<button type="submit" class="btn">Login</button>
					</div>

					<div class="text-center mb-2">
						<div class="mb-3" style="color: #777">or login with</div>
						<a href="https://www.facebook.com/login/" class="btn btn-social btn-facebook">Facebook</a>
						<a href="https://accounts.google.com/v3/signin/identifier" class="btn btn-social btn-google">Google</a>
						<a href="https://x.com/i/flow/login" class="btn btn-social btn-twitter">Twitter</a>
					</div>

					<div style="color: #777">Don't have an account?
						<a href="registration.jsp" class="register-link">Register here</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- Food Menu Section after login (Optional display) -->
<div class="menu-container">
<%
	ArrayList<FoodItem> foodList = (ArrayList<FoodItem>) request.getAttribute("foodList");
	if (foodList != null && foodList.size() > 0) {
		for (FoodItem food : foodList) {
%>
		<div class="food-card">
			<img src="<%= food.getImageUrl() %>" alt="Food Image" />
			<h3><%= food.getName() %></h3>
			<p>Price: ₹<%= food.getPrice() %></p>
			<a href="FoodDetails.jsp?foodName=<%= food.getName() %>">Read More</a>
		</div>
<%
		}
	} else {
%>
		<h2>No food items available.</h2>
<%
	}
%>
</div>

<!-- Footer -->
<div class="footer">
	<p>&copy; 2025 Online Food Ordering System. All rights reserved.</p>
</div>

</body>
</html>
