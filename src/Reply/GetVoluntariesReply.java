package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.User;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetVoluntariesReply extends AuthenticatedReply
{
    private static final int MAX_VOLUNTARIES = 1000;
    private List<User> voluntaries;

    public GetVoluntariesReply
    (
        Boolean loginSuccesful,
        List<User> voluntaries
    ) {
        super(loginSuccesful);
        this.voluntaries = new ArrayList<>(voluntaries);
    }

    public String toJSONString()
    {
        JSONArray usersJSONArray = new JSONArray();
        for(int i = 0; i < voluntaries.size() && i < MAX_VOLUNTARIES; i++)
        {
            User user = voluntaries.get(i);
            usersJSONArray.put(user.toJSONString());
        }
        json.put("voluntaries", usersJSONArray);
        
        return json.toString();
    }
}