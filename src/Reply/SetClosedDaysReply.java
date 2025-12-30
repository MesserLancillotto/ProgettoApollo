package Comunication.Reply;

public class SetClosedDaysReply extends AuthenticatedUpdateReply
{
    public SetClosedDaysReply
    (
        Boolean loginSuccesful,
        Boolean updateSuccesful
    ) {
        super(loginSuccesful, updateSuccesful);
    }
}