package RequestReply.Request;

import org.json.*;

public class DeletePlaceRequest implements RequestType 
{
    private String city;
    private String address;

    public DeletePlaceRequest
    (
        String city,
        String address
    ) {
        this.city = city;
        this.address = address;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("city", city);
        json.put("address", address);
        return json.toString();
    }
}