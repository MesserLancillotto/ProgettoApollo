package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class GetSubscribedEventsRequest extends AuthenticatedRequest
{
    String targetID;

    public GetSubscribedEventsRequest
    (
        String userID,
        String password,
        String targetID
    ) {
        super(ComunicationType.GET_SUBSCRIBED_EVENTS, userID, password);
        json.put("targetID", targetID);
    }
}