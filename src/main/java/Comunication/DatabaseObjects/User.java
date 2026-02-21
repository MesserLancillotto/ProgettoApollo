package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import java.sql.*;

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

    private List<List<Integer>> disponibilities = new ArrayList<>();
    private List<String> allowedVisits = new ArrayList<String>();
    private List<String> voluntaryEventName = new ArrayList<String>();
    private List<Integer> voluntaryEventDate = new ArrayList<Integer>();

    private JSONObject json;

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
        String organization,
        List<List<Integer>> disponibilities,
        List<String> allowedVisits,
        List<String> voluntaryEventName,
        List<Integer> voluntaryEventDate
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
        this.disponibilities = new ArrayList<>(disponibilities);
        this.allowedVisits = new ArrayList<>(allowedVisits);
        this.voluntaryEventName = new ArrayList<>(voluntaryEventName);
        this.voluntaryEventDate = new ArrayList<>(voluntaryEventDate);
    }

    public User
    (
        String data
    ) {
        this.json = new JSONObject(data);

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
        if(this.json != null)
        {
            return this.json;
        }
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

        JSONArray disponibilitiesJSON = new JSONArray();
        for(List<Integer> interval : disponibilities)
        {
            JSONArray inner = new JSONArray();
            inner.put(interval.get(0));
            inner.put(interval.get(1));
            disponibilitiesJSON.put(inner);
        }
        json.put("disponibilities", disponibilitiesJSON);

        JSONArray allowedVisitsJSON = new JSONArray();
        for(String name : allowedVisits)
        {
            allowedVisitsJSON.put(name);
        }
        json.put("allowedVisits", allowedVisitsJSON);

        JSONArray voluntaryEventNameJSON = new JSONArray();
        for(String name : voluntaryEventName)
        {
            voluntaryEventNameJSON.put(name);
        }
        json.put("voluntaryEventName", voluntaryEventNameJSON);

        JSONArray voluntaryEventDateJSON = new JSONArray();
        for(Integer date : voluntaryEventDate)
        {
            voluntaryEventDateJSON.put(date);
        }
        json.put("voluntaryEventDate", voluntaryEventDateJSON);

        return json;
    }

    public String toJSONString()
    {
        return toJSONObject().toString();
    }
}