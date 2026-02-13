package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;

public class Event 
{   
    private static final int MAX_VOLUNTARIES = 100;

    private String name;
    private String description;
    private String type;
    private String organization;
    private String city;
    private String address;
    private String rendezvous;
    private String state;
    private List<String> voluntaries;
    private List<List<Integer>> singleEvent;

     public Event(
        String name,
        String description,
        String type,
        String organization,
        String city,
        String address,
        String rendezvous,
        String state,
        List<String> voluntaries,
        List<List<Integer>> singleEvent
    ) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.organization = organization;
        this.city = city;
        this.address = address;
        this.rendezvous = rendezvous;
        this.state = state;
        this.voluntaries = List.copyOf(voluntaries);
        this.singleEvent = List.copyOf(singleEvent);
    }

    public String toJSONString() {
        JSONObject json = new JSONObject();
        
        json.put("name", name);
        json.put("description", description);
        json.put("type", type);
        json.put("organization", organization);
        json.put("city", city);
        json.put("address", address);
        json.put("rendezvous", rendezvous);
        json.put("state", state);
        
        JSONArray voluntariesArray = new JSONArray();
        for(int i = 0; i < voluntaries.size() && i < MAX_VOLUNTARIES; i++) 
        {
            voluntariesArray.put(voluntaries.get(i));
        }
        json.put("voluntaries", voluntariesArray);
        
        JSONArray singleEventArray = new JSONArray();
        for(List<Integer> event : singleEvent) 
        {
            JSONArray eventArray = new JSONArray();
            for(Integer value : event) 
            {
                eventArray.put(value);
            }
            singleEventArray.put(eventArray);
        }
        json.put("singleEvent", singleEventArray);
        
        return json.toString();
    }
}