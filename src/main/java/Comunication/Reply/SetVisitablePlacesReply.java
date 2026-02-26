package Comunication.Reply;

import org.json.*;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class SetVisitablePlacesReply extends AuthenticatedUpdateReply
{
    private Boolean updateSuccessful;

    public SetVisitablePlacesReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful, updateSuccessful);
    }
}