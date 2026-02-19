package Comunication.Request;

import java.util.*;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest;

public class GetPersonalDataRequest extends AuthenticatedRequest
{    
    public GetPersonalDataRequest
    (
        String userID,
        String password
    ) {
        super(ComunicationType.GET_PERSONAL_DATA, userID, password);
    }

    public String toJSONString()
    {
        return json.toString();
    }
}