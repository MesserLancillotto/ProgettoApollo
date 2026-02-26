package Comunication.Request.Interfaces;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;

public abstract class AuthenticatedRequest extends Request
{
    protected JSONObject json;
    protected String jsonString;
    
    public AuthenticatedRequest
    (
        ComunicationType comunicationType,
        String userID,
        String password
    ) {
        super(comunicationType);
        json = new JSONObject();
        json.put("comunicationType", comunicationType.name());
        json.put("userID", userID);
        json.put("password", password);
    }

    public String toJSONString()
    {
        if(jsonString == null)
        {
            jsonString = json.toString();
        }
        return jsonString;
    }
}