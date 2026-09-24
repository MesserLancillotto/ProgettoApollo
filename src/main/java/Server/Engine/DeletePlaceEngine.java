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
        );
    ""","""
        DELETE FROM eventsVoluntaries 
        WHERE name IN (
            SELECT name FROM events 
            WHERE city = ?
              AND address = ?
        );
    ""","""
        DELETE FROM events
        WHERE 
            city = ? 
            AND address = ?
    ""","""
        DELETE FROM places
        WHERE 
            city = ? 
            AND address = ?
    """
    };

    private String city;
    private String address;

    public DeletePlaceEngine
    (
        String data
    ) {
        super(data);
        this.city = json.getString("city");
        this.address = json.getString("address"); 
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {  
        if(!petitionerIsConfigurator())
        {
            return new DeletePlaceReply(false, false);
        }

        PreparedStatement organizationStatement = connection.prepareStatement(ORGANIZATION_QUERY);
        organizationStatement.setString(1, this.city);
        organizationStatement.setString(2, this.address);
        ResultSet result = organizationStatement.executeQuery();

        if(!result.next())
        {
            return new DeletePlaceReply(true, false);
        }

        if(!getOrganization().equals(result.getString("organization")))
        {
            return new DeletePlaceReply(true, false);
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
