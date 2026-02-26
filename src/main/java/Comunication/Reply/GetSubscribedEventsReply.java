package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.Event;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetSubscribedEventsReply extends AuthenticatedReply 
{
    public GetSubscribedEventsReply
    (
        Boolean loginSuccessful, 
        List<Event> events
    ) {
        super(loginSuccessful);
        JSONArray eventsJSONArray = new JSONArray();
        for (Event event : events) 
        {
            eventsJSONArray.put(event.getJSONObject());
        }
        json.put("events", eventsJSONArray);
    }

    public GetSubscribedEventsReply(Boolean loginSuccessful) 
    {
        super(loginSuccessful);
        json.put("events", JSONObject.NULL);
    }
}
