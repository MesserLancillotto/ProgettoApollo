package RequestReply.Request;

import java.util.*;
import org.json.*;

public class GetVoluntariesForVisitRequest implements RequestType
{
    
    private String eventName;
    private Map<String, Object> filters;

    public GetVoluntariesForVisitRequest
    (
        String eventName
    ) {
        this.eventName = eventName;
        this.filters = null;
    }

    public GetVoluntariesForVisitRequest
    (
        Map<String, Object> filters
    ) {
        this.eventName = null;
        this.filters = filters;
    }

    public GetVoluntariesForVisitRequest
    (
        String eventName,
        Map<String, Object> filters
    ) {
        this.eventName = eventName;
        this.filters = filters;
    }

    private GetVoluntariesForVisitRequest withCity(String city)
    {
        filters.put("city", city);
        return this;        
    }

    private GetVoluntariesForVisitRequest withYear(int year)
    {
        // anno degli utenti ricercati
        filters.put("year", year);
        return this;        
    }

    private GetVoluntariesForVisitRequest greaterAge(boolean value)
    {
        // età degli utenti, meno o più dell'anno segnato sopra
        filters.put("olderThanYear", value);
        return this;
    }

    private GetVoluntariesForVisitRequest withAllowedVisits(String visitType)
    {
        filters.put("visitType", visitType);
        return this;        
    }

    private GetVoluntariesForVisitRequest withOrganization(String organization)
    {
        filters.put("organization", organization);
        return this;        
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        if(eventName != null)
        {
            json.put("eventName", eventName);
            return json.toString();
        }
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toString();
    }
}