package Comunication.Reply.Interfaces;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public abstract class AuthenticatedUpdateReply extends AuthenticatedReply 
{
    protected final Boolean updateSuccessful;

    public AuthenticatedUpdateReply(
        Boolean loginSuccessful, 
        Boolean updateSuccessful
    ) {
        super(loginSuccessful);
        this.updateSuccessful = updateSuccessful;
    }

    @Override
    public String toJSONString() 
    {
        json.put("updateSuccessful", updateSuccessful);
        return json.toString();
    }
}
