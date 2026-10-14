package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.SetUserPermissionsReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class SetUserPermissionsEngine extends AuthenticatedEngine
{
    private String targetID;
    private List<String> permissions;

    private static final String INSERT_PERMISSION = """
        INSERT INTO userPermissions (userID, visitType) 
        VALUES ( ?, ? )
    """;

    public SetUserPermissionsEngine(String data) 
    {
        super(data);
        this.targetID = json.optString("targetID", "");
        this.permissions = new ArrayList<>();
        if (json.has("permissions")) 
        {
            JSONArray arr = json.getJSONArray("permissions");
            for (int i = 0; i < arr.length(); i++) 
            {
                permissions.add(arr.getString(i));
            }
        }
    }
    
    @Override
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if (!petitionerIsConfigurator()) 
        {
            return new SetUserPermissionsReply(true, false);
        }

        int updatedLines = 0;
        for (String permission : permissions) 
        {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PERMISSION)) 
            {
                statement.setString(1, targetID);
                statement.setString(2, permission);
                updatedLines += statement.executeUpdate();
            }
        }
        return new SetUserPermissionsReply(true, updatedLines > 0);
    }
}