package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;
import RequestReply.UserRoleTitle.*;
import RequestReply.ComunicationType.*;

public class SetNewEventEngine extends Engine
{
    private String userID;
    private String userPassword;
    private String eventName;
    private String description;
    private String city;
    private String address;
    private String meetingPoint;
    private int startDate;
    private int endDate;
    private String organization;
    private int minimumUsers; 
    private int maximumUsers;
    private int maximumFriends;
    private String visitType;
    private String state;
    
    private ArrayList<String> visitDays; // // Dichiarare queste variabili
    private ArrayList<Integer> startHour;
    private ArrayList<Integer> duration;

    public SetNewEventEngine(
        String userID,
        String userPassword,
        String eventName,
        String description,
        String city,
        String address,
        String meetingPoint, // // Virgola, non punto e virgola
        int startDate,
        int endDate,
        String organization,
        int minimumUsers,
        int maximumUsers,
        int maximumFriends,
        String visitType,
        String state,
        ArrayList<String> visitDays,
        ArrayList<Integer> startHour,
        ArrayList<Integer> duration        
    ) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.eventName = eventName;
        this.description = description;
        this.city = city;
        this.address = address;
        this.meetingPoint = meetingPoint;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organization = organization;
        this.minimumUsers = minimumUsers;
        this.maximumUsers = maximumUsers;
        this.maximumFriends = maximumFriends;
        this.visitType = visitType;
        this.state = state;
        
        this.visitDays = visitDays;
        this.startHour = startHour;
        this.duration = duration;
    }

    public String handleRequest()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String roleCheckQuery = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement roleStatement = connection.prepareStatement(roleCheckQuery);
            roleStatement.setString(1, userID);
            roleStatement.setString(2, userPassword);
            ResultSet result = roleStatement.executeQuery();
            if(
                !result.next() 
                || !result.getString("role").equals("CONFIGURATOR") 
                || !result.getString("organization").equals(this.organization)
            ) {
                return new SetNewEventReply(false, false, false).toJSONString(); 
            }

            String checkClosureDaysQuery = "SELECT DISTINCT organization FROM closedDays WHERE (startDay BETWEEN ? AND ?) OR (endDay BETWEEN ? AND ?)";
            PreparedStatement checkClosureDaysStatement = connection.prepareStatement(checkClosureDaysQuery);
            checkClosureDaysStatement.setInt(1, startDate);
            checkClosureDaysStatement.setInt(2, endDate);
            checkClosureDaysStatement.setInt(3, startDate);
            checkClosureDaysStatement.setInt(4, endDate);
            ResultSet checkClosureDaysResult = checkClosureDaysStatement.executeQuery();
            if(checkClosureDaysResult.next())
            {
                return new SetNewEventReply(true, false, true).toJSONString();
            } 

            String checkTerritoryQuery = "SELECT DISTINCT territory FROM organizations WHERE organization = ? AND territory = ?";
            PreparedStatement checkTerritoryStatement = connection.prepareStatement(checkTerritoryQuery);
            checkTerritoryStatement.setString(1, organization);
            checkTerritoryStatement.setString(2, city);
            ResultSet checkTerritoryResult = checkTerritoryStatement.executeQuery();
            if(!checkTerritoryResult.next())
            {
                return new SetNewEventReply(true, false, true).toJSONString();
            } 

            String query = "INSERT INTO events VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE, TRUE)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, eventName);
            statement.setString(2, description);
            statement.setString(3, city); 
            statement.setString(4, address);
            statement.setString(5, meetingPoint);
            statement.setInt(6, startDate);
            statement.setInt(7, endDate);
            statement.setString(8, organization);
            statement.setInt(9, minimumUsers); 
            statement.setInt(10, maximumUsers);
            statement.setInt(11, maximumFriends);
            statement.setString(12, visitType); 
            statement.setString(13, state);
            if(statement.executeUpdate() != 1)
            {
                return new SetNewEventReply(true, false, false).toJSONString();
            }
            
            for(int i = 0; i < visitDays.size(); i++)
            {
                query = "INSERT INTO daysOfWeek VALUES ( ?, ?, ?, ? )";
                statement = connection.prepareStatement(query);
                statement.setString(1, eventName);
                statement.setString(2, visitDays.get(i));
                statement.setInt(3, startHour.get(i));
                statement.setInt(4, duration.get(i));
                int _x = statement.executeUpdate();
            }
            return new SetNewEventReply(true, true, false).toJSONString();
            
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new SetNewEventReply(true, false, false).toJSONString();
    }
}