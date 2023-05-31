<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="styles/index.css" />
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
      <a href="messages" class="nav-btn-right">Messages</a>
      <a href="dashboard" class="nav-btn-right">Dashboard</a>
      <a href="logout" class="nav-btn-right">Logout</a>
      </sec:authorize>
      <sec:authorize access="!isAuthenticated()">
      <a href="register" class="nav-btn-right">Sign in</a>
      <a href="login" class="nav-btn-right">Login</a>
      </sec:authorize>
    </div>

    <div class="main-container">
      <form action="/glassnet/search" method="post">
      	<div class="filter-container">
        <div class="sort-container">
          <label for="select">Sort by:</label>
          <select name="sort" id="">
            <option value="Name">Name</option>
            <option value="Price">Price</option>
            <option value="Release date">Release date</option>
          </select>
          <label for="checkbox">Descending</label>
          <input type="checkbox" name="desc">
        </div>
        <div class="search-bar">
          <input name="name" type="text" placeholder="Name of product" />
          <input type="submit" value="Apply" />
        </div>
      </div>
      </form>

      <div class="wrapper">
      </div>

      <div class="grid-container">
      <c:forEach items="${products }" var="p">
		<div class="grid-element">
          <img src="data:image/*;base64,${p.image.data}" alt="Not found" />
          <div class="inline-wrapper">
            <p>${p.name }</p>
            <p>${p.price}$</p>
          </div>
          	<form action="/glassnet/puttocart?id=${p.id}" method="post" class="inline-wrapper">
          		<input type="number" name="amount" placeholder="1">
          		<input type="submit" value="Put to cart" class="small-blue-btn">
          	</form>
        </div>      
      </c:forEach>
      </div>

      <div class="wrapper">
        <div class="paging-container">
          <a href="products?page=0" class="paging-btn">1</a>
          <a href="products?page=1" class="paging-btn">2</a>
          <a href="products?page=2" class="paging-btn">3</a>
          <a href="products?page=3" class="paging-btn">4</a>
        </div>
      </div>
    </div>
  </body>
</html>
