<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
      <sec:authorize access="isAuthenticated()">
      <a href="dashboard" class="nav-btn-right">Dashboard</a>
      <a href="logout" class="nav-btn-right">Logout</a>
      </sec:authorize>
      <sec:authorize access="!isAuthenticated()">
      <a href="register" class="nav-btn-right">Sign in</a>
      <a href="login" class="nav-btn-right">Login</a>
      </sec:authorize>
    </div>

    <div class="vertical-container"></div>
    <div class="horizontal-container">
      <h1 class="regards">Welcome in Glassnet!</h1>
    </div>
    <div class="horizontal-container">
      <a href="products" class="option-btn">
        <div class="vert-container">
          <img src="images/buying.png" alt="" />
          <h1>Find your glasses!</h1>
        </div>
      </a>

      
      <a href="register" class="option-btn">
        <div class="vert-container">
          <img src="images/doctor.png" alt="" />
          <h1>Become Glassnet expert!</h1>
        </div>
      </a>
    </div>
  </body>
</html>
