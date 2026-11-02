package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class DeleteEventInstanceReply extends AuthenticatedUpdateReply
{
    public DeleteEventInstanceReply(Boolean loginSuccessful, Boolean updateSuccessful) 
    {
        super(loginSuccessful, updateSuccessful);
    }
}
