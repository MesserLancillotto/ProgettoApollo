package RequestReply.Request;

import org.json.*;
import java.util.*;

public class SetUserToEventRequest implements RequestType 
{
    private ArrayList<String> friends;
    private String event;
    private int time;

    public SetUserToEventRequest
    (
        ArrayList<String> friends,
        String event,
        int time
    ) {
        this.friends = friends;
        this.event = event;
        this.time = time;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("friends", friends);
        json.put("event", event);
        json.put("time", time);
        return json.toString();
    }
}