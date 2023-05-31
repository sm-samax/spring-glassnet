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
		<div class="stack-container">
		<c:if test="${empty messages }"><h1>The in-box is empty</h1></c:if>
		<c:forEach items="${messages }" var="m">
			<div class="stack-element">
				<h3>${m.title }</h3>
				<p>${m.body}</p>
				<p>${m.date}</p>
				<form action="/glassnet/deleteMessage?id=${m.id}" method="post">
					<input type="submit" value="Delete" class="blue-btn">
				</form>
			</div>		
		</c:forEach>		
		</div>
	</div>
  </body>
</html>
