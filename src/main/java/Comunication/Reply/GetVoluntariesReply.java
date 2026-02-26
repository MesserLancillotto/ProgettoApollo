package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.User;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetVoluntariesReply extends AuthenticatedReply 
{
    public GetVoluntariesReply(Boolean loginSuccessful, List<User> voluntaries) 
    {
        super(loginSuccessful);
        JSONArray usersJSONArray = new JSONArray();
        for (User voluntary : voluntaries) 
        {
            usersJSONArray.put(voluntary.getJSONObject());
        }
        json.put("voluntaries", usersJSONArray);
    }
}
