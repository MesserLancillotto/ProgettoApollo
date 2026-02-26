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

    private static String QUERY
        = "INSERT INTO places VALUES ( ?, ?, ?, ? )";

    public SetVisitablePlacesEngine 
    (
        String data
    ) {
        super(data);
        JSONArray placesData = json.has("places") 
            ? json.getJSONArray("places") 
            : new JSONArray();
        
        places = new ArrayList<Place>();
        for(int i = 0; i < placesData.length(); i++)
        {
            places.add(new Place(placesData.getJSONObject(i)));
        }
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        int updatedLines = 0;

        for(Place place : places)
        {
            PreparedStatement statement 
                = this.connection.prepareStatement(QUERY);

            statement.setString(1, getOrganization());
            statement.setString(2, place.getCity());
            statement.setString(3, place.getAddress());
            statement.setString(4, place.getDescription());

            updatedLines += statement.executeUpdate();
        }
        return new SetVisitablePlacesReply(true, updatedLines > 0);
    }
}
