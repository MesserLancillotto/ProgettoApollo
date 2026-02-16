package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetVoluntariesReply;
import Comunication.DatabaseObjects.User;
import Comunication.DatabaseObjects.UserRole;

public class GetVoluntariesEngine extends AuthenticatedEngine
{
    private Map<String, Object> filters;
    
    public GetVoluntariesEngine(String data) 
    {
        super(data);
        this.filters = extractFiltersFromJson();
    }
    
    private Map<String, Object> extractFiltersFromJson() 
    {
        Map<String, Object> extractedFilters = new HashMap<>();
        
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
        }
        return extractedFilters;
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        List<User> voluntaries = getFilteredVoluntaries();
        return new GetVoluntariesReply(true, voluntaries);
    }
        private List<User> getFilteredVoluntaries() throws SQLException 
    {
        List<User> voluntaries = new ArrayList<>();    
        StringBuilder queryBuilder = new StringBuilder(
            """
                SELECT userID, userName, userSurname, city, organization, 
                birth_dd, birth_mm, birth_yy, changePasswordDue
                user_since, role, changePasswordDue 
                FROM users WHERE role = 'VOLUNTARY' 
            """
        );
        
        List<Object> parameters = new ArrayList<>();
        
        if(filters.containsKey("city")) 
        {
            queryBuilder.append(" AND city LIKE ?");
            parameters.add("%" + filters.get("city") + "%");
        }
        
        if(filters.containsKey("name")) 
        {
            queryBuilder.append(" AND (userName LIKE ? OR userSurname LIKE ?)");
            parameters.add("%" + filters.get("name") + "%");
            parameters.add("%" + filters.get("name") + "%");
        }
        
        if(filters.containsKey("birthYear")) 
        {
            int birthYear = (int) filters.get("birthYear");
            boolean olderThan = filters.containsKey("olderThanYear") 
                && (boolean) filters.get("olderThanYear");
            
            if(olderThan) 
            {
                queryBuilder.append(" AND birth_yy < ?");
            } else {
                queryBuilder.append(" AND birth_yy > ?");
            }
            parameters.add(birthYear);
        }

        this.statement = connection.prepareStatement(queryBuilder.toString());
            
        for (int i = 0; i < parameters.size(); i++) 
        {
            statement.setObject(i + 1, parameters.get(i));
        }
            
        ResultSet result = statement.executeQuery();
        while (result.next()) 
        {
            User user = createUserFromResultSet(result);
            voluntaries.add(user);
        }
        
        return voluntaries;
    }
        
    private User createUserFromResultSet(ResultSet result) throws SQLException 
    {
        return new User(
            result.getString("userID"),
            result.getString("userName"),
            result.getString("userSurname"),
            result.getString("city"),
            result.getInt("birth_dd"),
            result.getInt("birth_mm"),
            result.getInt("birth_yy"),
            result.getInt("user_since"),
            UserRole.valueOf(result.getString("role")),
            result.getBoolean("changePasswordDue"),
            result.getString("organization")
        );
    }
}