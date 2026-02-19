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
        SET changePasswordDue = false, password = ? 
        WHERE userID = ?
    """;

    public SetNewPasswordEngine(String data) 
    {
        super(data);
        this.newPassword = json.getString("newPassword");
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if(!petitionerCanLogIn())
        {
            return new SetNewPasswordReply(false, false);
        }
        PreparedStatement statement = connection.prepareStatement(setChangePasswordDue);
        statement.setString(1, newPassword);
        statement.setString(2, getUserID());
        int linesChanged = statement.executeUpdate();
        
        return new SetNewPasswordReply(true, linesChanged == 1);
    }
}