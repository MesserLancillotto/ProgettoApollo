package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Helper.DateIntervalCalculator;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.DeletePlaceReply;

public class DeletePlaceEngine extends AuthenticatedEngine
{
    private static final String [] QUERIES = {
    """
        DELETE FROM eventsData 
        WHERE name IN (
            SELECT name FROM events 
            WHERE city = ?
              AND address = ?
        );
    ""","""
        DELETE FROM eventsVoluntaries 
        WHERE name IN (
            SELECT name FROM events 
            WHERE city = ?
              AND address = ? 
        );
    ""","""
        DELETE FROM placesData 
        WHERE 
            city = ? 
            AND address = ? ;
    ""","""
        DELETE FROM events
        WHERE 
            city = ? 
            AND address = ? ;
    ""","""
        DELETE FROM places
        WHERE 
            city = ? 
            AND address = ? ;
    """
    };

    private String city;
    private String address;
    private String visitType;

    public DeletePlaceEngine
    (
        String data
    ) {
        super(data);
        this.city = json.getString("city");
        this.address = json.getString("address");
        this.visitType = json.getString("visitType"); 
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {  
        if(!petitionerIsConfigurator())
        {
            return new DeletePlaceReply(false, false);
        }

        int totalRows = 0;
            
        for(String query : QUERIES)
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.city);
            statement.setString(2, this.address);
            totalRows += statement.executeUpdate();
        }
        return new DeletePlaceReply(true, totalRows > 0);
    }
}
