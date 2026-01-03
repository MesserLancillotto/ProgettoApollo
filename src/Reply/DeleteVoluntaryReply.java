package Comunication.Reply;

public class DeleteVoluntaryReply extends AuthenticatedUpdateReply
{
    public DeleteVoluntaryReply
    (
        Boolean loginSuccesful,
        Boolean updateSuccesful
    ) {
        super(loginSuccesful, updateSuccesful);
    }
}