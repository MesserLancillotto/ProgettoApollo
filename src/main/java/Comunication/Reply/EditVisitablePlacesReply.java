package Comunication.Reply;

import org.json.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public class EditVisitablePlacesReply extends AuthenticatedReply
{
    private Boolean updateSuccessful;

    public EditVisitablePlacesReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful);
        this.updateSuccessful = updateSuccessful;
    }

    @Override
    public String toJSONString()
    {
        json.put("updateSuccessful", updateSuccessful);
        return json.toString();
    }
}