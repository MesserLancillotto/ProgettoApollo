package Server.Engine;

import org.json.*;
import java.util.*;
import java.sql.*;

import Comunication.DatabaseObjects.Place;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.SetVisitablePlacesReply;

public class SetVisitablePlacesEngine extends AuthenticatedEngine
{
    private List<Place> places;
    private JSONArray placesData;

    private static String PLACES_QUERY 
        = "INSERT INTO places VALUES ( ?, ?, ?, ? )";
    private static String PLACES_DATA_QUERY 
        = "INSERT INTO placesData VALUES ( ?, ?, ?, ? )";

    public SetVisitablePlacesEngine 
    (
        String data
    ) {
        super(data);
        try 
        {
            placesData = json.getJSONArray("places");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        places = new ArrayList<Place>();
        for(int i = 0; i < placesData.length(); i++)
        {
            JSONObject placesJSON = placesData.getJSONObject(i);
            
            String city = placesJSON.getString("city");
            String address = placesJSON.getString("address");
            String description = placesJSON.getString("description");
            String organization = placesJSON.getString("organization");
            JSONArray visitTypesArray = placesJSON.getJSONArray("visitTypes");
            JSONArray voluntariesArray = placesJSON.getJSONArray("voluntaries");

            List<String> visitTypes = new ArrayList<>();
            for(int j = 0; j < visitTypesArray.length(); j++) 
            {
                visitTypes.add(visitTypesArray.getString(j));
            }
            
            List<String> voluntaries = new ArrayList<>();
            for(int j = 0; j < voluntariesArray.length(); j++)
            {
                voluntaries.add(voluntariesArray.getString(j));
            }

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
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        for(int i = 0; i < places.size() && i < MAX_PLACES; i++)
        {
            Place place = places.get(i);
            if(place.getVisitTypesLength() != place.getVoluntariesLength())
            {
                return new SetVisitablePlacesReply(true, false);
            }
            PreparedStatement statement 
                = this.connection.prepareStatement(PLACES_QUERY);

            statement.setString(1, getOrganization());
            statement.setString(2, place.getCity());
            statement.setString(3, place.getAddress());
            statement.setString(4, place.getDescription());

            int updatedLines = statement.executeUpdate();

            List<String> visitTypes = new ArrayList<>(place.getVisitTypes());
            List<String> voluntaries = new ArrayList<>(place.getVoluntaries());
            
            statement = this.connection.prepareStatement(PLACES_DATA_QUERY);

            for
            (
                int j = 0; 
                j < place.getVoluntariesLength() 
                    && j < MAX_VOLUNTARIES; 
                j++
            ) {
                statement.setString(1, place.getCity());
                statement.setString(2, place.getAddress());
                statement.setString(3, visitTypes.get(i));
                statement.setString(4, voluntaries.get(i));
            }
            updatedLines = statement.executeUpdate();
        }
        return new SetVisitablePlacesReply(true, true);
    }
}
