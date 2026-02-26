package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.User;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetPersonalDataReply extends AuthenticatedReply 
{
    public GetPersonalDataReply(Boolean loginSuccessful, User user) 
    {
        super(loginSuccessful);
        json.put("user", user.getJSONObject());
    }

    public GetPersonalDataReply(Boolean loginSuccessful)
    {
        super(loginSuccessful);
    }
}
