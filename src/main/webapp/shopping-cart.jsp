<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="styles/index.css" />
    <link rel="stylesheet" href="styles/login.css" />
    <title>Glassnet</title>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
  </head>
  <body>
    <div class="nav-bar">
      <a href="index" class="nav-logo"
        ><img src="images/logo.png" alt="" />
        <p>Glassnet</p></a
      >
      <a href="products" class="nav-btn-right">Products</a>
      <a href="shopping-cart" class="nav-btn-right">Shopping cart</a>
      <sec:authorize access="isAuthenticated()">
      <a href="dashboard" class="nav-btn-right">Dashboard</a>
      <a href="logout" class="nav-btn-right">Logout</a>
      </sec:authorize>
      <sec:authorize access="!isAuthenticated()">
      <a href="register" class="nav-btn-right">Sign in</a>
      <a href="login" class="nav-btn-right">Login</a>
      </sec:authorize>
    </div>

	<div class="horizontal-container">
		<div class="stack-container">
		<c:if test="${empty products }"><h1>The shopping cart is empty</h1></c:if>
		<c:if test="${!empty products }"><div class="stack-element">
			<p>Sum: ${sum }$</p>
			<p>Number of products: ${productNumber}</p>
			<form action="checkout" method="post">
				<input type="submit" class="blue-btn" value="Checkout">
			</form>
			<form action="clearCart" method="post">
				<input type="submit" class="blue-btn" value="Clear">
			</form>
		</div></c:if>
		<c:forEach items="${products }" var="p">
			<div class="stack-element">
          			<img src="data:image/*;base64,${p.key.image.data}" alt="" />
		          	<p>${p.key.name}</p>
		          	<p>Price: ${p.key.price * p.value}$</p>
					<p>Amount: ${p.value}</p>
	        	</div>
		</c:forEach>		
		</div>
	</div>
  </body>
</html>
