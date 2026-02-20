package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.Place;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetSubscribedEventsReply extends AuthenticatedReply 
{
    private List<Place> places;

    public GetSubscribedEventsReply(Boolean loginSuccessful, List<Place> places) 
    {
        super(loginSuccessful);
        this.places = new ArrayList<>(places);
    }

    public GetSubscribedEventsReply(Boolean loginSuccessful) 
    {
        super(loginSuccessful);
        this.places = null;
    }

    public String toJSONString() 
    {
        if(places == null)
        {
            return json.toString();
        }

        JSONArray placesJSONArray = new JSONArray();

        for (Place place : places) 
        {
            placesJSONArray.put(new JSONObject(place.toJSONString()));
        }
        
        json.put("places", placesJSONArray);
        return json.toString();
    }
}
