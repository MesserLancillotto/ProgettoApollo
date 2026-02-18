package Server.Engine;

import org.json.*;
import java.sql.*;

import Comunication.Reply.SetMaximumFriendsReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Server.Engine.Interfaces.AuthenticatedEngine;

public class SetMaximumFriendsEngine extends AuthenticatedEngine
{
    private Integer maximum_friends;
    private static final String QUERY = 
    """
        MERGE INTO organizations (organization, maximum_friends) 
        KEY(organization) 
        VALUES ( ?, ? );
    """;

    public SetMaximumFriendsEngine 
    (
        String data
    ) {
        super(data);
        this.maximum_friends = json.getInt("maximum_friends");
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if(!petitionerIsConfigurator())
        {
            return new SetMaximumFriendsReply(false, false);
        }

        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, getOrganization());
        statement.setInt(2, maximum_friends);
        if(statement.executeUpdate() == 1)
        {
            return new SetMaximumFriendsReply(true, true);
        }
        return new SetMaximumFriendsReply(true, false);  
    }
}
