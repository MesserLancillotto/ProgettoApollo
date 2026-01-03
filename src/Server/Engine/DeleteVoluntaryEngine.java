package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Server.Engine.Helper.DateIntervalCalculator;
import Comunication.Reply.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.SetClosedDaysReply;

public class DeleteVoluntaryEngine extends AuthenticatedEngine
{
    private static final int MAX_TABLES = 100;
    private static final int TABLES_NUMBER = 6;

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
            return new SetClosedDaysReply(false, false);
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new SetClosedDaysReply(false, false);
            }
            
            if(!"CONFIGURATOR".equals(role))
            {
                return new SetClosedDaysReply(false, false);
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
            result.close();
            statement.close();
            disconnectDB();

            if(updatedRows != 1)
            {
                return new AuthenticatedReply(true, true);
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new AuthenticatedReply(true, false);    
    }
}
