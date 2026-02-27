package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetSubscribedEventsReply;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.DatabaseObjects.Event;
import Server.Engine.Helper.*;

public class GetSubscribedEventsEngine extends AuthenticatedEngine
{
    
    private static final String QUERY = """
        SELECT DISTINCT 
            e.name, 
            e.description, 
            e.visitType, 
            e.organization, 
            e.city, 
            e.address, 
            e.rendezvous, 
            e.state
        FROM events e
        JOIN subscriptions ON e.name = subscriptions.name
        JOIN eventsData ed ON ed.name = e.name
        WHERE subscriptions.userID = ? ;
    """;

    private String targetID;

    public GetSubscribedEventsEngine(String data) 
    {
        super(data);
        this.targetID = json.getString("targetID");
    }

    public AuthenticatedReply processWithConnection() throws SQLException
    {   
        if(!petitionerCanLogIn() || !petitionerIsUser())
        {
            return new GetSubscribedEventsReply(false);
        }

        List<Event> events = new ArrayList<>();

        PreparedStatement statement = 
            connection.prepareStatement(QUERY);
            
        statement.setString(1, this.targetID);
            
        ResultSet result = statement.executeQuery();

        while(result.next())
        {
            events.add(
                EventCreator.createEventFromName(
                    connection, 
                    result.getString("name"))
            );
        }
        return new GetSubscribedEventsReply(true, events);
    }
}