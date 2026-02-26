package Comunication.Reply;

import org.json.*;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public class EditVisitablePlacesReply extends AuthenticatedReply
{
    public EditVisitablePlacesReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful);
        json.put("updateSuccessful", updateSuccessful);
    }
}