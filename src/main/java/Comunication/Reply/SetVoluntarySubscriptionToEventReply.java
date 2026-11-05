package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class SetVoluntarySubscriptionToEventReply extends AuthenticatedUpdateReply
{
    public SetVoluntarySubscriptionToEventReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful, updateSuccessful);
    }
}