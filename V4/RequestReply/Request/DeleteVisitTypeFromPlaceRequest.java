package RequestReply.Request;

import org.json.*;

public class DeleteVisitTypeFromPlaceRequest implements RequestType
{
    private String city;
    private String address;
    private String visitType;

    public DeleteVisitTypeFromPlaceRequest
    (
        String city,
        String address,
        String visitType
    ) {
        this.city = city;
        this.address = address;
        this.visitType = visitType;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("city", city);
        json.put("address", address);
        json.put("visitType", visitType);
        return json.toString();
    }
}
