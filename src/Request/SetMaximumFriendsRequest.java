package Comunication.Request;

// external
import org.json.*;
// custom
import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetMaximumFriendsRequest extends AuthenticatedRequest
{
    private Integer maximum_friends;

    public SetMaximumFriendsRequest
    (
        String userID,
        String password,
        Integer maximum_friends
    ) {
        super(ComunicationType.SET_MAXIMUM_FRIENDS, userID, password);
        this.maximum_friends = maximum_friends;
    }

    public String toJSONString()
    {
        json.put("maximum_friends", maximum_friends);
        return json.toString();
    }
}