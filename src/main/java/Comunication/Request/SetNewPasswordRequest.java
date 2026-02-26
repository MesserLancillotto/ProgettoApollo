package Comunication.Request;

// external
import org.json.*;
// custom
import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetNewPasswordRequest extends AuthenticatedRequest
{
    public SetNewPasswordRequest
    (
        String userID,
        String password,
        String newPassword
    ) {
        super(ComunicationType.SET_NEW_PASSWORD, userID, password);
        json.put("newPassword", newPassword);
    }
}