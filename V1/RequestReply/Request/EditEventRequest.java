package RequestReply.Request;

import org.json.JSONObject;
import java.util.*;

public class EditEventRequest implements RequestType
{
    private Map<String, Object> fields = new HashMap<String, Object>();
    
    public EditEventRequest(String eventName) {
        this.fields.put("eventName", eventName);
    }

    public EditEventRequest(String eventName, Map<String, Object> fields)
    {
        this(eventName);
        this.fields.putAll(fields);
    }
    
    public EditEventRequest withEventDescription(String eventDescription) 
    {
        fields.put("eventDescription", eventDescription);
        return this;
    }
    
    public EditEventRequest withCity(String city) 
    {
        fields.put("city", city);
        return this;
    }
    
    public EditEventRequest withAddress(String address) 
    {
        fields.put("address", address);
        return this;
    }
    
    public EditEventRequest withMeetingPoint(String point) 
    {
        fields.put("meetingPoint", point);
        return this;
    }

    public EditEventRequest withStartDate(int startDate) 
    {
        fields.put("startDate", startDate);
        return this;
    }
    
    public EditEventRequest withEndDate(int endDate) 
    {
        fields.put("endDate", endDate);
        return this;
    }
    
    public EditEventRequest withOrganizationName(String organizationName) 
    {
        fields.put("organizationName", organizationName);
        return this;
    }
    
    public EditEventRequest withMinUsers(int minUsers) 
    {
        fields.put("minUsers", minUsers);
        return this;
    }
    
    public EditEventRequest withMaxUsers(int maxUsers) 
    {
        fields.put("maxUsers", maxUsers);
        return this;
    }
    
    public EditEventRequest withMaxFriends(int maxFriends) 
    {
        fields.put("maxFriends", maxFriends);
        return this;
    }
    
    public EditEventRequest withVisitType(String visitType) 
    {
        fields.put("visitType", visitType);
        return this;
    }
    
    public EditEventRequest withState(String state) 
    {
        fields.put("state", state);
        return this;
    }


    public EditEventRequest withPrice(float price) 
    {
        fields.put("price", price);
        return this;
    }
    
    public EditEventRequest witVoluntariesDisponibilityCollect(boolean x)
    {
        fields.put("voluntariesCanSubmit", x);
        return this;
    }   
    
    public EditEventRequest witUserSubscriptionCollect(boolean x)
    {
        fields.put("usersCanSubmit", x);
        return this;
    }   

    public EditEventRequest withField(String fieldName, Object value) 
    {
        fields.put(fieldName, value);
        return this;
    }
    

    public String toJSONString() 
    {
        if(!fields.containsKey("eventName"))
            return "";
        JSONObject json = new JSONObject(fields);
        return json.toString();
    }
}