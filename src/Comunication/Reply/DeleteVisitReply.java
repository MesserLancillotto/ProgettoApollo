package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class DeleteVisitReply extends AuthenticatedUpdateReply
{
    public DeleteVisitReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful, updateSuccessful);
    }
}