package RequestReply.Reply;

import org.json.*;

public class SetUserToEventReply implements ReplyType
{
    private boolean accessSuccesful;
    private boolean querySuccesful;

    public SetUserToEventReply
    (
        boolean accessSuccesful,
        boolean querySuccesful
    ) { 
        this.accessSuccesful = accessSuccesful;
        this.querySuccesful = querySuccesful;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("accessSuccesful", accessSuccesful);
        json.put("querySuccesful", querySuccesful);
        return json.toString();
    }
}