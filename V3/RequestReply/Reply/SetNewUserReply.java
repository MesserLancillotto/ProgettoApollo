package RequestReply.Reply;

import org.json.*;

public class SetNewUserReply implements ReplyType
{
    private boolean accessSuccesful;
    private String userID;

    public SetNewUserReply
    (
        boolean accessSuccesful,
        String userID
    ) { 
        this.accessSuccesful = accessSuccesful;;
        this.userID = userID;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("accessSuccesful", accessSuccesful);
        json.put("userID", userID);
        return json.toString();
    }
}