/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;
import User.LeaveRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Asus
 */
public class LeaveRequestDBContext {
    private DBContext dbContext;
    public LeaveRequestDBContext(DBContext dbContext){
        this.dbContext = dbContext;
    }
    public void addLeaveRequest(LeaveRequest request){
        String query = "INSERT INTO LeaveRequests (employee_id, full_name, department, position, start_date, end_date, reason, status)" +
                        "Value (?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = dbContext.connection.prepareStatement(query)) {
            stmt.setInt(1, request.getEmployeeId());
            stmt.setString(2, request.getFullName());
            stmt.setString(3, request.getDepartment());
            stmt.setString(4, request.getPosition());
            stmt.setString(5, request.getStartDate());
            stmt.setString(6, request.getEndDate());
            stmt.setString(7, request.getReason());
            stmt.setString(8, request.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Database Error in addLeaveRequest: " + e.getMessage());
            e.printStackTrace();       
        }
    }
   public List<LeaveRequest> getLeaveRequestsByManagerId(int managerId) {
        List<LeaveRequest> requests = new ArrayList<>();
        String query = "SELECT lr.* FROM LeaveRequests lr " +
                      "JOIN Employees e ON lr.employee_id = e.id " +
                      "JOIN Users u ON e.user_id = u.id " +
                      "WHERE u.manager_id = ?";
        try (PreparedStatement stmt = dbContext.connection.prepareStatement(query)) {
            stmt.setInt(1, managerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LeaveRequest request = new LeaveRequest(
                    rs.getInt("id"),
                    rs.getInt("employee_id"),
                    rs.getString("full_name"),
                    rs.getString("department"),
                    rs.getString("position"),
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getString("reason"),
                    rs.getString("status")
                );
                requests.add(request);
            }
        } catch (SQLException e) {
            System.err.println("Database error in getLeaveRequestsByManagerId: " + e.getMessage());
            e.printStackTrace();
        }
        return requests;
    }
   public void updateLeaveRequestStatus(int requestId, String status ){
       String query = "UPDATE LeaveRequests SET status = ? where id = ?";
       try(PreparedStatement stmt = dbContext.connection.prepareStatement(query)) {
           stmt.setString(1, status);
           stmt.setInt(2, requestId);
           stmt.executeUpdate();
       } catch (SQLException e) {
            System.err.println("Database error in updateLeaveRequestsStatus: " + e.getMessage());
           e.printStackTrace();
       }
   }
}
