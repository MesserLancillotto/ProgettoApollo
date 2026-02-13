package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Server.Engine.Helper.DateIntervalCalculator;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;
import Comunication.Reply.DeleteVoluntaryReply;

public class DeleteVoluntaryEngine extends AuthenticatedEngine
{
    private String targetID;

    public DeleteVoluntaryEngine
    (
        String data
    ) {
        super(data);
        this.targetID = json.getString("targetID"); 
    }
    
    public AuthenticatedUpdateReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new DeleteVoluntaryReply(false, false);
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new DeleteVoluntaryReply(false, false);
            }
            
            if(!"CONFIGURATOR".equals(role))
            {
                return new DeleteVoluntaryReply(false, false);
            }
            
            String query = """
                DELETE FROM userd WHERE userID = ?;
                DELEET FROM placesData WHERE userID = ?;
                DELETE FROM userPermissions WHERE userID = ?;
                DELETE FROM eventsData WHERE userID = ?;
                DELETE FROM eventsVoluntaries WHERE userID = ?;
                DELETE FROM voluntariesDisponibilities WHERE userID = ?;
            """;
            
            PreparedStatement statement = connection.prepareStatement(query);
            
            for(int i = 1; i <= TABLES_NUMBER && i < MAX_TABLES; i++)
            {
                statement.setString(i, organization);
            }

            int updatedRows = statement.executeUpdate();

            if(updatedRows != 1)
            {
                return new DeleteVoluntaryReply(true, true);
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
        return new DeleteVoluntaryReply(true, false);    
    }
}
