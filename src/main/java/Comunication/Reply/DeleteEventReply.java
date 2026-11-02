package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class DeleteEventReply extends AuthenticatedUpdateReply
{
    public DeleteEventReply(Boolean loginSuccessful, Boolean updateSuccessful) 
    {
        super(loginSuccessful, updateSuccessful);
    }
}
