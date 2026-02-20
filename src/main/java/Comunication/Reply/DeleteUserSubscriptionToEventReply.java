package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class DeleteUserSubscriptionToEventReply extends AuthenticatedUpdateReply
{
    public DeleteUserSubscriptionToEventReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful, updateSuccessful);
    }
}