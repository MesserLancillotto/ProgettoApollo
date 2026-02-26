package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetAllowedVisitTypesReply extends AuthenticatedReply 
{
    public GetAllowedVisitTypesReply(
        Boolean loginSuccessful, 
        List<String> visitTypes
    )  {
        super(loginSuccessful);
        JSONArray visitTypesJSON = new JSONArray();
        for (String visitType : visitTypes) 
        { 
            visitTypesJSON.put(visitType);
        }
        json.put("visitTypes", visitTypesJSON);
    }
}
