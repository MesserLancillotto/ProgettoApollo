package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.User;
import Comunication.DatabaseObjects.Place;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetPersonalDataReply extends AuthenticatedReply 
{
    public GetPersonalDataReply(Boolean loginSuccessful, User user) 
    {
        super(loginSuccessful);
        json.put("user", user.getJSONObject());
    }

    public GetPersonalDataReply(Boolean loginSuccessful, User user, List<Place> assignedPlaces) 
    {
        super(loginSuccessful);
        JSONObject userJson = user.getJSONObject();
        JSONArray placesArray = new JSONArray();
        
        for (Place place : assignedPlaces) {
            placesArray.put(place.getJSONObject());
        }
        
        userJson.put("assignedPlaces", placesArray);
        json.put("user", userJson);
    }

    public GetPersonalDataReply(Boolean loginSuccessful)
    {
        super(loginSuccessful);
    }
}