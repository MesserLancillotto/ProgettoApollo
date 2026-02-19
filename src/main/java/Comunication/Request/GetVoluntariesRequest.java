package Comunication.Request;

import java.util.*;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest;

public class GetVoluntariesRequest extends AuthenticatedRequest
{    
    private static final int MAX_FILTERS = 5;
    private JSONObject filters;

    public GetVoluntariesRequest
    (
        String userID,
        String password,
        JSONObject filters
    ) {
        super(ComunicationType.GET_VOLUNTARIES, userID, password);
        this.filters = (filters != null) ? filters : new JSONObject();
    }

    public GetVoluntariesRequest(
        String userID,
        String password
    ) {
        super(ComunicationType.GET_VOLUNTARIES, userID, password);
        this.filters = new JSONObject();
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
    
    public GetVoluntariesRequest withSurname(String surname)
    {
        filters.put("surname", surname);
        return this;        
    }

    public String toJSONString()
    {
        json.put("filters", filters);
        return json.toString();
    }
}