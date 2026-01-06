package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class DeleteVisitRequest extends AuthenticatedRequest
{
    private String city;
    private String address;
    private String visitType;

    public DeleteVisitRequest
    (
        String userID,
        String password,
        String city,
        String address,
        String visitType
    ) {
        super(ComunicationType.DELETE_VOLUNTARY, userID, password);
        this.city = city;
        this.address = address;
        this.visitType = visitType;
    }

    public String toJSONString()
    {
        json.add("city", city);
        json.add("address", address);
        json.add("visitType", visitType);
        return json.toString();
    }
}