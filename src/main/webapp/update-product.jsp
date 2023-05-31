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
      <a href="dashboard" class="nav-btn-right">Dashboard</a>
      <a href="logout" class="nav-btn-right">Logout</a>
    </div>
	
		<div class="horizontal-container">
      	<sf:form modelAttribute="product" action="/glassnet/updateProduct" method="post" class="login-form" enctype="multipart/form-data">
	        <h2>Update product</h2>
    	    <sf:input path="name" placeholder="${product.name}"/>
        	<sf:input path="price" placeholder="${product.price}"/>
        	<input type="file" accept="image/*" name="image">
        	<input class="blue-btn" type="submit" value="Update" />
      	</sf:form>
      	</div>
  </body>
</html>
