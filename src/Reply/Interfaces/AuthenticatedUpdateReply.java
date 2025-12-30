package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public class AuthenticatedUpdateReply extends AuthenticatedReply
{
    private Boolean updateSuccesful;

    public AuthenticatedUpdateReply
    (
        Boolean loginSuccesful,
        Boolean updateSuccesful
    ) {
        super(loginSuccesful);
        this.updateSuccesful = updateSuccesful;
    }

    public String toJSONString()
    {
        json.put("updateSuccesful", updateSuccesful);
        return json.toString();
    }
}