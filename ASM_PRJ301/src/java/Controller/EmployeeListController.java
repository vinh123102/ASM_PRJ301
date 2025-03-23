/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.EmployeeDBContext;
import DAL.LoginDBContext;
import User.Employee;
import User.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Asus
 */
@WebServlet(name = "EmployeeListController", urlPatterns = {"/EmployeeList"})
public class EmployeeListController extends HttpServlet {
    private EmployeeDBContext employeeDBContext;
    @Override
    public void init() throws ServletException{
        LoginDBContext dbContext = new LoginDBContext();
        employeeDBContext = new EmployeeDBContext(dbContext);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       User user = (User) request.getSession().getAttribute("user");
       if (user == null || !"manager".equals(user.getRole())){
           response.sendRedirect("Login.jsp");
           return;
       }
        List<Employee> employees = employeeDBContext.getEmployeesByManagerId(user.getId());
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("welcome.jsp").forward(request, response);
    }

   
}
