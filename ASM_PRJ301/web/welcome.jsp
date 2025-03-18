<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="User.User" %>
<%@ page import="User.Employee" %>
<%@ page import="DAL.EmployeeDBContext" %>
<%@ page import="DAL.LoginDBContext" %>
<%@ page import="java.util.List" %>
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
        .employee-list {
            margin-top: 30px;
            text-align: center;
        }

        .employee-list h3 {
            color: #333;
            font-size: 20px;
            margin-bottom: 15px;
            text-align: center;
        }

        .employee-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center; 
            align-items: flex-start; 
        }

        .employee-card {
            background: #f9f9f9;
            border-radius: 8px;
            padding: 15px;
            width: 250px;
            box-shadow: 0 5px 10px rgba(0, 0, 0, 0.05);
            text-align: left;
            display: flex; 
            flex-direction: column; 
        }

        .employee-card p {
            font-size: 14px;
            color: #333;
            margin: 5px 0;
            display: flex; 
            align-items: center; 
        }

        .employee-card p strong {
            color: #555;
            width: 80px;
            flex-shrink: 0; 
            text-align: right; 
            margin-right: 10px; 
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
        <% 
            if (user.getUsername().equals("Vinhnc")) {
                LoginDBContext dbContext = new LoginDBContext();
                EmployeeDBContext employeeDBContext = new EmployeeDBContext(dbContext);
                List<Employee> employees = employeeDBContext.getEmployeesByManagerId(user.getId());
                if (!employees.isEmpty()) {
        %>
            <div class="employee-list">
                <h3>Your Employees</h3>
                    <div class="employee-grid">
                        <% for (Employee employee : employees) { %>
                        <div class="employee-card">
                            <p><strong>ID:</strong> <%= employee.getId() %></p>
                            <p><strong>Full Name:</strong> <%= employee.getFullName() %></p>
                            <p><strong>Email:</strong> <%= employee.getEmail() %></p>
                        </div>
                        <% } %>
                    </div>
            </div>  
        <% 
            }
        }
        %>
        <a href="Login.jsp" class="logout-button">Logout</a>
    </div>
</body>
</html>