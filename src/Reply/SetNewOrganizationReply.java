package Comunication.Reply;

import org.json.*;
import java.util.List;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class SetNewOrganizationReply extends AuthenticatedReply
{
    private Boolean signUpSuccesful;
    private Integer territoriesAdded;
    private List<String> notValidTerritories;

    public SetNewOrganizationReply
    (
        Boolean loginSuccesful,
        Boolean signUpSuccesful,
        Integer territoriesAdded,
        List<String> notValidTerritories
    ) {
        super(loginSuccesful);
        this.signUpSuccesful = signUpSuccesful;
        this.territoriesAdded = territoriesAdded;
        this.notValidTerritories = notValidTerritories;
    }

    public String toJSONString()
    {
        json.put("signUpSuccesful", signUpSuccesful);
        json.put("territoriesAdded", territoriesAdded);
        json.put("notValidTerritories", new JSONArray(notValidTerritories));
        return json.toString();
    }
}