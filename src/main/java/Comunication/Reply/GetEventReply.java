package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.Event;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.DatabaseObjects.Event;

public class GetEventReply extends AuthenticatedReply 
{
    public GetEventReply(Boolean loginSuccessful, List<Event> events) 
    {
        super(loginSuccessful);
        JSONArray eventsJSON = new JSONArray();
        for (Event event : events) 
        {
            eventsJSON.put(event.getJSONObject());
        }
        json.put("events", eventsJSON);
    }
}
