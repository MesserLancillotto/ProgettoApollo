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

    public SetNewUserRequest(String userName, String password, String city, Integer birthYear) {
        super(ComunicationType.SET_NEW_USER, "", "");
        String name = userName != null ? userName : "";
        String surname = "";
        if (userName != null && userName.contains(".")) {
            String[] parts = userName.split("\\.", 2);
            name = parts[0];
            surname = parts[1];
        }
        json.put("name", name);
        json.put("surname", surname);
        json.put("password", password);
        json.put("city", city);
        json.put("birth_dd", 1);
        json.put("birth_mm", 1);
        json.put("birth_yy", birthYear != null ? birthYear : 2000);
        json.put("user_since", 2026);
        json.put("role", UserRole.CONFIGURATOR);
        json.put("organization", "");
    }
}