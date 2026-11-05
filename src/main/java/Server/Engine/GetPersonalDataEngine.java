package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetPersonalDataReply;
import Comunication.DatabaseObjects.User;
import Comunication.DatabaseObjects.UserRole;
import Comunication.DatabaseObjects.Place;
import Server.Engine.Helper.*;

public class GetPersonalDataEngine extends AuthenticatedEngine
{
    public GetPersonalDataEngine(String data) 
    {
        super(data);
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {        
        User user = UserCreator.createUserFromID(connection, getUserID());   
        JSONObject userJson = user.getJSONObject();
        
        // Recupero delle visite autorizzate dalla tabella userPermissions
        JSONArray allowedVisitsArray = new JSONArray();
        String permQuery = "SELECT visitType FROM userPermissions WHERE userID = ?";
        try (PreparedStatement stmtPerm = connection.prepareStatement(permQuery)) {
            stmtPerm.setString(1, getUserID());
            try (ResultSet rsPerm = stmtPerm.executeQuery()) {
                while (rsPerm.next()) {
                    allowedVisitsArray.put(rsPerm.getString("visitType"));
                }
            }
        }
        userJson.put("allowedVisits", allowedVisitsArray);
        
        String roleStr = userJson.optString("role");
        
        if (UserRole.VOLUNTARY.name().equals(roleStr)) {
            List<Place> assignedPlaces = new ArrayList<>();
            String placeQuery = "SELECT city, address, description, organization, visitType, userID FROM places WHERE userID = ?";
            
            try (PreparedStatement stmtPlace = connection.prepareStatement(placeQuery)) {
                stmtPlace.setString(1, getUserID());
                try (ResultSet rsPlace = stmtPlace.executeQuery()) {
                    while (rsPlace.next()) {
                        Place place = new Place(
                            rsPlace.getString("city"),
                            rsPlace.getString("address"),
                            rsPlace.getString("description"),
                            rsPlace.getString("organization"),
                            rsPlace.getString("visitType"),
                            rsPlace.getString("userID")
                        );
                        assignedPlaces.add(place);
                    }
                }
            }
            
            return new GetPersonalDataReply(true, user, assignedPlaces);
        }
        
        return new GetPersonalDataReply(true, user);
    } 
}