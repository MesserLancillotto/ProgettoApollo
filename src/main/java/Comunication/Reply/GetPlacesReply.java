package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.Place;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetPlacesReply extends AuthenticatedReply 
{
    private static final int MAX_TERRITORIES = 1000;
    private List<Place> places;

    public GetPlacesReply(Boolean loginSuccessful, List<Place> places) 
    {
        super(loginSuccessful);
        this.places = new ArrayList<>(places);
    }

    @Override
    public String toJSONString() 
    {
        JSONArray placesJSONArray = new JSONArray();
        
        for (int i = 0; i < places.size() && i < MAX_TERRITORIES; i++) 
        {
            placesJSONArray.put(new JSONObject(places.get(i).toJSONString()));
        }
        
        json.put("places", placesJSONArray);
        return json.toString();
    }
}
