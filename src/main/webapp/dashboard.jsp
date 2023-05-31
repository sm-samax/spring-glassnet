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
<link rel="stylesheet" href="styles/dashboard.css" />
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
		<div class="profile">
			<form id="avatar-upload" action="/glassnet/uploadAvatar"
				method="post" enctype="multipart/form-data">
				<label for="avatar-input"> 
					<c:if test="${empty avatar }">
						<img src="images/default-avatar.jpg" alt="Not found" id="avatar-img" />
					</c:if> 
					<c:if test="${not empty avatar }">
						<img src="data:image/*;base64,${avatar.data}" alt="Not found" id="avatar-img" />
					</c:if>
				</label>
				<input type="file" name="file" accept="image/*" id="avatar-input" />
				<input type="submit" value="Save avatar" class="blue-btn">
			</form>
			<sf:form modelAttribute="user" action="/glassnet/updateUser"
				method="post" class="vertical-box">
				<label for="text">Username:</label>
				<sf:input path="username" />
				<label for="text">Email:</label>
				<sf:input path="email" />
				<label for="password">Password:</label>
				<sf:password path="password" />
				<label for="password">Confirm password:</label>
				<sf:password path="confirmPassword" />
				<input type="submit" class="blue-btn" value="Update" />
			</sf:form>
		</div>

		<div class="profile">
			<div class="vertical-box-justify">
				<a href="messages" class="blue-btn">Messages</a>
				<a href="shopping-cart" class="blue-btn">Shopping cart</a>
				<a href="provider-menu" class="blue-btn">Provider menu</a> 
			</div>
		</div>
	</div>
	
	<script>
      var input = document.getElementById("avatar-input");
      var avatar = document.getElementById("avatar-img");
      input.onchange = (evt) => {
        const [file] = input.files;
        if (file) {
          avatar.src = URL.createObjectURL(file);
        }
      };
    </script>
</body>
</html>
