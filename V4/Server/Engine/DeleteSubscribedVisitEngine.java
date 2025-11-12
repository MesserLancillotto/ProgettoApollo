package Server.Engine;

import org.json.*;
import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;


public class DeleteSubscribedVisitEngine extends Engine
{
    private String userID;
    private String password;
    private String event;
    private int time;

    public DeleteSubscribedVisitEngine
    (
        String userID, 
        String password, 
        String event,
        int time
    ) {
        this.userID = userID;
        this.password = password;
        this.event = event;
        this.time = time;
    }

    public String handleRequest()
    {
        try 
        (
            Connection connection = connectDB(dbUrl, "sa", "")
        ) {
            String loginQuery = "SELECT role FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, userID);
            loginStatement.setString(2, password);
            ResultSet userResult = loginStatement.executeQuery(); 
            
            if(!userResult.next()) {
                return new DeleteSubscribedVisitReply(false, 0).toJSONString(); 
            }
            
            if(!userResult.getString("role").equals("USER")) {
                return new DeleteSubscribedVisitReply(true, 0).toJSONString(); 
            }
            
            String userOrganization = userResult.getString("organization");
            String query = """
                DELETE FROM eventsUsers 
                eventName  = ?
                AND userID = ?
                AND time = ?
            """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, event);
            statement.setString(2, userID);
            statement.setInt(3, time);
            int rowsDeleted = statement.executeUpdate();
            return new DeleteSubscribedVisitReply(true, rowsDeleted).toJSONString(); 
            
        } catch(Exception e) {
            e.printStackTrace();
            return new DeleteSubscribedVisitReply(false, 0).toJSONString();
        }
    }
}