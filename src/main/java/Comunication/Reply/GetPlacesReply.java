package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.Place;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetPlacesReply extends AuthenticatedReply 
{
    public GetPlacesReply(Boolean loginSuccessful, List<Place> places) 
    {
        super(loginSuccessful);
        JSONArray placesJSONArray = new JSONArray();
        for (Place place : places) 
        {
            placesJSONArray.put(place.getJSONObject());
        }
        json.put("places", placesJSONArray);
    }
}
