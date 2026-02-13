package Server.Engine;

import java.util.ArrayList;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetEventReply;

public class GetEventEngine extends AuthenticatedEngine
{
    private String state;

    public GetEventEngine
    (
        String data
    ) {
        super(data);
        state = json.getString("state");
    }
    
    public AuthenticatedReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new GetEventReply(false, new ArrayList<Boolean>());
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new GetEventReply(false, new ArrayList<Boolean>());
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"CONFIGURATOR".equals(role))
            {
                return new GetEventReply(false, new ArrayList<Boolean>());
            }
            
            String query = """
                SELECT e.*, ed.*
                FROM events e
                INNER JOIN eventsData ed ON e.name = ed.name
                WHERE e.state LIKE '?'
            """;
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, "%" + state + "%");

            ResultSet result = statement.executeQuery();

            if(!result.next())
            {
                return new GetEventReply(true, new ArrayList<Boolean>());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new GetEventReply(true, new ArrayList<Boolean>());    
    }
}
