<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="User.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <% 
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
    %>
    <h2>Welcome <%= user.getUsername() %>!</h2>
    <p>Your ID: <%= user.getId() %></p>
    <p>Your Role: <%= user.getRole() %></p>
    <a href="Login.jsp">Logout</a>
</body>
</html>