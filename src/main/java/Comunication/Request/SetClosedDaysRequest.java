package Comunication.Request;

import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetClosedDaysRequest extends AuthenticatedRequest
{
    public SetClosedDaysRequest
    (
        String userID,
        String password,
        Integer closure_start_date,
        Integer closure_end_date
    ) {
        super(ComunicationType.SET_CLOSED_DAYS, userID, password);
        json.put("closure_start_date", closure_start_date);
        json.put("closure_end_date", closure_end_date);
    }
}