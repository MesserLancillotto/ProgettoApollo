package Comunication.Reply;

import org.json.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public class SetVisitablePlacesReply extends AuthenticatedReply
{
    private Boolean updateSuccesful;

    public SetVisitablePlacesReply
    (
        Boolean loginSuccesful,
        Boolean updateSuccesful
    ) {
        super(loginSuccesful);
        this.updateSuccesful = updateSuccesful;
    }

    public String toJSONString()
    {
        json.put("updateSuccesful", updateSuccesful);
        return json.toString();
    }
}