package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetPersonalDataReply;
import Comunication.DatabaseObjects.User;
import Comunication.DatabaseObjects.UserRole;
import Helper.UserCreator;

public class GetPersonalDataEngine extends AuthenticatedEngine
{
    
    private static final String QUERY =
        """
            SELECT userID, userName, userSurname, city, organization, 
            birth_dd, birth_mm, birth_yy, changePasswordDue
            user_since, role, changePasswordDue 
            FROM users WHERE userID = ? 
        """ ;

    public GetPersonalDataEngine(String data) 
    {
        super(data);
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, getUserID());
        ResultSet result = statement.executeQuery();
        
        if(!result.next())
        {
            return new GetPersonalDataReply(false);
        }
        
        User user = UserCreator.createUserFromResultSet(connection, result);
        
        return new GetPersonalDataReply(true, user);
    } 
}