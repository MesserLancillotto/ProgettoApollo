package Server.Engine;

import org.json.*;
import java.sql.*;

import Comunication.Reply.SetMaximumFriendsReply;
import Comunication.Reply.AuthenticatedUpdateReply;
import Server.Engine.Interfaces.AuthenticatedEngine;

public class SetMaximumFriendsEngine extends AuthenticatedEngine
{
    private Integer maximum_friends;
    public SetMaximumFriendsEngine 
    (
        String data
    ) {
        super(data);

        this.maximum_friends = json.getInt("maximum_friends");
    }
    
    public AuthenticatedUpdateReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new SetMaximumFriendsReply(false, false);
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new SetMaximumFriendsReply(false, false);
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"CONFIGURATOR".equals(role))
            {
                return new SetMaximumFriendsReply(false, false);
            }

            String query = "INSERT INTO organizations VALUES ( ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, organization);
            statement.setInt(2, maximum_friends);
            if(statement.executeUpdate() != 1)
            {
                return new SetMaximumFriendsReply(true, true);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new SetMaximumFriendsReply(true, false);  
    }
}
