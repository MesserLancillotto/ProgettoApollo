package Server.Engine;

import org.json.*;
import java.util.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.SetNewOrganizationReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class SetNewOrganizationEngine extends AuthenticatedEngine
{
    private List<String> territories;

    private static final int MAX_TERRITORIES = 100;
    
    public SetNewOrganizationEngine(String data) {
        super(data);
        territories = new ArrayList<>();
        JSONArray territoriesArray = json.getJSONArray("territories");
        for(int i = 0; i < territoriesArray.length(); i++)
        {
            territories.add(territoriesArray.getString(i));
        }
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if (!petitionerIsConfigurator()) 
        {
            return new SetNewOrganizationReply(true, false);
        }

        String currentOrganization = getOrganization();
        if (currentOrganization != null && !currentOrganization.equals("")) 
        {
            return new SetNewOrganizationReply(true, false);
        }

        String newOrganization = json.optString("organization", "");
        if (newOrganization.isEmpty()) 
        {
            return new SetNewOrganizationReply(true, false);
        }

        String updateConfiguratorQuery = "UPDATE users SET organization = ? WHERE userID = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateConfiguratorQuery);
        updateStatement.setString(1, newOrganization);
        updateStatement.setString(2, getUserID());
        int rowsUpdated = updateStatement.executeUpdate();

        if (rowsUpdated != 1) 
        {
            return new SetNewOrganizationReply(true, false);
        }

        int maximumFriends = json.optInt("maximum_friends", json.optInt("maximumFriends", 0));
        String mergeOrganizationQuery = "MERGE INTO organizations (organization, maximum_friends) KEY(organization) VALUES ( ?, ? )";
        try (PreparedStatement orgStatement = connection.prepareStatement(mergeOrganizationQuery)) 
        {
            orgStatement.setString(1, newOrganization);
            orgStatement.setInt(2, maximumFriends);
            orgStatement.executeUpdate();
        }

        List<String> notValidTerritories = new ArrayList<>();
        List<String> validTerritories = new ArrayList<>();
        int territoriesAdded = 0;

        for(String territory : territories)
        {
            if(insertTerritory(newOrganization, territory))
            {
                territoriesAdded += 1;
                validTerritories.add(territory);
            } else {
                notValidTerritories.add(territory);
            }
        }
        
        return new SetNewOrganizationReply(
            true,
            true,
            maximumFriends
        );
    }

    private Boolean insertTerritory(String organization, String territory) 
    {
        try 
        {
            String query = "MERGE INTO territories VALUES ( ?, ? )";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, organization);
            statement.setString(2, territory);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Errore in insertTerritory()");
            e.printStackTrace();
            return false;
        }
    }
}