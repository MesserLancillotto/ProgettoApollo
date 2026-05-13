package Comunication.Reply;

import Comunication.Reply.Interfaces.AuthenticatedReply;
import org.json.JSONObject;

public class SetNewUserReply extends AuthenticatedReply
{
    private String userID;

    public SetNewUserReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful,
        String userID
    ) {
        super(loginSuccessful);
        json.put("updateSuccessful", updateSuccessful);
        json.put("userID", userID);
        this.userID = userID;
    }
}