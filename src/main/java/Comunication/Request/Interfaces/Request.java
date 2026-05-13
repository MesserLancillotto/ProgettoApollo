package Comunication.Request.Interfaces;

import org.json.*;

import Comunication.Request.Interfaces.RequestInterface;
import Comunication.ComunicationType.ComunicationType;

public abstract class Request implements RequestInterface
{
    private ComunicationType comunicationType;
    protected JSONObject json;
    protected String jsonString;


    public Request
    (
        ComunicationType comunicationType
    ) {
        this.comunicationType = comunicationType;
        json = new JSONObject();
        json.put("comunicationType", comunicationType.name());
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