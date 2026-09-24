package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Helper.DateIntervalCalculator;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.DeleteVisitTypeFromPlaceReply;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.SetClosedDaysReply;

public class DeleteVisitTypeFromPlaceEngine extends AuthenticatedEngine
{

    private static final String ORGANIZATION_QUERY = """
        SELECT organization FROM places WHERE city = ? and address = ?;
    """;

    private static final String [] QUERIES = {
    """
        DELETE FROM eventsData 
        WHERE name IN (
            SELECT name FROM events 
            WHERE city = ?
              AND address = ?
              AND visitType = ?
        );
    ""","""
        DELETE FROM eventsVoluntaries 
        WHERE name IN (
            SELECT name FROM events 
            WHERE city = ?
              AND address = ? 
              AND visitType = ?
        );
    ""","""
        DELETE FROM placesData 
        WHERE 
            city = ? 
            AND address = ? 
            AND visitType = ?;
    ""","""
        DELETE FROM events
        WHERE 
            city = ? 
            AND address = ? 
            AND visitType = ?;
    ""","""
        DELETE FROM places
        WHERE 
            city = ? 
            AND address = ? 
            AND visitType = ?;
    """
    };

    private String city;
    private String address;
    private String visitType;

    public DeleteVisitTypeFromPlaceEngine
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
            return new DeleteVisitTypeFromPlaceReply(false, false);
        }
        if(!petitionerIsConfigurator())
        {
            return new DeleteVisitTypeFromPlaceReply(false, false);
        }

        PreparedStatement organizationStatement = connection.prepareStatement(ORGANIZATION_QUERY);
        organizationStatement.setString(1, this.city);
        organizationStatement.setString(2, this.address);
        ResultSet result = organizationStatement.executeQuery();

        if(!result.next())
        {
            return new DeleteVisitTypeFromPlaceReply(true, false);
        }

        if(!getOrganization().equals(result.getString("organization")))
        {
            return new DeleteVisitTypeFromPlaceReply(true, false);
        }

        int updatedRows = 0;

        for(String query : QUERIES)
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, city);
            statement.setString(2, address);
            statement.setString(3, visitType);
            updatedRows += statement.executeUpdate();
        }
        
        return new DeleteVisitTypeFromPlaceReply(true, updatedRows > 0);    
    }
}
