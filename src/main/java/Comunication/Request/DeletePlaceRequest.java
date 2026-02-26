package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class DeletePlaceRequest extends AuthenticatedRequest
{
    private String jsonString;

    public DeletePlaceRequest
    (
        String userID,
        String password,
        String city,
        String address,
        String visitType
    ) {
        super(ComunicationType.DELETE_PLACE, userID, password);
        json.put("city", city);
        json.put("address", address);
        json.put("visitType", visitType);
        jsonString = null;
    }


}