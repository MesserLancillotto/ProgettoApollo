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

public class EditPasswordEngine extends Engine
{

    private String userID;
    private String password;
    private String newPassword;

    public EditPasswordEngine(
        String userID,
        String password,
        String newPassword
    ) {
        this.userID = userID;
        this.password = password;
        this.newPassword = newPassword;   
    }

    public String handleRequest()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String loginQuery = "SELECT * FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, userID);
            loginStatement.setString(2, password);
            ResultSet userResult = loginStatement.executeQuery();            
            if(!userResult.next())
            {
               return new EditPasswordReply(false, false).toJSONString();
            }
            String query = "UPDATE users SET userPassword = ? WHERE userID = ? AND userPassword = ?";
            PreparedStatement selectStatement = connection.prepareStatement(query); 
            selectStatement.setString(1, newPassword);
            selectStatement.setString(2, userID);
            selectStatement.setString(3, password);
            int rowsUpdated = selectStatement.executeUpdate();
            if (rowsUpdated == 1) {
               return new EditPasswordReply(true, true).toJSONString();
            }
            return new EditPasswordReply(true, false).toJSONString();
        } catch(Exception e)
        {
            e.printStackTrace();
            return new EditPasswordReply(true, false).toJSONString();
        }
    }
}
