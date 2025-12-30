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
    private static final int MAX_PLACES = 1000;
    private static final int MAX_VOLUNTARIES = 1000;

    private List<Place> places;
    private JSONArray placesData;

    public SetVisitablePlacesEngine 
    (
        String data
    ) {
        super(data);
        placesData = json.getJSONArray("places");
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
            for(int j = 0; j < voluntariesArray.length(); i++)
            {
                voluntaries.add(voluntariesArray.getString(i));
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
    
    public AuthenticatedReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new SetVisitablePlacesReply(false, false);
        }
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new SetVisitablePlacesReply(false, false);
            }
            for(int i = 0; i < places.size() && i < MAX_PLACES; i++)
            {
                Place place = places.get(i);
                if(place.getVisitTypesLength() != place.getVoluntariesLength())
                {
                    return new SetVisitablePlacesReply(true, false);
                }
                String query = "INSERT INTO places VALUES ( ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, place.getCity());
                statement.setString(2, place.getAddress());
                statement.setString(3, place.getDescription());
                if(statement.executeUpdate() != 1)
                {
                    assert true;
                }
                List<String> visitTypes = new ArrayList<>(place.getVisitTypes());
                List<String> voluntaries = new ArrayList<>(place.getVoluntaries());
                for
                (
                    int j = 0; 
                    j < place.getVoluntariesLength() 
                        && j < MAX_VOLUNTARIES; 
                    j++
                ) {
                    query = "INSERT INTO placesData VALUES ( ?, ?, ?, ? )";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, place.getCity());
                    statement.setString(2, place.getAddress());
                    statement.setString(3, visitTypes.get(i));
                    statement.setString(4, voluntaries.get(i));
                    if(statement.executeUpdate() != 1)
                    {
                        assert true;
                    }
                }
            }
            return new SetVisitablePlacesReply(true, true);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new SetVisitablePlacesReply(false, false);
    }
}
