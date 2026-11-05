package Server.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.*;
import java.sql.*;

import Comunication.DatabaseObjects.Event;
import Comunication.DatabaseObjects.EventInstance;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetEventReply;

public class GetEventEngine extends AuthenticatedEngine
{
    private String state;

    public GetEventEngine(String data) 
    {
        super(data);
        state = json.optString("state", "");
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    { 
        String role = getRole();
        String organization = getOrganization();

        boolean isConfiguratorOrVoluntary = petitionerIsConfigurator() || "VOLUNTARY".equals(role);
        boolean isUser = "USER".equals(role);

        if (!isConfiguratorOrVoluntary && !isUser) {
            return new GetEventReply(false, new ArrayList<Event>());
        }

        StringBuilder queryBuilder = new StringBuilder("""
            SELECT e.name, e.description, e.visitType, e.organization, e.city, e.address, e.rendezvous,
                   ed.start_date, ed.end_date, ed.state
            FROM events e
            INNER JOIN eventsData ed ON e.name = ed.name
            WHERE 1=1
        """);

        ArrayList<Object> params = new ArrayList<>();

        if (state != null && !state.isEmpty()) {
            queryBuilder.append(" AND ed.state LIKE ?");
            params.add("%" + state + "%");
        }

        if (isConfiguratorOrVoluntary && organization != null && !organization.isEmpty()) {
            queryBuilder.append(" AND e.organization = ?");
            params.add(organization);
        }

        queryBuilder.append(";");

        try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            try (ResultSet result = statement.executeQuery()) {
                Map<String, Event> eventMap = new HashMap<>();

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
                    
                    // Aggiunge l'istanza all'evento
                    event.getJSONObject().optJSONArray("instances").put(instance.getJSONObject());
                }
                
                ArrayList<Event> events = new ArrayList<>(eventMap.values());
                return new GetEventReply(true, events);
            }
        }
    }
}