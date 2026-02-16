package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.GetPlacesReply;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.DatabaseObjects.Place;

public class GetPlacesEngine extends AuthenticatedEngine
{
    private Map<String, Object> filters;
    
    public GetPlacesEngine(String data) 
    {
        super(data);
        this.filters = extractFiltersFromJson();
    }

    private Map<String, Object> extractFiltersFromJson()
    {
        Map<String, Object> extractedFilters = new HashMap<>();

        List<String> allowedFilters = new ArrayList<String>();
        allowedFilters.put("city");
        allowedFilters.put("address");
        allowedFilters.put("visitTypes");
        allowedFilters.put("states");

        for(String filter : allowedFilters)
        {
            if(json.jas(filter))
            {
                extractedFilters.put(
                    filter,
                    filtersJson.getString())
            }
        }

        if(json.has("filters")) 
        {
            JSONObject filtersJson = json.getJSONObject("filters");

            if(filtersJson.has())
            {

            }

            for (int i = 0; i < keys.size() && i < MAX_FILTERS; i++) 
            {
                String key = keys.get(i);
                filters.put(key, filtersJSONObject.get(key));
            }
        }
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {   
        StringBuilder queryBuilder = new StringBuilder(
            """
                SELECT city, address, description, organization 
                FROM places WHERE 1=1
            """
        );
        
        List<String> parameters = new ArrayList<String>();
            
        if (filters.containsKey("city"))
        {
            queryBuilder.append(" AND city LIKE ? ");
            parameters.add("%" + filters.get("city") + "%");
        }
        if (filters.containsKey("address"))
        {
            queryBuilder.append(" AND address LIKE ? ");
            parameters.add("%" + filters.get("address") + "%");
        }
            
        PreparedStatement statement = 
            connection.prepareStatement(queryBuilder.toString());
            
        for(int i = 0; i < parameters.size() && i < MAX_FILTERS; i++)
        {
            statement.setString(i + 1, parameters.get(i));
        }
            
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
                SELECT visitType, voluntary FROM placesData 
                WHERE city = ? AND address = ?
            """;
            
            PreparedStatement detailsStmt = 
                connection.prepareStatement(detailsQuery)
                
            detailsStmt.setString(1, city);
            detailsStmt.setString(2, address);
                
            ResultSet detailsResult = detailsStmt.executeQuery();
            ArrayList<String> visitTypes = new ArrayList<String>();
            ArrayList<String> voluntaries = new ArrayList<String>();                
                
            int i = 0;
            while(detailsResult.next() && i < MAX_RESULTS)
            {
                i++;
                visitTypes.add(detailsResult.getString("visitType"));
                voluntaries.add(detailsResult.getString("voluntary"));
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
        return new GetPlacesReply(true, places);
    }
}