package Comunication.Reply;

public class SetMaximumFriendsReply extends AuthenticatedUpdateReply
{
    public SetMaximumFriendsReply
    (
        Boolean loginSuccesful,
        Boolean updateSuccesful
    ) {
        super(loginSuccesful, updateSuccesful);
    }
}