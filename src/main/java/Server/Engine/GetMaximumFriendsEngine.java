package Server.Engine;

import java.util.ArrayList;
import org.json.*;
import java.sql.*;

import Comunication.DatabaseObjects.Event;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetMaximumFriendsReply;

public class GetMaximumFriendsEngine extends AuthenticatedEngine
{
    private static final String QUERY = 
    """
        SELECT maximum_friends FROM organizations WHERE organization = ?
    """;

    public GetMaximumFriendsEngine(String jsonData)
    {
        super(jsonData);
    }

    @Override
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, json.getString("organization"));
        ResultSet result = statement.executeQuery();
        
        if(result.next())
        {
            return new GetMaximumFriendsReply(true, result.getInt("maximum_friends"));
        }
        return new GetMaximumFriendsReply();
    } 
}