package Comunication.Request;

// external
import org.json.*;
// custom
import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetNewPasswordRequest extends AuthenticatedRequest
{
    private String newPassword;

    public SetNewPasswordRequest
    (
        String userID,
        String password,
        String newPassword
    ) {
        super(ComunicationType.SET_NEW_PASSWORD, userID, password);
        this.newPassword = newPassword;
    }

    public String toJSONString()
    {
        json.put("newPassword", newPassword);
        return json.toString();
    }
}