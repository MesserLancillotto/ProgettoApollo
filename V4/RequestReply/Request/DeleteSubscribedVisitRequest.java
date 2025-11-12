package RequestReply.Request;

import org.json.*;

public class DeleteSubscribedVisitRequest implements RequestType 
{
    private String event;
    private int time;

    public DeleteSubscribedVisitRequest
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