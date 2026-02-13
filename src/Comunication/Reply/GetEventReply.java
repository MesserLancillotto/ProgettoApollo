package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.Event;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.DatabaseObjects.Event;

public class GetEventReply extends AuthenticatedReply 
{
    private static final int MAX_EVENTS = 365;
    private List<Event> events;

    public GetEventReply(Boolean loginSuccessful, List<Event> events) 
    {
        super(loginSuccessful);
        this.events = new ArrayList<Event>(events);
    }

    @Override
    public String toJSONString() 
    {
        json.put("loginSuccessful", loginSuccessful);
        JSONArray array = new JSONArray();
        
        for (int i = 0; i < events.size() && i < MAX_EVENTS; i++) 
        {
            array.put(new JSONObject(events.get(i).toJSONString()));
        }
        json.put("events", array);
        return json.toString();
    }
}
