package Comunication.Request.Interfaces;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;

public abstract class AuthenticatedRequest extends Request
{

    
    public AuthenticatedRequest
    (
        ComunicationType comunicationType,
        String userID,
        String password
    ) {
        super(comunicationType);
        json.put("userID", userID);
        json.put("password", password);
    }
}