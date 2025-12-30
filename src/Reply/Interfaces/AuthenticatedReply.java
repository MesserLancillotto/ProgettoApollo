package Comunication.Reply.Interfaces;

import org.json.*;

public class AuthenticatedReply implements ReplyInterface
{
    private Boolean loginSuccesful;
    public JSONObject json;

    public AuthenticatedReply
    (
        Boolean loginSuccesful
    ) {
        this.loginSuccesful = loginSuccesful;
        json = new JSONObject();
        json.put("loginSuccesful", loginSuccesful);
    }

    public String toJSONString()
    {
        return json.toString();
    }
}