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
      <a href="register" class="nav-btn-right">Sign in</a>
    </div>

    <div class="horizontal-container">
      <sf:form modelAttribute="user"  action="login"  method="post" class="login-form">
        <h2>Login</h2>
        <sf:input path="email" placeholder="Email" />
        <sf:password path="password" placeholder="Password" />
        <input class="blue-btn" type="submit" value="Continue" />
      </sf:form>

      <div class="vertical-container">
        <h1>Don't have an account?</h1>
        <a href="register" class="blue-btn">Registration</a>
      </div>
    </div>
  </body>
</html>
