package Comunication.Request;

import org.json.*;
import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 
import Comunication.DatabaseObjects.*;

public class SetNewUserRequest extends AuthenticatedRequest
{
    public SetNewUserRequest
    (
        String name,
        String surname,
        String password,
        String city,
        Integer birth_dd,
        Integer birth_mm,
        Integer birth_yy,
        Integer user_since,
        UserRole role,
        String organization
    ) {
        super(ComunicationType.SET_NEW_USER, "", "");
        json.put("name", name);
        json.put("surname", surname);
        json.put("password", password);
        json.put("city", city);
        json.put("birth_dd", birth_dd);
        json.put("birth_mm", birth_mm);
        json.put("birth_yy", birth_yy);
        json.put("user_since", user_since);
        json.put("role", role);
        json.put("organization", organization);
    }
}