package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetUserSubscriptionToEventRequest extends AuthenticatedRequest
{
    private String eventName;
    private Integer date;
    private List<String> friends;

    public SetUserSubscriptionToEventRequest
    (
        String userID,
        String password,
        String eventName,
        Integer date,
        List<String> friends
    ) {
        super(ComunicationType.SET_NEW_PASSWORD, userID, password);
        this.friends = new ArrayList<String>(friends);
        this.eventName = eventName;
        this.date = date;
    }

    public String toJSONString()
    {
        JSONArray friendsJSON = new JSONArray();

        for(String friend : this.friends)
        {
            friendsJSON.put(friend);
        }
        json.put("friends", friendsJSON);
        json.put("eventName", eventName);
        json.put("date", date);        
        return json.toString();
    }
}