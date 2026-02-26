package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import Helper.*;
import Comunication.DatabaseObjects.EventInstance;

public class Event 
{   
/**
* @param name
* @param description
* @param type
* @param organization
* @param city
* @param address
* @param rendezvous
* 
*/

    private String name;
    private String description;
    private String type;
    private String organization;
    private String city;
    private String address;
    private String visitType;
    private String rendezvous;
    private List<EventInstance> instances;

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
        this.name = name;
        this.description = description;
        this.visitType = visitType;
        this.organization = organization;
        this.city = city;
        this.address = address;
        this.rendezvous = rendezvous;
        this.instances = List.copyOf(instances);
    }

    public JSONObject getJSONObject()
    {
        if(json != null)
        {
            return json;
        }
        json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("type", type);
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