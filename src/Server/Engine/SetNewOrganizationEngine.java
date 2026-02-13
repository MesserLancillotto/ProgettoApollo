package Server.Engine;

import org.json.*;
import java.util.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.SetNewOrganizationReply;

public class SetNewOrganizationEngine extends AuthenticatedEngine
{
    private List<String> territories;
    
    public SetNewOrganizationEngine(String data) {
        super(data);
        territories = new ArrayList<>();
        JSONArray territoriesArray = json.getJSONArray("territories");
        for(int i = 0; i < territoriesArray.length(); i++)
        {
            territories.add(territoriesArray.getString(i));
        }
    }
    
    public ReplyInterface handleRequest()
    {
        if (!connectDB()) 
        {
            return new SetNewOrganizationReply(false, false, 0, null);
        }
        
        try 
        {
            if (!petitionerCanLogIn()) 
            {
                return new SetNewOrganizationReply(false, false, 0, null);
            }
            
            if (!petitionerIsConfigurator()) 
            {
                return new SetNewOrganizationReply(true, false, 0, null);
            }
            
            List<String> notValidTerritories = new ArrayList<>();
            List<String> validTerritories = new ArrayList<>();
            int territoriesAdded = 0;
            
            for (String territory : territories) 
            {
                if (isValidTerritory(territory)) 
                {
                    if (insertTerritory(territory)) 
                    {
                        territoriesAdded++;
                        validTerritories.add(territory);
                    } else {
                        notValidTerritories.add(territory);
                    }
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
            
        } catch (Exception e) {
            e.printStackTrace();
            return new SetNewOrganizationReply(true, false, 0, null);
        }
    }
    
    private boolean isValidTerritory(String territory) 
    {
        try 
        {
            String query = "SELECT COUNT(*) FROM territories WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, territory);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean insertTerritory(String territory) 
    {
        try 
        {
            String query = "INSERT INTO organizationTerritories VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, organization);
            statement.setString(2, territory);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}