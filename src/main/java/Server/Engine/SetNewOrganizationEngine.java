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
            return new SetNewOrganizationReply(true, false, 0, null);
        }
        
        List<String> notValidTerritories = new ArrayList<>();
        List<String> validTerritories = new ArrayList<>();
        int territoriesAdded = 0;

        for(String territory : territories)
        {
            if(insertTerritory(territory))
            {
                territoriesAdded += 1;
                validTerritories.add(territory);
            } else {
                notValidTerritories.add(territory);
            }
            
        }
        
        return new SetNewOrganizationReply(
            true,
            territoriesAdded > 0,
            territoriesAdded,
            notValidTerritories
        );
    }

    private Boolean insertTerritory(String territory) 
    {
        try 
        {
            String query = "INSERT INTO territories VALUES ( ?, ? )";
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