package Server.Engine;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import org.json.*;
import java.util.concurrent.*;

import RequestReply.Reply.*;
import RequestReply.Request.*;
import RequestReply.ComunicationType.*;
import RequestReply.UserRoleTitle.*;
import Server.Engine.*;

public class SetDisponibilityEngine extends Engine
{
    private String userID;
    private String password;
    private String eventName;
    private int time;

    public SetDisponibilityEngine
    (
        String userID,
        String password,
        String eventName,
        int time
    ) {
        this.userID = userID;
        this.password = password;
        this.eventName = eventName;
        this.time = time;
    }

    public String handleRequest()
    {
        try 
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String userCheckQuery = """
               SELECT 1
               FROM users u
               JOIN events e ON u.organization = e.organization
               WHERE u.userID = ?
               AND u.userPassword = ?
               AND e.eventName = ?
               AND (? BETWEEN e.startDate AND e.endDate)
            """;
            PreparedStatement stmt = connection.prepareStatement(userCheckQuery);
            stmt.setString(1, userID);
            stmt.setString(2, password);
            stmt.setString(3, eventName);
            stmt.setInt(4, time);
            if(!stmt.executeQuery().next())
            {
                return new SetDisponibilityReply(true, true).toJSONString();
            }
            String query = "INSERT INTO eventsVoluntaries VALUES ( ?, ?, ? )";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, eventName);
            statement.setString(2, userID);
            statement.setInt(3, time);
            if(statement.executeUpdate() == 1)
            {
                return new SetDisponibilityReply(true, true).toJSONString();
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new SetDisponibilityReply(false, false).toJSONString();
    }
}