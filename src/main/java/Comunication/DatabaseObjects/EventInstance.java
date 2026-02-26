package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import Helper.*;

public class EventInstance
{   
    private JSONObject json;
    private String jsonString;

    public EventInstance(
        Integer start_date,
        Integer end_date,
        String state,
        List<String> voluntaries,
        List<String> users
    ) {
        json = new JSONObject();
        json.put("start_date", start_date);
        json.put("end_date", end_date);
        json.put("state", state);
        json.put("voluntaries", 
            JsonListConverter.listToJsonArray(voluntaries));
        json.put("users", 
            JsonListConverter.listToJsonArray(users));
    }

    public JSONObject getJSONObject()
    {
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