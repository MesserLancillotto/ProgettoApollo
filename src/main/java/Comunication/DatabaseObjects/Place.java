package Comunication.DatabaseObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Place
{
    private JSONObject json;

    public Place(
        String city,
        String address,
        String description,
        String organization,
        String visitType,
        String defaultVoluntary
    ) {
        json = new JSONObject();
        json.put("city", city);
        json.put("address", address);
        json.put("visitType", visitType);
        json.put("description", description);
        json.put("organization", organization);
        json.put("defaultVoluntary", defaultVoluntary);
    }

    public Place(JSONObject data)
    {
        this(
            data.getString("city"),
            data.getString("address"),
            data.getString("visitType"),
            data.getString("description"),
            data.getString("organization"),
            data.getString("defaultVoluntary")
        );
    }
    
    public Place()
    {
        this(
            "NONE",
            "NONE",
            "NONE",
            "NONE",
            "NONE",
            "NONE"
        );
    }

    public JSONObject getJSONObject()
    {
        return json;
    }

    public String toJSONString() 
    {
        return getJSONObject().toString(); 
    }

    public String getCity()
    {
        return json.getString("city");
    }

    public String getAddress()
    {
        return json.getString("address");
    }

    public String getDescription()
    {
        return json.getString("description");
    }

    public String getOrganization()
    {
        return json.getString("organization");
    }

    public String getVisitType()
    {
        return json.getString("visitType");
    }
    
    public String getDefaultVoluntary()
    {
        return json.getString("defaultVoluntary");
    }
}