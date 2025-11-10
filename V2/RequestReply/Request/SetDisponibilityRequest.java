package RequestReply.Request;

import org.json.*;
import RequestReply.UserRoleTitle.*;

public class SetDisponibilityRequest implements RequestType
{
    private String event;
    private int time;

    public SetDisponibilityRequest
    (
        String event,
        int time
    ) {
        this.event = event;
        this.time = time;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("event", event);
        json.put("time", time);
        return json.toString(); 
    }
}