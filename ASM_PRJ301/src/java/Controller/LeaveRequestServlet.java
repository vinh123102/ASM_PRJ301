/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.EmployeeDBContext;
import DAL.LeaveRequestDBContext;
import DAL.LoginDBContext;
import User.Employee;
import User.LeaveRequest;
import User.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Asus
 */
@WebServlet(name = "LeaveRequest", urlPatterns = {"/LeaveRequest"})
public class LeaveRequestServlet extends HttpServlet {
    private LeaveRequestDBContext leaveRequestDBContext;
    private EmployeeDBContext employeeDBContext;
     @Override
    public void init() throws ServletException{
        LoginDBContext dbContext = new LoginDBContext();
        leaveRequestDBContext = new LeaveRequestDBContext(dbContext);
        employeeDBContext = new EmployeeDBContext(dbContext);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("submit".equals(action)){
            User user = (User) request.getSession().getAttribute("user");
            if(user == null || !"employee".equals(user.getRole())){
                response.sendRedirect("Login.jsp");
                return;
            }
            Employee employee = employeeDBContext.getEmployeeByUserId(user.getId());
            if(employee == null){
                request.setAttribute("error","Employee information not found.");
                request.getRequestDispatcher("welcome.jsp").forward(request, response);
                return;
            }
            String fullName = employee.getFullName();
            String department = employee.getDepartment();
            String position = employee.getPosition();
            String startDate = request.getParameter("start_date");
            String endDate = request.getParameter("end_date");
            String reason = request.getParameter("reason");

            LeaveRequest leaveRequest = new LeaveRequest(
                0, // ID sẽ được tự động tăng
                employee.getId(),
                fullName,
                department,
                position,
                startDate,
                endDate,
                reason,
                "Pending"
            );

            leaveRequestDBContext.addLeaveRequest(leaveRequest);
            response.sendRedirect("welcome.jsp");
        } else if ("approve".equals(action) || "reject".equals(action)) {
            // Xử lý đồng ý hoặc từ chối đơn xin nghỉ phép
            User user = (User) request.getSession().getAttribute("user");
            if (user == null || !"manager".equals(user.getRole())) {
                response.sendRedirect("login.jsp");
                return;
            }

            int requestId = Integer.parseInt(request.getParameter("request_id"));
            String status = "approve".equals(action) ? "Approved" : "Rejected";
            leaveRequestDBContext.updateLeaveRequestStatus(requestId, status);
            response.sendRedirect("welcome.jsp");
        }
    }   
}
