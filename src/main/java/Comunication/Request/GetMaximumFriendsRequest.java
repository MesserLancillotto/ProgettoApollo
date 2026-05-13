package Comunication.Request;

import java.util.*;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest;

public class GetMaximumFriendsRequest extends AuthenticatedRequest
{    
    public GetMaximumFriendsRequest
    (
        String organization
    ) {
        super(ComunicationType.GET_MAXIMUM_FRIENDS, "", "");
        json.put("organization", organization);
    }
}