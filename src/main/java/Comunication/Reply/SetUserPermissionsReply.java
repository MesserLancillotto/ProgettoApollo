package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class SetUserPermissionsReply extends AuthenticatedUpdateReply
{
    public SetUserPermissionsReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful, updateSuccessful);
    }
}
