package RequestReply.Request;

import org.json.*;
import RequestReply.UserRoleTitle.*;

public class SetClosedDaysRequest implements RequestType
{
    private int startDate;
    private int endDate;
    private String organization;

    public SetClosedDaysRequest
    (
        int startDate,
        int endDate,
        String organization
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.organization = organization;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("startDate", startDate);
        json.put("endDate", endDate);
        json.put("organization", organization);
        return json.toString();
    }
}
