package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class GetAllowedVisitTypesRequest extends AuthenticatedRequest
{
    String state;

    public GetAllowedVisitTypesRequest
    (
        String userID,
        String password
    ) {
        super(ComunicationType.GET_ALLOWED_VISIT_TYPES, userID, password);
    }

    public String toJSONString()
    {
        return json.toString();
    }
}