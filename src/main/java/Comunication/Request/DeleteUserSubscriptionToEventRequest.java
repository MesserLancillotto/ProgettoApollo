package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class DeleteUserSubscriptionToEventRequest extends AuthenticatedRequest
{
    public DeleteUserSubscriptionToEventRequest
    (
        String userID,
        String password,
        String eventName,
        Integer date
    ) {
        super(
            ComunicationType.DELETE_USER_SUBSCRIPTION_TO_EVENT, 
            userID, 
            password
        );
        json.put("eventName", eventName);
        json.put("date", date);   
    }
}