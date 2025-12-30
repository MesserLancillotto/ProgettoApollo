package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetEventReply;

public class GetEventEngine extends AuthenticatedEngine
{
    private String state:

    public GetEventEngine
    (
        String data
    ) {
        super(data);
        state = json.getString("state");
    }
    
    public AuthenticatedUpdateReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new AuthenticatedReply(false);
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new AuthenticatedReply(false);
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"CONFIGURATOR".equals(role))
            {
                return new AuthenticatedReply(false);
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
                return new GetEventReply(true, false);
            }

            while()
            {

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new GetEventReply(true, false);    
    }
}
