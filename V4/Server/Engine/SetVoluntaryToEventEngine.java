package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

public class SetVoluntaryToEventEngine extends Engine
{
    private String userID;
    private String password;
    private String targetID;
    private String event;
    private int time;

    public SetVoluntaryToEventEngine(
        String userID, 
        String password,
        String targetID,
        String event,
        int time
    ) {
        this.userID = userID;
        this.password = password;
        this.targetID = targetID;
        this.event = event;
        this.time = time;
    }

    public String handleRequest()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) 
        {
            String loginQuery = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, userID);
            loginStatement.setString(2, password);
            ResultSet userResult = loginStatement.executeQuery(); 
            
            if(!userResult.next()) {
                return new SetVoluntaryToEventReply(false, false).toJSONString(); 
            }
            
            if(!userResult.getString("role").equals("CONFIGURATOR") || !userID.equals(targetID)) {
                return new SetVoluntaryToEventReply(true, false).toJSONString();
            }
            String userOrganization = userResult.getString("organization");

            String checkTargetQuery = "SELECT 1 FROM users WHERE userID = ? AND organization = ?";
            PreparedStatement checkTargetStmt = connection.prepareStatement(checkTargetQuery);
            checkTargetStmt.setString(1, targetID);
            checkTargetStmt.setString(2, userOrganization);
            if (!checkTargetStmt.executeQuery().next()) {
                return new SetVoluntaryToEventReply(true, false).toJSONString();
            }

            String query = "INSERT INTO EVENTSVOLUNTARIES VALUES ( ?, ?, ? );";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, event);
            statement.setString(2, targetID);
            statement.setInt(3, time);
            
            return new SetVoluntaryToEventReply(true, true).toJSONString();
            
        } catch(Exception e) {
            e.printStackTrace();
            return new SetVoluntaryToEventReply(false, false).toJSONString();
        }
    }
}