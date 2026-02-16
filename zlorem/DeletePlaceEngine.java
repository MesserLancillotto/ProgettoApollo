package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Server.Engine.Helper.DateIntervalCalculator;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;
import Comunication.Reply.DeletePlaceReply;

public class DeletePlaceEngine extends AuthenticatedEngine
{
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
    
    public AuthenticatedUpdateReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new DeletePlaceReply(false, false);
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new DeletePlaceReply(false, false);
            }
            
            if(!"CONFIGURATOR".equals(role))
            {
                return new DeletePlaceReply(false, false);
            }
            
            String query = """
                BEGIN TRANSACTION;

                DELETE FROM eventsData 
                WHERE name IN (
                    SELECT name FROM events 
                    WHERE 
                        city = ? 
                        AND address = ?
                );

                DELETE FROM eventsVoluntaries 
                WHERE name IN (
                    SELECT name FROM events 
                    WHERE 
                        city = ? 
                        AND address = ?
                );

                DELETE FROM events 
                WHERE 
                    city = ? AND address = ?;
                
                DELETE FROM places
                WHERE 
                    city = ? 
                    AND address = ?;
                
                DELETE FROM placesData
                WHERE 
                    city = ? 
                    AND address = ?; 

                COMMIT;
            """;
            
            statement = connection.prepareStatement(query);
            
            statement.setString(1, city);
            statement.setString(2, address);

            statement.setString(3, city);
            statement.setString(4, address);

            statement.setString(5, city);
            statement.setString(6, address);

            statement.setString(7, city);
            statement.setString(8, address);

            statement.setString( 9, city);
            statement.setString(10, address);

            int updatedRows = statement.executeUpdate();

            if(updatedRows != 1)
            {
                return new DeletePlaceReply(true, true);
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
        return new DeletePlaceReply(true, false);    
    }
}
