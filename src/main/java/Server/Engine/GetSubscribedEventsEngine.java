package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetSubscribedEventsReply;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.DatabaseObjects.Place;

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
            e.state, 
            ed.start_date, 
            ed.end_date 
        FROM events e
        JOIN subscriptions ON e.name = subscriptions.name
        JOIN eventsData ed ON ed.name = e.name
        WHERE subscriptions.userID = ? ;
    """;

    public GetSubscribedEventsEngine(String data) 
    {
        super(data);
    }

    public AuthenticatedReply processWithConnection() throws SQLException
    {   
        if(!petitionerCanLogIn() || !petitionerIsUser())
        {
            return new GetSubscribedEventsReply(false);
        }

        PreparedStatement statement = 
            connection.prepareStatement(QUERY);
            
        statement.setString(1, getUserID());
            
        ResultSet result = statement.executeQuery();
        ArrayList<Place> places = new ArrayList<Place>();
            
        while(result.next())
        {
            String city = result.getString("city");
            String address = result.getString("address");
            String description = result.getString("description");
            String organization = result.getString("organization");
            String detailsQuery = 
            """
                SELECT visitType, userID FROM placesData 
                WHERE city = ? AND address = ?
            """;
            
            PreparedStatement detailsStmt = 
                connection.prepareStatement(detailsQuery);
                
            detailsStmt.setString(1, city);
            detailsStmt.setString(2, address);
                
            ResultSet detailsResult = detailsStmt.executeQuery();
            ArrayList<String> visitTypes = new ArrayList<String>();
            ArrayList<String> voluntaries = new ArrayList<String>();                
                
            int i = 0;
            while(detailsResult.next())
            {
                i++;
                visitTypes.add(detailsResult.getString("visitType"));
                voluntaries.add(detailsResult.getString("userID"));
            }
                
            detailsResult.close();
                
            Place place = new Place(
                city, 
                address, 
                description,
                organization,
                visitTypes, 
                voluntaries
            );
            places.add(place);
        }
        return new GetSubscribedEventsReply(true, places);
    }
}