package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.EditVisitablePlacesReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class EditVisitablePlacesEngine extends AuthenticatedEngine
{
    private String city;
    private String address;
    private String visitType;
    private String voluntary;

    public EditVisitablePlacesEngine(String data) 
    {
        super(data);
        this.city = json.getString("city");
        this.address = json.getString("address");
        this.visitType = json.getString("visitType");
        this.voluntary = json.getString("newDefauldVoluntary");
    }
    
    protected AuthenticatedReply processWithConnection() 
        throws SQLException
    {
        if(!petitionerIsConfigurator())
        {
            return new EditVisitablePlacesReply(false, false);
        }
        
        if(!sameOrganizationPlaceConfigurator(city, address))
        {
            return new EditVisitablePlacesReply(true, false);
        }

        String query = """
            UPDATE places 
            SET userID = ? 
            WHERE city = ? 
              AND address = ? 
              AND visitType = ?
              AND EXISTS (
                  SELECT 1 
                  FROM userPermissions up 
                  WHERE up.userID = ? 
                    AND up.visitType = places.visitType
              );
        """;

        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, voluntary);
        statement.setString(2, city);
        statement.setString(3, address);
        statement.setString(4, visitType);
        statement.setString(5, voluntary);
        
        int successCount = statement.executeUpdate();
        
        System.out.println(statement.toString());
        System.out.println(successCount);

        return new EditVisitablePlacesReply(true, successCount > 0);
    
    }
}