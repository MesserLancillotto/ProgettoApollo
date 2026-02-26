package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class GetPlacesRequest extends AuthenticatedRequest
{
    private JSONObject filters;

    public GetPlacesRequest
    (
        String userID,
        String password
    ) {
        super(ComunicationType.GET_PLACES, userID, password);
        this.filters = new JSONObject();
    }

    public GetPlacesRequest withCity(String city)
    {
        filters.put("city", city);
        return this;
    }

    public GetPlacesRequest withAddress(String address)
    {
        filters.put("address", address);
        return this;
    }

    public GetPlacesRequest withVisitType(List<String> visitTypes)
    {
        filters.put("visitTypes", new JSONArray(visitTypes));
        return this;
    }

    public GetPlacesRequest withState(List<String> states)
    {
        filters.put("states", new JSONArray(states));
        return this;
    }

    @Override
    public String toJSONString()
    {
        if(json.has("filters"))
        {
            json.remove("filters");
        }
        json.put("filters", filters);
        return json.toString();
    }
}