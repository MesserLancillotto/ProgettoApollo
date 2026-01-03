package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.DatabaseObjects.Place;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.DatabaseObjects.Event;

public class GetAllowedVisitTypesReply extends AuthenticatedReply
{
    private static final int MAX_VISIT_TYPES = 100;
    private List<String> visitTypes;

    public GetAllowedVisitTypesReply(Boolean loginSuccesful, List<String> visitTypes)
    {
        super(loginSuccesful);
        this.visitTypes = new ArrayList<String>(visitTypes);
    }

    public String toJSONString()
    {
        json.put("loginSuccesful", loginSuccesful);
        JSONArray array = new JSONArray();
        
        for(int i = 0; i < events.size() && i < MAX_VISIT_TYPES; i++)
        {
            array.put(new JSONObject(evenvisitTypests.get(i).toJSONString()));
        }
        json.put("visitTypes", array);
        return json.toString();
    }
}