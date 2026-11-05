package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetVoluntarySubscriptionToEventRequest extends AuthenticatedRequest
{
    private String eventName;
    private Integer date;
    private List<String> friends;

    public SetVoluntarySubscriptionToEventRequest
    (
        String userID,
        String password,
        String eventName,
        Integer date,
        List<String> friends
    ) {
        super(ComunicationType.SET_USER_SUBSCRIPTION_TO_EVENT, userID, password);
        json.put("friends", new JSONArray(friends));
        json.put("eventName", eventName);
        json.put("date", date);
    }
}