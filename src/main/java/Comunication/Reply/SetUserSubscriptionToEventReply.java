package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class SetUserSubscriptionToEventReply extends AuthenticatedUpdateReply
{
    public SetUserSubscriptionToEventReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful, updateSuccessful);
    }
}