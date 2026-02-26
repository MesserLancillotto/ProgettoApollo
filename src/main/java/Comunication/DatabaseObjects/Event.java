package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import Helper.*;
import Comunication.DatabaseObjects.EventInstance;

public class Event 
{   
    private JSONObject json;
    private String jsonString;

    public Event(
        String name,
        String description,
        String visitType,
        String organization,
        String city,
        String address,
        String rendezvous,
        List<EventInstance> instances
    ) {
        json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("type", visitType);
        json.put("organization", organization);
        json.put("city", city);
        json.put("address", address);
        json.put("visitType", visitType);
        json.put("rendezvous", rendezvous);
        JSONArray instancesJSON = new JSONArray();
        for(EventInstance instance : instances)
        {
            instancesJSON.put(instance.getJSONObject());
        }
        json.put("instances", instancesJSON);
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