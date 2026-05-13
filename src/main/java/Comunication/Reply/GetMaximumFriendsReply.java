package Comunication.Reply;

import org.json.JSONObject;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetMaximumFriendsReply extends AuthenticatedReply 
{ 
    public GetMaximumFriendsReply(
        Boolean querySuccesful, 
        Integer friendsNumber
    ) {
        super(true);
        this.json.put("querySuccesful", querySuccesful);
        this.json.put("friendsNumber", friendsNumber);
    }

    public GetMaximumFriendsReply() 
    {
        super(true);
        this.json.put("querySuccesful", false);
    }
}