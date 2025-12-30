package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.ReplyInterface;
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
        
        if (json.has("filters")) 
        {
            JSONObject filtersJson = json.getJSONObject("filters");
            
            if (filtersJson.has("city")) 
            {
                extractedFilters.put(
                    "city", 
                    filtersJson.getString("city")
                );
            }
            
            if (filtersJson.has("birthYear")) 
            {
                extractedFilters.put(
                    "birthYear", 
                    filtersJson.getInt("birthYear")
                );
            }
            
            if (filtersJson.has("olderThanYear")) 
            {
                extractedFilters.put(
                    "olderThanYear", 
                    filtersJson.getBoolean("olderThanYear")
                );
            }
            
            if (filtersJson.has("visitType")) 
            {
                extractedFilters.put(
                    "visitType", 
                    filtersJson.getString("visitType")
                );
            }
            
            if (filtersJson.has("name")) 
            {
                extractedFilters.put(
                    "name", 
                    filtersJson.getString("name")
                );
            }
        }
        return extractedFilters;
    }
    
    public ReplyInterface handleRequest()
    {
        if (!connectDB()) 
        {
            return new GetVoluntariesReply(false, null);
        }
        
        try 
        {
            if (!petitionerCanLogIn()) 
            {
                return new GetVoluntariesReply(false, null);
            }
            List<User> voluntaries = getFilteredVoluntaries();
            return new GetVoluntariesReply(true, voluntaries);
        } catch (Exception e) {
            e.printStackTrace();
            return new GetVoluntariesReply(true, null);
        }
    }
    
    private List<User> getFilteredVoluntaries() throws SQLException 
    {
        List<User> voluntaries = new ArrayList<>();    
        StringBuilder queryBuilder = new StringBuilder(
            """
                SELECT userID, name, surname, city, organization, 
                birth_dd, birth_mm, birth_yy, changePasswordDue
                user_since, role, changePasswordDue 
                FROM users WHERE role = 'VOLUNTARY' 
            """
        );
        
        List<Object> parameters = new ArrayList<>();
        
        if (filters.containsKey("city")) 
        {
            queryBuilder.append(" AND city LIKE ?");
            parameters.add("%" + filters.get("city") + "%");
        }
        
        if (filters.containsKey("name")) 
        {
            queryBuilder.append(" AND (name LIKE ? OR surname LIKE ?)");
            parameters.add("%" + filters.get("name") + "%");
            parameters.add("%" + filters.get("name") + "%");
        }
        
        if (filters.containsKey("birthYear")) 
        {
            int birthYear = (int) filters.get("birthYear");
            boolean olderThan = filters.containsKey("olderThanYear") && 
                (boolean) filters.get("olderThanYear");
            
            if (olderThan) 
            {
                queryBuilder.append(" AND birth_yy < ?");
            } else {
                queryBuilder.append(" AND birth_yy > ?");
            }
            parameters.add(birthYear);
        }
        
        try 
        (
            PreparedStatement statement = 
                connection.prepareStatement(queryBuilder.toString())
        ) {
            for (int i = 0; i < parameters.size(); i++) 
            {
                statement.setObject(i + 1, parameters.get(i));
            }
            
            try 
            (
                ResultSet result = statement.executeQuery()
            ) {
                while (result.next()) 
                {
                    if (shouldIncludeUser(result)) 
                    {
                        User user = createUserFromResultSet(result);
                        voluntaries.add(user);
                    }
                }
            }
        }
        return voluntaries;
    }
    
    private boolean shouldIncludeUser(ResultSet result) throws SQLException 
    {
        if (filters.containsKey("visitType"))
        {
            String visitType = (String) filters.get("visitType");
            String userId = result.getString("userID");    
            String query = 
            """
                SELECT COUNT(*) FROM userPermissions " +
                WHERE userID = ? AND visitType = ?
            """;
            try 
            (
                PreparedStatement statement = connection.prepareStatement(query)
            ) {
                statement.setString(1, userId);
                statement.setString(2, visitType);
                
                try 
                (
                    ResultSet _result = statement.executeQuery();
                )
                {
                    
                    if (_result.next()) 
                    {
                        return _result.getInt(1) > 0;
                    }
                } catch(Exception e) 
                {
                    e.printStackTrace();
                    return false;
                }
            } catch(Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    
    private User createUserFromResultSet(ResultSet result) throws SQLException 
    {
        return new User(
            result.getString("userID"),
            result.getString("name"),
            result.getString("surname"),
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