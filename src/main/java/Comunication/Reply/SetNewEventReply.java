package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class SetNewEventReply extends AuthenticatedUpdateReply
{
    public SetNewEventReply(Boolean loginSuccessful, Boolean updateSuccessful) 
    {
        super(loginSuccessful, updateSuccessful);
    }
}
