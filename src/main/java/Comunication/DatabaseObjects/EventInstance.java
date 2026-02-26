package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import Helper.*;

public class EventInstance
{   
/**
* @param start_date
* @param end_date
* @param state
* @param voluntaries
* @param users
*/

    private static final int MAX_VOLUNTARIES = 100;

    private Integer start_date;
    private Integer end_date;
    private String state;
    private List<String> voluntaries;
    private List<String> users;

    private JSONObject json;

    public EventInstance(
        Integer start_date,
        Integer end_date,
        String state,
        List<String> voluntaries,
        List<String> users
    ) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.state = state;
        this.voluntaries = List.copyOf(voluntaries);
        this.users = List.copyOf(users);
    }

    private JSONObject getJSONObject()
    {
        if(json != null)
        {
            return json;
        }
        json.put("start_date", this.start_date);
        json.put("end_date", this.end_date);
        json.put("state", this.state);
        json.put("voluntaries", 
            JsonListConverter.listToJsonArray(voluntaries));
        json.put("users", 
            JsonListConverter.listToJsonArray(users));
        return json;
    }

    public String toJSONString() 
    {
        if(jsonString == null)
        {
            jsonString = getJSONObject().toString(); 
        }
        return jsonString;
    }
}