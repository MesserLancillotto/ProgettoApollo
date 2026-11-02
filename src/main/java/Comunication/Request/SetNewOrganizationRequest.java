package Comunication.Request;

import org.json.*;
import java.util.List;
import java.util.ArrayList;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetNewOrganizationRequest extends AuthenticatedRequest
{
    private String organization;
    private ArrayList<String> territories; 
    private Integer maximumFriends;

    public SetNewOrganizationRequest
    (
        String userID, 
        String password,
        String organization,
        List<String> territories,
        Integer maximumFriends
    ) {
        super(ComunicationType.SET_NEW_ORGANIZATION, userID, password);
        this.organization = organization;
        this.territories = territories != null ? new ArrayList<>(territories) : new ArrayList<>();
        this.maximumFriends = maximumFriends;
        json.put("organization", organization);
        json.put("territories", new JSONArray(this.territories));
        json.put("maximumFriends", maximumFriends);
        json.put("maximum_friends", maximumFriends);
    }

    public SetNewOrganizationRequest
    (
        String userID, 
        String password,
        String organization,
        List<String> territories
    ) {
        this(userID, password, organization, territories, 0);
    }

    public Integer getMaximumFriends() {
        return maximumFriends;
    }
}