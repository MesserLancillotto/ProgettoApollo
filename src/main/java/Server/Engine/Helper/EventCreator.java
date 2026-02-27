package Server.Engine.Helper;

import Comunication.ComunicationType.*;
import Comunication.DatabaseObjects.*;

import java.sql.*;
import java.util.*;

public class EventCreator
{
    private static final String QUERY = """
        SELECT description, visitType, organization, city, address, rendezvous
        FROM events 
        WHERE name = ?
    """;
    
    public static Event createEventFromName(
        Connection connection, 
        String eventName
    ) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, eventName);
        ResultSet eventsResult = statement.executeQuery();
        
        eventsResult.next();

        return new Event(
            eventName,
            eventsResult.getString("description"),
            eventsResult.getString("visitType"),
            eventsResult.getString("organization"),
            eventsResult.getString("city"),
            eventsResult.getString("address"),
            eventsResult.getString("rendezvous"),
            new ArrayList<EventInstance>()
        );
    }
}