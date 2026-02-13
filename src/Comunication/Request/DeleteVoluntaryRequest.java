package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class DeleteVoluntaryRequest extends AuthenticatedRequest
{
    private String targetID;

    public DeleteVoluntaryRequest
    (
        String userID,
        String password,
        String targetID
    ) {
        super(ComunicationType.DELETE_VOLUNTARY, userID, password);
        this.targetID = targetID;
    }

    public String toJSONString()
    {
        json.put("targetID", targetID);
        return json.toString();
    }
}