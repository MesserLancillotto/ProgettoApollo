package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetAllowedVisitTypesReply extends AuthenticatedReply 
{
    private static final int MAX_VISIT_TYPES = 100;
    private List<String> visitTypes;

    public GetAllowedVisitTypesReply(
        Boolean loginSuccessful, 
        List<String> visitTypes
    )  {
        super(loginSuccessful);
        this.visitTypes = new ArrayList<String>(visitTypes);
    }

    @Override
    public String toJSONString() 
    {
        json.put("loginSuccessful", loginSuccessful);
        JSONArray array = new JSONArray();

        for (int i = 0; i < visitTypes.size() && i < MAX_VISIT_TYPES; i++) 
        { 
            array.put(visitTypes.get(i));
        }
        
        json.put("visitTypes", array);
        return json.toString();
    }
}
