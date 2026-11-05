package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetSubscribedEventsReply;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.DatabaseObjects.Event;
import Comunication.DatabaseObjects.EventInstance;

public class GetSubscribedEventsEngine extends AuthenticatedEngine
{
    private static final String QUERY_USER = """
        SELECT DISTINCT 
            e.name, 
            e.description, 
            e.visitType, 
            e.organization, 
            e.city, 
            e.address, 
            e.rendezvous,
            ed.start_date,
            ed.end_date,
            ed.state
        FROM events e
        JOIN subscriptions s ON e.name = s.name
        JOIN eventsData ed ON e.name = ed.name
        WHERE s.userID = ? ;
    """;

    private static final String QUERY_VOLUNTARY = """
        SELECT DISTINCT 
            e.name, 
            e.description, 
            e.visitType, 
            e.organization, 
            e.city, 
            e.address, 
            e.rendezvous,
            ed.start_date,
            ed.end_date,
            ed.state
        FROM events e
        JOIN eventsVoluntaries ev ON e.name = ev.name
        JOIN eventsData ed ON e.name = ed.name AND ev.date = ed.start_date
        WHERE ev.userID = ? ;
    """;

    private String targetID;

    public GetSubscribedEventsEngine(String data) 
    {
        super(data);
        this.targetID = json.getString("targetID");
    }

    public AuthenticatedReply processWithConnection() throws SQLException
    {   
        if(!petitionerCanLogIn())
        {
            return new GetSubscribedEventsReply(false);
        }

        String targetRole = "";
        String roleQuery = "SELECT ROLE FROM users WHERE userID = ?";
        try (PreparedStatement roleStmt = connection.prepareStatement(roleQuery)) {
            roleStmt.setString(1, this.targetID);
            try (ResultSet rs = roleStmt.executeQuery()) {
                if (rs.next()) {
                    targetRole = rs.getString("ROLE");
                }
            }
        }

        String queryToUse = "VOLUNTARY".equals(targetRole) ? QUERY_VOLUNTARY : QUERY_USER;

        Map<String, Event> eventMap = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(queryToUse)) {
            statement.setString(1, this.targetID);
            try (ResultSet result = statement.executeQuery()) {
                while(result.next())
                {
                    String name = result.getString("name");
                    Event event = eventMap.get(name);
                    if (event == null) {
                        String description = result.getString("description");
                        String visitType = result.getString("visitType");
                        String org = result.getString("organization");
                        String city = result.getString("city");
                        String address = result.getString("address");
                        String rendezvous = result.getString("rendezvous");
                        
                        event = new Event(name, description, visitType, org, city, address, rendezvous, new ArrayList<>());
                        eventMap.put(name, event);
                    }

                    int startDate = result.getInt("start_date");
                    int endDate = result.getInt("end_date");
                    String instState = result.getString("state");

                    EventInstance instance = new EventInstance(startDate, endDate, instState, new ArrayList<>(), new ArrayList<>());
                    event.getJSONObject().optJSONArray("instances").put(instance.getJSONObject());
                }
            }
        }
        
        List<Event> events = new ArrayList<>(eventMap.values());
        return new GetSubscribedEventsReply(true, events);
    }
}