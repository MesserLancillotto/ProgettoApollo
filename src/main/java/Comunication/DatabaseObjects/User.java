package Comunication.DatabaseObjects;

import org.json.*;

import Comunication.DatabaseObjects.UserRole;


public class User 
{
    private String userID;
    private String name;
    private String surname;
    private String city;
    private Integer birth_dd;
    private Integer birth_mm;
    private Integer birth_yy;
    private Integer user_since;
    private UserRole role;
    private Boolean changePasswordDue;
    private String organization;

    public User
    (
        String userID,
        String name,
        String surname,
        String city,
        Integer birth_dd,
        Integer birth_mm,
        Integer birth_yy,
        Integer user_since,
        UserRole role,
        Boolean changePasswordDue,
        String organization
    ) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.birth_dd = birth_dd;
        this.birth_mm = birth_mm;
        this.birth_yy = birth_yy;
        this.user_since = user_since;
        this.role = role;
        this.changePasswordDue = changePasswordDue;
        this.organization = organization;
    }

    public User
    (
        String data
    ) {
        JSONObject json = new JSONObject(data);
        this.userID = json.getString("userID");
        this.name = json.getString("name");
        this.surname = json.getString("surname");
        this.city = json.getString("city");
        this.birth_dd = json.getInt("birth_dd");
        this.birth_mm = json.getInt("birth_mm");
        this.birth_yy = json.getInt("birth_yy");
        this.user_since = json.getInt("user_since");
        this.role = UserRole.valueOf(json.getString("role"));
        this.changePasswordDue = json.getBoolean("changePasswordDue");
        this.organization = json.getString("organization");
    }

    public JSONObject toJSONObject()
    {
        JSONObject json = new JSONObject();
        json.put("userID", userID);
        json.put("name", name);
        json.put("surname", surname);
        json.put("city", city);
        json.put("birth_dd", birth_dd);
        json.put("birth_mm", birth_mm);
        json.put("birth_yy", birth_yy);
        json.put("user_since", user_since);
        json.put("role", role);
        json.put("changePasswordDue", changePasswordDue);
        json.put("organization", organization);
        return json;
    }

    public String toJSONString()
    {
        return toJSONObject().toString();
    }
}