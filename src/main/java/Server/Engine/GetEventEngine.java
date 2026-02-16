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
    private static final String QUERY = 
        """
            SELECT e.*, ed.*
            FROM events e
            INNER JOIN eventsData ed ON e.name = ed.name
            WHERE e.state LIKE ?
        """;

    private String state;

    public GetEventEngine
    (
        String data
    ) {
        super(data);
        state = json.getString("state");
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    { 
        String role = getRole();
        String organization = getOrganization();

        if(!petitionerIsConfigurator())
        {
            return new GetEventReply(false, new ArrayList<Event>());
        }
            
        PreparedStatement statement = connection.prepareStatement(QUERY);
            
        statement.setString(1, "%" + state + "%");

        ResultSet result = statement.executeQuery();

        if(!result.next())
        {
            return new GetEventReply(true, new ArrayList<Event>());
        }
        return new GetEventReply(true, new ArrayList<Event>());    
    }
}
