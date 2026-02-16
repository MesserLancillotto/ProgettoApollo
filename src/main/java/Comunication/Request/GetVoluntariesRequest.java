package Comunication.Request;

import java.util.*;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest;

public class GetVoluntariesRequest extends AuthenticatedRequest
{    
    private static final int MAX_FILTERS = 5;
    private Map<String, Object> filters;

    public GetVoluntariesRequest
    (
        String userID,
        String password,
        Map<String, Object> filters
    ) {
        super(ComunicationType.GET_VOLUNTARIES, userID, password);
        this.filters = (filters != null) ? filters : new HashMap<>();
    }

    public GetVoluntariesRequest(
        String userID,
        String password
    ) {
        super(ComunicationType.GET_VOLUNTARIES, userID, password);
        this.filters = new HashMap<>();
    }

    public GetVoluntariesRequest withCity(String city)
    {
        filters.put("city", city);
        return this;        
    }

    public GetVoluntariesRequest withYear(int year)
    {
        filters.put("birthYear", year);
        return this;        
    }

    public GetVoluntariesRequest greaterAge(boolean value)
    {
        filters.put("olderThanYear", value);
        return this;
    }

    public GetVoluntariesRequest withAllowedVisits(String visitType)
    {
        filters.put("visitType", visitType);
        return this;        
    }

    public GetVoluntariesRequest withName(String name)
    {
        filters.put("name", name);
        return this;        
    }

    public String toJSONString()
    {
        List<Map.Entry<String, Object>> entryList 
            = new ArrayList<>(filters.entrySet());

        for (int i = 0; i < entryList.size() && i < MAX_FILTERS; i++) 
        {
            Map.Entry<String, Object> entry = entryList.get(i);
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toString();
    }
}