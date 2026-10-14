package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetVoluntaryPermissionsRequest extends AuthenticatedRequest
{
    private String targetID;
    private List<String> permissions;

    public SetVoluntaryPermissionsRequest
    (
        String userID,
        String password,
        String targetID,
        List<String> permissions
    ) {
        super(ComunicationType.SET_VOLUNTARY_PERMISSIONS, userID, password);
        this.targetID = targetID;
        this.permissions = permissions != null ? new ArrayList<>(permissions) : new ArrayList<>();
        json.put("targetID", targetID);
        json.put("permissions", new JSONArray(this.permissions));
    }

    public SetVoluntaryPermissionsRequest
    (
        String userID,
        String password
    ) {
        this(userID, password, "", new ArrayList<>());
    }

    public void addPermission(String permission) 
    {
        this.permissions.add(permission);
        json.put("permissions", new JSONArray(this.permissions));
    }

    public String getTargetID() 
    {
        return this.targetID;
    }

    public List<String> getPermissions() 
    {
        return Collections.unmodifiableList(this.permissions);
    }
}