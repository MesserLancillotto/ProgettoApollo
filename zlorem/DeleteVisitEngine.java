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
    
    public AuthenticatedUpdateReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new DeleteVisitReply(false, false);
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new DeleteVisitReply(false, false);
            }
            
            if(!"CONFIGURATOR".equals(role))
            {
                return new DeleteVisitReply(false, false);
            }
            
            String query = """
                DELETE FROM placesData 
                WHERE 
                    city = ? 
                    AND address = ? 
                    AND visitType = ?;
            """;
            
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, city);
            statement.setString(2, address);
            statement.setString(3, visitType);

            int updatedRows = statement.executeUpdate();

            if(updatedRows != 1)
            {
                return new DeleteVisitReply(true, true);
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            disconnectDB();
        }
        return new DeleteVisitReply(true, false);    
    }
}
