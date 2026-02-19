package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.User;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetPersonalDataReply extends AuthenticatedReply 
{
    private User user;

    public GetPersonalDataReply(Boolean loginSuccessful, User user) 
    {
        super(loginSuccessful);
        this.user = user;
        json.put("user", user.toJSONObject());
    }

    public GetPersonalDataReply(Boolean loginSuccessful)
    {
        super(loginSuccessful);
    }

    @Override
    public String toJSONString() 
    {
        return json.toString();
    }
}
