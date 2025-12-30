package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetEventReply;
import Comunication.DatabaseObjects.Event;

public class GetEventEngine extends AuthenticatedEngine
{
    private String state;

    public GetEventEngine(String data) 
    {
        super(data);
        state = json.getString("state");
    }
    
    public AuthenticatedReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new GetEventReply(false, new ArrayList<>());
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new GetEventReply(false, new ArrayList<>());
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"CONFIGURATOR".equals(role))
            {
                return new GetEventReply(false, new ArrayList<>());
            }
            
            String query = """
                SELECT e.*, ed.*
                FROM events e
                INNER JOIN eventsData ed ON e.name = ed.name
                WHERE e.state LIKE ?
            """;
            
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, "%" + state + "%");

            ResultSet result = statement.executeQuery();
            
            List<Event> events = new ArrayList<>();
            
            while(result.next())
            {
                Event event = createEventFromResultSet(result);
                events.add(event);
            }
            
            result.close();
            statement.close();
            
            return new GetEventReply(true, events);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new GetEventReply(false, new ArrayList<>());
        }
        finally
        {
            disconnectDB();
        }
    }
    
    private Event createEventFromResultSet(ResultSet resultSet) throws SQLException
    {
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String type = resultSet.getString("type");
        String organization = resultSet.getString("organization");
        String city = resultSet.getString("city");
        String address = resultSet.getString("address");
        String rendezvous = resultSet.getString("rendezvous");
        String state = resultSet.getString("state");
        
        List<String> voluntaries = new ArrayList<>();
        String voluntariesStr = resultSet.getString("voluntaries");
        if(voluntariesStr != null && !voluntariesStr.isEmpty())
        {
            try 
            {
                JSONArray voluntariesArray = new JSONArray(voluntariesStr);
                for(int i = 0; i < voluntariesArray.length() && i < 100; i++)
                {
                    voluntaries.add(voluntariesArray.getString(i));
                }
            } 
            catch (JSONException e) 
            {
                String[] voluntariesArray = voluntariesStr.split(",");
                for(int i = 0; i < voluntariesArray.length && i < 100; i++)
                {
                    voluntaries.add(voluntariesArray[i].trim());
                }
            }
        }
        
        List<List<Integer>> singleEvent = new ArrayList<>();
        String singleEventStr = resultSet.getString("singleEvent");
        if(singleEventStr != null && !singleEventStr.isEmpty())
        {
            try 
            {
                JSONArray singleEventArray = new JSONArray(singleEventStr);
                for(int i = 0; i < singleEventArray.length() && i < 365; i++)
                {
                    JSONArray timeIntervalArray 
                        = singleEventArray.getJSONArray(i);
                    if(timeIntervalArray.length() >= 2)
                    {
                        List<Integer> timeInterval = new ArrayList<>();
                        timeInterval.add(timeIntervalArray.getInt(0));
                        timeInterval.add(timeIntervalArray.getInt(1));
                        singleEvent.add(coppia);
                    }
                }
            } 
            catch (JSONException e) 
            {
                String[] eventPairs = singleEventStr.split(",");
                for(String pair : eventPairs)
                {
                    if(pair.contains("-"))
                    {
                        String[] times = pair.split("-");
                        if(times.length >= 2)
                        {
                            try
                            {
                                List<Integer> coppia = new ArrayList<>();
                                coppia.add(Integer.parseInt(times[0].trim()));
                                coppia.add(Integer.parseInt(times[1].trim()));
                                singleEvent.add(coppia);
                            }
                            catch(NumberFormatException nfe)
                            {
                                assert true;
                            }
                        }
                    }
                }
            }
        }
        
        return new Event(
            name,
            description,
            type,
            organization,
            city,
            address,
            rendezvous,
            state,
            voluntaries,
            singleEvent
        );
    }
}