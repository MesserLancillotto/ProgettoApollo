package Comunication.Reply;

public class SetNewPasswordReply extends AuthenticatedUpdateReply
{
    public SetNewPasswordReply
    (
        Boolean loginSuccesful,
        Boolean updateSuccesful
    ) {
        super(loginSuccesful, updateSuccesful);
    }
}