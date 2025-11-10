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

public class SetClosedDaysEngine extends Engine
{
    private String userID;
    private String password;
    private int startDate;
    private int endDate;
    private String organization;

    public SetClosedDaysEngine
    (
        String userID,
        String password,
        int startDate,
        int endDate,
        String organization
    ) {
        this.userID = userID;
        this.password = password;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organization = organization;
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
            roleStatement.setString(2, password);
            ResultSet result = roleStatement.executeQuery();

            if(
                !result.next() 
                || !result.getString("role").equals("CONFIGURATOR") 
                || !result.getString("organization").equals(this.organization)
            ) {
                return new SetClosedDaysReply(false, false).toJSONString(); 
            }
            String query = "INSERT INTO closedDays VALUES ( ?, ?, ? )"; 
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, startDate);
            statement.setInt(2, endDate);
            statement.setString(3, organization);
            if(statement.executeUpdate() == 1)
            {
                return new SetClosedDaysReply(true, true).toJSONString(); 
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new SetClosedDaysReply(true, false).toJSONString();
    }
}