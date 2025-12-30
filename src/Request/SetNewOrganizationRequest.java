package Comunication.Request;

import org.json.*;
import java.util.List;
import java.util.ArrayList;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetNewOrganizationRequest extends AuthenticatedRequest
{
    String organization;
    private ArrayList<String> territories; 

    public SetNewOrganizationRequest
    (
        String userID, 
        String password,
        String organization,
        List<String> territories
    ) {
        super(ComunicationType.SET_NEW_ORGANIZATION, userID, password);
        this.organization = organization;
        this.territories = new ArrayList<>(territories);
    }

    public String toJSONString()
    {
        json.put("organization", organization);
        JSONArray territoriesJSONArray = new JSONArray(territories);
        json.put("territories", territoriesJSONArray);
        return json.toString();
    }
}