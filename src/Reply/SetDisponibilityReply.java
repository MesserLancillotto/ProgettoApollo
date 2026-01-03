package Comunication.Reply;

public class SetDisponibilityReply extends AuthenticatedUpdateReply
{
    public SetDisponibilityReply
    (
        Boolean loginSuccesful,
        Boolean updateSuccesful
    ) {
        super(loginSuccesful, updateSuccesful);
    }
}