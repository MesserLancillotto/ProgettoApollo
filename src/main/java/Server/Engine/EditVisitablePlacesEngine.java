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
        
        String query = """
            UPDATE places 
            SET userID = ? 
            WHERE city = ? 
              AND address = ? 
              AND visitType = ?;
        """;
        
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, city);
        statement.setString(2, address);
        statement.setString(3, visitType);
        statement.setString(4, voluntary);
        
        int successCount = statement.executeUpdate();
        
        return new EditVisitablePlacesReply(true, successCount > 0);
    
    }
}