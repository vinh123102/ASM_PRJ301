<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="User.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
    <style>
        /* Reset mặc định */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(45deg, #6ab7f5, #c2e9fb);
        }

        .welcome-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            text-align: center;
        }

        .welcome-container h2 {
            color: #2ecc71;
            font-size: 28px;
            font-weight: 600;
            margin-bottom: 20px;
        }

        .info-box {
            margin-bottom: 20px;
            padding: 15px;
            background: #f9f9f9;
            border-radius: 5px;
            text-align: left;
        }

        .info-box p {
            font-size: 16px;
            color: #333;
            margin: 5px 0;
        }

        .info-box p strong {
            color: #555;
            display: inline-block;
            width: 120px;
        }

        .logout-button {
            display: inline-block;
            padding: 10px 20px;
            background: #e74c3c;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 500;
            transition: background 0.3s ease;
        }

        .logout-button:hover {
            background: #c0392b;
        }
    </style>
</head>
<body>
    <% 
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
    %>
    <div class="welcome-container">
        <h2>Welcome <%= user.getUsername() %>!</h2>
        <div class="info-box">
            <p><strong>Your ID:</strong> <%= user.getId() %></p>
            <p><strong>Role:</strong> <%= user.getRole() %></p>
        </div>
        <a href="Login.jsp" class="logout-button">Logout</a>
    </div>
</body>
</html>