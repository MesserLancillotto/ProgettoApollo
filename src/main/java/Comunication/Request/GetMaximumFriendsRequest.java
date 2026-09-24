package Comunication.Request;

import java.util.*;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest;

public class GetMaximumFriendsRequest extends AuthenticatedRequest
{    
    public GetMaximumFriendsRequest
    (   
        String userID,
        String password,
        String organization
    ) {
        super(ComunicationType.GET_MAXIMUM_FRIENDS, userID, password);
        json.put("organization", organization);
    }
}