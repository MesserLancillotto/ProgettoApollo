package Comunication.Request.Interfaces;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;

public abstract class AuthenticatedRequest extends Request
{
    private String userID;
    private String password;
    public JSONObject json;

    public AuthenticatedRequest
    (
        ComunicationType comunicationType,
        String userID,
        String password
    ) {
        super(comunicationType);
        this.userID = userID;
        this.password = password;
        json = new JSONObject();
        json.put("comunicationType", comunicationType.name());
        json.put("userID", userID);
        json.put("password", password);
    }

    public abstract String toJSONString();
}