package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.DatabaseObjects.Place;
import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class EditVisitablePlacesRequest extends AuthenticatedRequest
{   
    public EditVisitablePlacesRequest
    (
        String userID,
        String password,
        String city,
        String address,
        String visitType,
        String newDefauldVoluntary
    ) {
        super(ComunicationType.EDIT_VISITABLE_PLACES, userID, password);
        json.put("city", city);
        json.put("address", address);
        json.put("visitType", visitType);
        json.put("newDefauldVoluntary", newDefauldVoluntary);    
    }
}