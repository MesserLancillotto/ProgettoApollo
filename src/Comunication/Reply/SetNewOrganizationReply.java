package Comunication.Reply;

import org.json.*;
import java.util.List;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class SetNewOrganizationReply extends AuthenticatedUpdateReply
{
    private Boolean signUpSuccessful;
    private Integer territoriesAdded;
    private List<String> notValidTerritories;

    public SetNewOrganizationReply
    (
        Boolean loginSuccessful,
        Boolean signUpSuccessful,
        Integer territoriesAdded,
        List<String> notValidTerritories
    ) {
        super(loginSuccessful, territoriesAdded > 0);
        this.signUpSuccessful = signUpSuccessful;
        this.territoriesAdded = territoriesAdded;
        this.notValidTerritories = notValidTerritories;
    }

    public String toJSONString()
    {
        json.put("signUpSuccessful", signUpSuccessful);
        json.put("territoriesAdded", territoriesAdded);
        json.put("notValidTerritories", new JSONArray(notValidTerritories));
        return json.toString();
    }
}