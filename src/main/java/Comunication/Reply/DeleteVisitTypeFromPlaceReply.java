package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class DeleteVisitTypeFromPlaceReply extends AuthenticatedUpdateReply
{
    public DeleteVisitTypeFromPlaceReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful, updateSuccessful);
    }
}