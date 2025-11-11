package RequestReply.Request;

import java.util.*;
import org.json.*;

public class GetVoluntariesRequest implements RequestType
{    
    private Map<String, Object> filters;

    public GetVoluntariesRequest
    (
        Map<String, Object> filters
    ) {
        this.filters = filters;
    }

    private GetVoluntariesRequest withCity(String city)
    {
        filters.put("city", city);
        return this;        
    }

    private GetVoluntariesRequest withYear(int year)
    {
        // anno degli utenti ricercati
        filters.put("birthYear", year);
        return this;        
    }

    private GetVoluntariesRequest greaterAge(boolean value)
    {
        // età degli utenti, meno o più dell'anno segnato sopra
        filters.put("olderThanYear", value);
        return this;
    }

    private GetVoluntariesRequest withAllowedVisits(String visitType)
    {
        filters.put("visitType", visitType);
        return this;        
    }

    private GetVoluntariesRequest withName(String name)
    {
        filters.put("name", name);
        return this;        
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toString();
    }
}