package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class GetEventRequest extends AuthenticatedRequest
{
    String state;

    public GetEventRequest
    (
        String userID,
        String password,
        String state
    ) {
        super(ComunicationType.GET_EVENT, userID, password);
        this.state = state;
    }

    public String toJSONString()
    {
        json.put("state", state);
        return json.toString();
    }
}