package RequestReply.Reply;

import org.json.*;

public class SetVoluntaryToEventReply implements ReplyType 
{
    private boolean accessSuccesful;
    private boolean querySuccessful;

    public SetVoluntaryToEventReply
    (
        boolean accessSuccesful,
        boolean querySuccessful
    ) {
        this.accessSuccesful = accessSuccesful;
        this.querySuccessful = querySuccessful;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("accessSuccesful", accessSuccesful);
        json.put("querySuccessful", querySuccessful);
        return json.toString();
    }
}