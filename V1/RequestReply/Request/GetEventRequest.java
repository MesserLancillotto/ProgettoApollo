package RequestReply.Request;

import java.util.*;
import org.json.*;

public class GetEventRequest implements RequestType
{
    private Map<String, Object> filters = new HashMap<String, Object>();
    
    public GetEventRequest(Map<String, Object> filters) {
        this.filters = filters;
    }
    
    public GetEventRequest() {
        this.filters = new HashMap<>();
    }
    
    private GetEventRequest withCity(String city) {
        filters.put("city", city);
        return this;
    }
    
    private GetEventRequest withOrganization(String organization) {
        filters.put("organization", organization);
        return this;
    }
    
    private GetEventRequest withVisitType(String visitType) {
        filters.put("visitType", visitType);
        return this;
    }
    
    private GetEventRequest withConfirmed(boolean confirmed) {
        filters.put("confirmed", confirmed);
        return this;
    }
    
    private GetEventRequest withEventName(String eventName) {
        filters.put("eventName", eventName);
        return this;
    }
    

    private GetEventRequest withPartialName(String partialName) {
        filters.put("partialName", partialName);
        return this;
    }

    private GetEventRequest withAddress(String address) {
        filters.put("address", address);
        return this;
    }
    
    private GetEventRequest withStartDate(int startDate) {
        filters.put("startDate", startDate);
        return this;
    }
    
    private GetEventRequest withEndDate(int endDate) {
        filters.put("endDate", endDate);
        return this;
    }
    
    private GetEventRequest withOrganizationName(String organizationName) {
        filters.put("organizationName", organizationName);
        return this;
    }
    
    private GetEventRequest withMinUsers(int minUsers) {
        filters.put("minUsers", minUsers);
        return this;
    }
    
    private GetEventRequest withMaxUsers(int maxUsers) {
        filters.put("maxUsers", maxUsers);
        return this;
    }
    
    private GetEventRequest withMaxFriends(int maxFriends) {
        filters.put("maxFriends", maxFriends);
        return this;
    }
    
    private GetEventRequest withState(String state) {
        filters.put("state", state);
        return this;
    }

    private GetEventRequest withPrice(float price, boolean greater)
    {
        filters.put("price", price);
        filters.put("greaterThan", greater);
        return this;
    }

    public String toJSONString() {
        JSONObject json = new JSONObject(filters);
        return json.toString();
    }
}