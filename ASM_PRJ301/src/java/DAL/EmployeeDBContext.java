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
    private int userId;

    public EmployeeDBContext(DBContext dbContext) {
        this.dbContext = dbContext;
    }

    // Lấy danh sách nhân viên dưới quyền của một quản lý
    public List<Employee> getEmployeesByManagerId(int managerId) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT e.* FROM Employees e " +
                      "JOIN Users u ON e.user_id = u.id " +
                      "LEFT JOIN Department d ON e.Department_id = d.id" +
                      "WHERE u.manager_id = ?";
        try (PreparedStatement stmt = dbContext.connection.prepareStatement(query)) {
            stmt.setInt(1, managerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("Department_name"),
                    rs.getString("position")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Database error in EmployeeDAO: " + e.getMessage());
            e.printStackTrace();
        }
        return employees;
    }

        public Employee getEmployeeByUserId(int UserId){
            String query = "select e.*, d.name AS department_name " + 
                           " from Employees e" +
                           "left join Departments d ON e.Department_id = d.id" +
                           "where e.user_id= ?";
            try(PreparedStatement stmt = dbContext.connection.prepareStatement(query)){
                stmt.setInt(1,userId);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                return new Employee (
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("Department_name"),
                    rs.getString("position")
                );
            }
        }catch (SQLException e){
            System.err.println("Database Error in EmployeeByUserId: " + e.getMessage());
        }
            return null;
    }
}
