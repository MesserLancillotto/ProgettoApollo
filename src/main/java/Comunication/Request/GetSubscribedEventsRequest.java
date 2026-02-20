package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class GetSubscribedEventsRequest extends AuthenticatedRequest
{
    String state;

    public GetSubscribedEventsRequest
    (
        String userID,
        String password
    ) {
        super(ComunicationType.GET_SUBSCRIBED_EVENTS, userID, password);
    }

    public String toJSONString()
    {
        return json.toString();
    }
}