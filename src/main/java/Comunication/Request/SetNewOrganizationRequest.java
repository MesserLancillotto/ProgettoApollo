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
        json.put("organization", organization);
        json.put("territories", new JSONArray(territories));
    }
}