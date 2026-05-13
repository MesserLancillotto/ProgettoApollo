package Comunication.Reply;

import java.util.*;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetPossibleVisitsReply extends AuthenticatedReply
{    
    private JSONObject json;

    public GetPossibleVisitsReply
    (
        Boolean querySuccesful,
        List visitTypes
    ) {
        super(true);
        json.put("querySuccesful", querySuccesful);
        json.put("visitTypes", visitTypes);
    }

    public GetPossibleVisitsReply() 
    {
        super(true);
        json.put("querySuccesful", false);
    }
}