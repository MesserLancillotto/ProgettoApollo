package Comunication.Reply;

import org.json.*;
import java.util.List;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class SetNewOrganizationReply extends AuthenticatedUpdateReply
{
    private Integer maximumFriends;

    public SetNewOrganizationReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful, updateSuccessful);
    }

    public SetNewOrganizationReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful,
        Integer maximumFriends
    ) {
        super(loginSuccessful, updateSuccessful);
        this.maximumFriends = maximumFriends;
        if (maximumFriends != null) {
            json.put("maximumFriends", maximumFriends);
            json.put("maximum_friends", maximumFriends);
        }
    }

    public Integer getMaximumFriends() {
        return maximumFriends;
    }
}