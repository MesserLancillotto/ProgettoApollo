package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.*;
import Server.Engine.Helper.*;
import Comunication.Reply.*;
import Comunication.Reply.Interfaces.*;
import Comunication.DatabaseObjects.*;

public class GetVoluntariesEngine extends AuthenticatedEngine
{
    private JSONObject filters;
    
    public GetVoluntariesEngine(String data) 
    {
        super(data);
        this.filters = extractFiltersFromJson();
    }

    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if(!petitionerIsConfigurator())
        {
            return new NegativeAuthenticatedReply();
        }
        List<User> voluntaries = getFilteredVoluntaries();
        return new GetVoluntariesReply(true, voluntaries);
    }

    private JSONObject extractFiltersFromJson() 
    {
        JSONObject extractedFilters = new JSONObject();

        List<String> allowedFilters = new ArrayList<String>();
        allowedFilters.add("city");
        allowedFilters.add("birthYear");
        allowedFilters.add("olderThanYear");
        allowedFilters.add("visitType");
        allowedFilters.add("name");
        allowedFilters.add("surname");
        
        if(json.has("filters")) 
        {
            JSONObject filtersJson = json.getJSONObject("filters");
            
            if(filtersJson.has("city")) 
            {
                extractedFilters.put(
                    "city", 
                    filtersJson.getString("city")
                );
            }
            
            if(filtersJson.has("birthYear")) 
            {
                extractedFilters.put(
                    "birthYear", 
                    filtersJson.getInt("birthYear")
                );
            }
            
            if(filtersJson.has("olderThanYear")) 
            {
                extractedFilters.put(
                    "olderThanYear", 
                    filtersJson.getBoolean("olderThanYear")
                );
            }
            
            if(filtersJson.has("visitType")) 
            {
                extractedFilters.put(
                    "visitType", 
                    filtersJson.getString("visitType")
                );
            }
            
            if(filtersJson.has("name")) 
            {
                extractedFilters.put(
                    "name", 
                    filtersJson.getString("name")
                );
            }

            if(filtersJson.has("surname")) 
            {
                extractedFilters.put(
                    "surname", 
                    filtersJson.getString("surname")
                );
            }
        }
        return extractedFilters;
    }
    
    private PreparedStatement makeStatement() throws SQLException
    {
        StringBuilder queryBuilder = new StringBuilder(
            """
                SELECT userID
                FROM users 
                WHERE role = 'VOLUNTARY' 
            """
        );
        List<Object> parameters = new ArrayList<>();

        if(filters.has("city")) 
        {
            queryBuilder.append(" AND city LIKE ?");
            parameters.add("%" + filters.get("city") + "%");
        }
        
        if(filters.has("name"))
        {
            queryBuilder.append(" AND userName LIKE ?");
            parameters.add("%" + filters.get("name") + "%");
        }

        if(filters.has("surname"))
        {
            queryBuilder.append(" AND userSurname LIKE ?");
            parameters.add("%" + filters.get("surname") + "%");
        }
        
        if(filters.has("birthYear")) 
        {
            int birthYear = (int) filters.get("birthYear");
            boolean olderThan = filters.has("olderThanYear") 
                && (boolean) filters.get("olderThanYear");
            
            if(olderThan) 
            {
                queryBuilder.append(" AND birth_yy < ?");
            } else {
                queryBuilder.append(" AND birth_yy > ?");
            }
            parameters.add(birthYear);
        }
        PreparedStatement statement 
            = connection.prepareStatement(queryBuilder.toString());
            
        for (int i = 0; i < parameters.size(); i++) 
        {
            statement.setObject(i + 1, parameters.get(i));
        }
        return statement;
    }

    private List<User> getFilteredVoluntaries() throws SQLException 
    {
        JSONObject filters = json.getJSONObject("filters");

        PreparedStatement statement = makeStatement();

        List<User> voluntaries = new ArrayList<>();    
            
        ResultSet result = statement.executeQuery();
        while (result.next()) 
        {
            User user = UserCreator.createUserFromID(
                connection, 
                result.getString("userID")
            );
            voluntaries.add(user);
        }
        return voluntaries;
    }
}