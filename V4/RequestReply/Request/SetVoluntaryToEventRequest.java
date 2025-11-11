package RequestReply.Request;

import org.json.JSONObject;
import java.util.*;

public class SetVoluntaryToEventRequest implements RequestType
{

    private String event;
    private String targetID;
    private int time;

    public SetVoluntaryToEventRequest(
        String event,
        String targetID,
        int time
    ) {
        this.event = event;
        this.targetID = targetID;
        this.time = time;
    }
    
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("event", event);
        json.put("targetID", targetID);
        json.put("time", time);
        return json.toString();
    }
}