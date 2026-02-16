package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.SetNewPasswordReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class SetNewPasswordEngine extends AuthenticatedEngine
{
    private String newPassword;

    private static String setChangePasswordDue = 
    """
        UPDATE users 
        SET changePasswordDue = ?, password = ? 
        WHERE userID = ? AND password = ?
    """;

    public SetNewPasswordEngine(String data) 
    {
        super(data);
        this.newPassword = json.getString("newPassword");
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(setChangePasswordDue);
        statement.setBoolean(1, false);
        statement.setString(2, newPassword);
        statement.setString(3, userID);
        statement.setString(4, password);
        int linesChanged = statement.executeUpdate();
        
        return new SetNewPasswordReply(true, linesChanged == 1);
    }
}