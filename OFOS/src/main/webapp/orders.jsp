<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
if (session.getAttribute("email") == null) {
    response.sendRedirect("log.jsp");
}
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Now - SG</title>

    <!-- bootstrap core css -->
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="sub_page">

<!-- header section can be included if you want -->

<section class="order_section layout_padding">
    <div class="container">
        <div class="heading_container">
            <h2>Order Your Favorite Items</h2>
        </div>

        <form action="PlaceOrderServlet" method="post">
            <div class="row">

                <!-- Example Item 1 -->
                <div class="col-md-4">
                    <div class="card p-3 mb-4">
                        <h5>Pizza</h5>
                        <p>Delicious cheese pizza.</p>
                        <input type="number" name="item_Pizza" class="form-control" placeholder="Quantity" min="0">
                    </div>
                </div>

                <!-- Example Item 2 -->
                <div class="col-md-4">
                    <div class="card p-3 mb-4">
                        <h5>Burger</h5>
                        <p>Juicy beef burger.</p>
                        <input type="number" name="item_Burger" class="form-control" placeholder="Quantity" min="0">
                    </div>
                </div>

                <!-- Example Item 3 -->
                <div class="col-md-4">
                    <div class="card p-3 mb-4">
                        <h5>Pasta</h5>
                        <p>Creamy Alfredo pasta.</p>
                        <input type="number" name="item_Pasta" class="form-control" placeholder="Quantity" min="0">
                    </div>
                </div>

                <!-- Example Item 4 -->
                <div class="col-md-4">
                    <div class="card p-3 mb-4">
                        <h5>French Fries</h5>
                        <p>Crispy golden fries.</p>
                        <input type="number" name="item_Fries" class="form-control" placeholder="Quantity" min="0">
                    </div>
                </div>

                <!-- Example Item 5 -->
                <div class="col-md-4">
                    <div class="card p-3 mb-4">
                        <h5>Salad</h5>
                        <p>Fresh green salad.</p>
                        <input type="number" name="item_Salad" class="form-control" placeholder="Quantity" min="0">
                    </div>
                </div>

            </div>

            <div class="btn_box text-center mt-4">
                <button type="submit" class="btn btn-primary">
                    Place Order
                </button>
            </div>
        </form>

    </div>
</section>

<!-- footer section can be included if you want -->

<!-- jQery -->
<script src="js/jquery-3.4.1.min.js"></script>
<!-- bootstrap js -->
<script src="js/bootstrap.js"></script>
</body>
</html>
