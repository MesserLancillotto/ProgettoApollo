package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetPlacesReply;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.DatabaseObjects.Place;

public class GetPlacesEngine extends AuthenticatedEngine
{
    private JSONObject filters;
    
    public GetPlacesEngine(String data) 
    {
        super(data);
        this.filters = extractFiltersFromJson();
    }

    private JSONObject extractFiltersFromJson()
    {
        JSONObject extractedFilters = new JSONObject();
        
        List<String> allowedFilters = new ArrayList<String>();
        allowedFilters.add("city");
        allowedFilters.add("address");
        allowedFilters.add("visitType");

        if(!json.has("filters")) 
        {
            return new JSONObject();
        }

        for(String filter : allowedFilters)
        {
            if(json.has(filter))
            {
                extractedFilters.put(filter, json.getString(filter));
            }
        }
        return extractedFilters;
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {   
        StringBuilder queryBuilder = new StringBuilder(
            """
                SELECT city, address, visitType, 
                description, organization, userID
                FROM places WHERE 1=1
            """
        );
        
        List<String> parameters = new ArrayList<String>();
            
        if (filters.has("city"))
        {
            queryBuilder.append(" AND city LIKE ? ");
            parameters.add("%" + filters.get("city") + "%");
        }
        if (filters.has("address"))
        {
            queryBuilder.append(" AND address LIKE ? ");
            parameters.add("%" + filters.get("address") + "%");
        }
        if (filters.has("visitType"))
        {
            queryBuilder.append(" AND visitType LIKE ? ");
            parameters.add("%" + filters.get("visitType") + "%");
        }
            
        PreparedStatement statement = 
            connection.prepareStatement(queryBuilder.toString());
        
        int i = 1;
        for(String parameter : parameters)
        {
            statement.setString(i, parameter);
            i++;
        }
            
        ResultSet result = statement.executeQuery();
        ArrayList<Place> places = new ArrayList<Place>();
            
        while(result.next())
        {
            String city = result.getString("city");
            String address = result.getString("address");
            String visitType = result.getString("visitType");
            String description = result.getString("description");
            String organization = result.getString("organization");
            String defaultVoluntary = result.getString("userID");
            
            Place place = new Place(
                city, 
                address, 
                visitType,
                description,
                organization,
                defaultVoluntary
            );
            places.add(place);
        }
        return new GetPlacesReply(true, places);
    }
}