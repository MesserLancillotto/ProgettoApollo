package Comunication.DatabaseObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Place
{

/**
 * @param visitTypes
 * @param voluntaries devono avere la stessa lunghezza dato che al visitTypes[i] corrisponde il volontario voluntaries[i]
 */
    private static final int MAX_TERRITORIES = 1000;
    private static final int MAX_VOLUNTARIES = 1000;

    private String city;
    private String address;
    private String description;
    private String organization;
    private List<String> visitTypes;
    private List<String> voluntaries;

    public Place(
        String city,
        String address,
        String description,
        String organization,
        List<String> visitTypes,
        List<String> voluntaries
    ) {
        this.city = city;
        this.address = address;
        this.description = description;
        this.organization = organization;
        this.visitTypes = new ArrayList<>(visitTypes);
        this.voluntaries = new ArrayList<>(voluntaries);
    }

    public Place(String data) 
    {
        JSONObject json = new JSONObject(data);
        this.city = json.getString("city");
        this.address = json.getString("address");
        this.description = json.getString("description");
        this.organization = json.getString("organization");

        JSONArray visitsArray = json.getJSONArray("visitTypes");
        this.visitTypes = new ArrayList<>();
        for (int i = 0; i < visitsArray.length() && i < MAX_TERRITORIES; i++) 
        {
            this.visitTypes.add(visitsArray.getString(i));
        }
        
        JSONArray voluntariesArray = json.getJSONArray("voluntaries");
        this.voluntaries = new ArrayList<>();
        for (int i = 0; i < voluntariesArray.length() && i < MAX_VOLUNTARIES; i++) 
        {
            this.voluntaries.add(voluntariesArray.getString(i));
        }
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

    public Integer getVisitTypesLength()
    {
        return visitTypes.size();
    }

    public List<String> getVisitTypes()
    {
        return new ArrayList<>(this.visitTypes);
    }

    public Integer getVoluntariesLength()
    {
        return voluntaries.size();
    }

    public List<String> getVoluntaries()
    {
        return new ArrayList<>(this.voluntaries);
    }

    public String toJSONString() 
    {
        JSONObject json = new JSONObject();
        json.put("city", city);
        json.put("address", address);
        json.put("description", description);
        json.put("organization", organization);
        json.put("visitTypes", new JSONArray(visitTypes));
        json.put("voluntaries", new JSONArray(voluntaries));
        return json.toString(); 
    }
}