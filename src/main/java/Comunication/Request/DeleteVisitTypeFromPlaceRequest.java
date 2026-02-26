package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class DeleteVisitTypeFromPlaceRequest extends AuthenticatedRequest
{
    public DeleteVisitTypeFromPlaceRequest
    (
        String userID,
        String password,
        String city,
        String address,
        String visitType
    ) {
        super(ComunicationType.DELETE_VISIT, userID, password);
        json.put("city", city);
        json.put("address", address);
        json.put("visitType", visitType);
    }
}