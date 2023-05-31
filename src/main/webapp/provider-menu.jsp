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

	<c:if test="${ isProvider eq false}">
    	<div class="horizontal-container">
    		<sf:form modelAttribute="provider"  action="/glassnet/becomeProvider"  method="post" class="login-form">
	        <h2>Become provider</h2>
    	    <sf:input path="companyName" placeholder="Company name" />
        	<sf:input path="address.state" placeholder="State" />
        	<sf:input path="address.province" placeholder="Province" />
        	<sf:input path="address.city" placeholder="City" />
        	<sf:input path="address.zip" placeholder="Zip" />
        	<sf:input path="address.street" placeholder="Street" />
        	<sf:input path="address.streetNumber" placeholder="Street number" />
        	<input class="blue-btn" type="submit" value="Forward" />
      	</sf:form>
    </div>
    </c:if>
	
	<c:if test="${ isProvider eq true}">
		<div class="horizontal-container">		
		
      	<sf:form modelAttribute="product"  action="/glassnet/addProduct"  method="post" class="login-form" enctype="multipart/form-data">
	        <h2>Create product</h2>
    	    <sf:input path="name" placeholder="Product name*" />
        	<sf:input path="price" placeholder="Price*" />
        	<input type="file" accept="image/*" name="image">
        	<input class="blue-btn" type="submit" value="Save" />
      	</sf:form>
      	</div>
      	<div class="stack-container">
      		<div class="stack-element">
      			<form action="salesReport" method="post">
					<input type="submit" class="blue-btn" value="Sales report">		
				</form>
      		</div>
      		<c:forEach items="${products }" var="p">
      			<div class="stack-element">
          			<img src="data:image/*;base64,${p.image.data}" alt="" />
		          	<p>${p.name}</p>
		          	<p>Price: ${p.price}$</p>
		          	<a href="/glassnet/updateProduct?id=${p.id}" class="blue-btn">Edit</a>
		          	<a href="/glassnet/deleteProduct?id=${p.id}" class="blue-btn">Delete</a>
	        	</div>
      		</c:forEach>
      	</div>
	</c:if>
  </body>
</html>
