/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import User.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class EmployeeDBContext {
    private DBContext dbContext;

    public EmployeeDBContext(DBContext dbContext) {
        this.dbContext = dbContext;
    }

    // Lấy danh sách nhân viên dưới quyền của một quản lý
    public List<Employee> getEmployeesByManagerId(int managerId) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT e.* FROM Employees e " +
                      "JOIN Users u ON e.user_id = u.id " +
                      "WHERE u.manager_id = ?";
        try (PreparedStatement stmt = dbContext.connection.prepareStatement(query)) {
            stmt.setInt(1, managerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Database error in EmployeeDAO: " + e.getMessage());
            e.printStackTrace();
        }
        return employees;
    }
}
