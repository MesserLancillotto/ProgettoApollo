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
        User user = UserCreator.createUserFromID(connection, getUserID());[cite: 27]   
        
        String roleStr = user.getJSONObject().optString("role");
        
        if (UserRole.VOLUNTARY.name().equals(roleStr)) {
            List<Place> assignedPlaces = new ArrayList<>();
            String query = "SELECT city, address, description, organization, visitType, defaultVoluntary FROM places WHERE defaultVoluntary = ?";
            
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, getUserID());
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Place place = new Place(
                            rs.getString("city"),
                            rs.getString("address"),
                            rs.getString("description"),
                            rs.getString("organization"),
                            rs.getString("visitType"),
                            rs.getString("defaultVoluntary")
                        );
                        assignedPlaces.add(place);
                    }
                }
            }
            
            return new GetPersonalDataReply(true, user, assignedPlaces);
        }
        
        return new GetPersonalDataReply(true, user);[cite: 27]
    } 
}