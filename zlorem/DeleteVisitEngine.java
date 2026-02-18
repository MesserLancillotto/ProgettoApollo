package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Server.Engine.Helper.DateIntervalCalculator;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;
import Comunication.Reply.DeleteVisitReply;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.SetClosedDaysReply;

public class DeleteVisitEngine extends AuthenticatedEngine
{
    private static final String QUERY = """
        DELETE FROM placesData 
        WHERE 
            city = ? 
            AND address = ? 
            AND visitType = ?;
    """;

    private String city;
    private String address;
    private String visitType;

    public DeleteVisitEngine
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
            return new DeleteVisitReply(false, false);
        }
        
        PreparedStatement statement = connection.prepareStatement(query);
            
        statement.setString(1, city);
        statement.setString(2, address);
        statement.setString(3, visitType);

        int updatedRows = statement.executeUpdate();

        return new DeleteVisitReply(true, updatedRows > 0);    
    }
}
