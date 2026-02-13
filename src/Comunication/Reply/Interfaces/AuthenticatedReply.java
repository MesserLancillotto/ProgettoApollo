package Comunication.Reply.Interfaces;

import org.json.JSONObject;

public abstract class AuthenticatedReply implements ReplyInterface 
{
    protected final Boolean loginSuccessful;
    protected final JSONObject json;

    public AuthenticatedReply(Boolean loginSuccessful) 
    {
        this.loginSuccessful = loginSuccessful;
        json = new JSONObject();
        json.put("loginSuccessful", loginSuccessful);
    }

    public String toJSONString() 
    {
        return json.toString();
    }
}
