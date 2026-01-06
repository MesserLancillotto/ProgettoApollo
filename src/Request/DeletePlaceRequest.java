package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class DeletePlaceRequest extends AuthenticatedRequest
{
    private String city;
    private String address;

    public DeletePlaceRequest
    (
        String userID,
        String password,
        String city,
        String address
    ) {
        super(ComunicationType.DELETE_PLACE, userID, password);
        this.city = city;
        this.address = address;
    }

    public String toJSONString()
    {
        json.add("city", city);
        json.add("address", address);
        return json.toString();
    }
}