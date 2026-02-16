package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public class NegativeAuthenticatedReply extends AuthenticatedReply
{
    public NegativeAuthenticatedReply() 
    {
        super(false);
    }
}