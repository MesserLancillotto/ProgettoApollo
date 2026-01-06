package Comunication.Reply;

public class DeleteVisitReply extends AuthenticatedUpdateReply
{
    public DeleteVisitReply
    (
        Boolean loginSuccesful,
        Boolean updateSuccesful
    ) {
        super(loginSuccesful, updateSuccesful);
    }
}