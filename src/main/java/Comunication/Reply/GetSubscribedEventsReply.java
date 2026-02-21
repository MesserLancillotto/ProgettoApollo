package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.Event;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetSubscribedEventsReply extends AuthenticatedReply 
{
    private List<Event> events;

    public GetSubscribedEventsReply(Boolean loginSuccessful, List<Event> events) 
    {
        super(loginSuccessful);
        this.events = new ArrayList<>(events);
    }

    public GetSubscribedEventsReply(Boolean loginSuccessful) 
    {
        super(loginSuccessful);
        this.events = null;
    }

    public String toJSONString() 
    {
        if(events == null)
        {
            return json.toString();
        }

        JSONArray eventsJSONArray = new JSONArray();

        for (Event Event : events) 
        {
            eventsJSONArray.put(new JSONObject(Event.toJSONString()));
        }
        
        json.put("events", eventsJSONArray);
        return json.toString();
    }
}
