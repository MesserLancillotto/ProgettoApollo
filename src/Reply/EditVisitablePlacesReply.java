package Comunication.Reply;

import org.json.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public class EditVisitablePlacesReply extends AuthenticatedReply
{
    private Boolean updateSuccesful;

    public EditVisitablePlacesReply
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