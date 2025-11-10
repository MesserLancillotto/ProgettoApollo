package RequestReply.Reply;

import org.json.*;

public class SetClosedDaysReply implements ReplyType
{
    private boolean loginSuccessful;
    private boolean querySuccesful;

    public SetClosedDaysReply
    (
        boolean loginSuccessful,
        boolean querySuccesful
    ) {
        this.loginSuccessful = loginSuccessful;
        this.querySuccesful = querySuccesful;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("loginSuccessful", loginSuccessful);
        json.put("querySuccesful", querySuccesful);
        return json.toString();
    }
}
