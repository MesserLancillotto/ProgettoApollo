package Comunication.Reply;

public class DeletePlaceReply extends AuthenticatedUpdateReply
{
    public DeletePlaceReply
    (
        Boolean loginSuccesful,
        Boolean updateSuccesful
    ) {
        super(loginSuccesful, updateSuccesful);
    }
}