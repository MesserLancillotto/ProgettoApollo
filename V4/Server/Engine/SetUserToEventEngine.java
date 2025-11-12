package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

public class SetUserToEventEngine extends Engine
{
    private String userID;
    private String password;
    private ArrayList<String> friends;
    private String event;
    private int time;

    public SetUserToEventEngine(
        String userID, 
        String password,
        ArrayList<String> friends,
        String event,
        int time
    ) {
        this.userID = userID;
        this.password = password;
        this.friends = friends;
        this.event = event;
        this.time = time;
    }

    public String handleRequest()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) 
        {
            friends.add(userID);

            String allowedPrenotationsQuery = """
                SELECT usersCanSubmit FROM events WHERE eventName = ?
            """;
            PreparedStatement s = connection.prepareStatement(allowedPrenotationsQuery);
            s.setString(1, event);
            ResultSet resultSetPrenotation = s.executeQuery(); 
            if(!resultSetPrenotation.next())
            {
                return new SetUserToEventReply(false, false).toJSONString(); 
            }

            String allowedUsersQuery = """
                SELECT COUNT(*) <= e.maxUsers - ?
                FROM eventsUsers eu
                INNER JOIN events e ON e.eventName = eu.event
                WHERE eu.event = ? AND eu.time = ?;
            """;
            PreparedStatement allowedUsersStatement = connection.prepareStatement(allowedUsersQuery);
            allowedUsersStatement.setInt(1, friends.size());
            allowedUsersStatement.setString(2, event);
            allowedUsersStatement.setInt(3, time);
            ResultSet r = allowedUsersStatement.executeQuery();
            if(!r.next())
            {
                return new SetUserToEventReply(false, false).toJSONString(); 
            }

            for(String userID : friends)
            {
                String loginQuery = "SELECT role FROM users WHERE userID = ? AND userPassword = ?";
                PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
                loginStatement.setString(1, userID);
                loginStatement.setString(2, password);
                ResultSet userResult = loginStatement.executeQuery(); 

                if(!userResult.next()) {
                    return new SetUserToEventReply(false, false).toJSONString(); 
                }

                if(!userResult.getString("role").equals("USER")) {
                    return new SetUserToEventReply(true, false).toJSONString();
                }
            }
            

            for(String targetID : friends)
            {
                String query = "INSERT INTO eventsUsers VALUES ( ?, ?, ? );";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, event);
                statement.setString(2, targetID);
                statement.setInt(3, time);
            }
            
            return new SetVoluntaryToEventReply(true, true).toJSONString();
            
        } catch(Exception e) {
            e.printStackTrace();
            return new SetVoluntaryToEventReply(false, false).toJSONString();
        }
    }
}