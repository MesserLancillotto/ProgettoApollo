package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import java.sql.*;

import Comunication.DatabaseObjects.*;


public class User 
{
    private JSONObject json;

    public User()
    {
        json = new JSONObject();
        json.put("userID", "NONE");
    }

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
        List<String> allowedVisits,
        List<List<Integer>> disponibilities,
        List<Event> voluntaryEvents
    ) {
        json = new JSONObject();
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

        JSONArray voluntaryEventsJSON = new JSONArray();
        for(Event event : voluntaryEvents)
        {
            voluntaryEventsJSON.put(event.getJSONObject());
        }
        json.put("allowedVisits", voluntaryEventsJSON);
    }

    public JSONObject getJSONObject()
    {
        if(this.json != null)
        {
            return this.json;
        }
        json = new JSONObject();
        return json;
    }

    public String toJSONString()
    {
        return getJSONObject().toString();
    }
}