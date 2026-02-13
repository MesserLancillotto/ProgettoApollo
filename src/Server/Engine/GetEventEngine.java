package Server.Engine;

import java.util.ArrayList;

import org.json.*;
import java.sql.*;

import Comunication.DatabaseObjects.Event;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;
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
            return new GetEventReply(false, new ArrayList<Event>());
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new GetEventReply(false, new ArrayList<Event>());
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"CONFIGURATOR".equals(role))
            {
                return new GetEventReply(false, new ArrayList<Event>());
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
                return new GetEventReply(true, new ArrayList<Event>());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new GetEventReply(true, new ArrayList<Event>());    
    }
}
