package Comunication.DatabaseObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Place
{
    private String city;
    private String address;
    private String description;
    private String organization;
    private String visitType;
    private String defaultVoluntary;

    private JSONObject json;

    public Place(
        String city,
        String address,
        String description,
        String organization,
        String visitType,
        String defaultVoluntary
    ) {
        this.city = city;
        this.address = address;
        this.description = description;
        this.organization = organization;
        this.visitType = visitType;
        this.defaultVoluntary = defaultVoluntary;
    }

    public Place(JSONObject data)
    {
        this(
            data.getString("city"),
            data.getString("address"),
            data.getString("description"),
            data.getString("organization"),
            data.getString("visitType"),
            data.getString("defaultVoluntary")
        );
    }

    public JSONObject getJSONObject()
    {
        if(json != null) 
        {
            return json;
        }
        json = new JSONObject();
        json.put("city", city);
        json.put("address", address);
        json.put("description", description);
        json.put("organization", organization);
        json.put("visitType", visitType);
        json.put("defaultVoluntary", defaultVoluntary);
        return json;
    }

    public String toJSONString() 
    {
        return getJSONObject().toString(); 
    }

    public String getCity()
    {
        return this.city;
    }

    public String getAddress()
    {
        return this.address;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getOrganization()
    {
        return this.organization;
    }

    public String getVisitType()
    {
        return this.visitType;
    }
    
    public String getDefaultVoluntary()
    {
        return this.defaultVoluntary;
    }
}