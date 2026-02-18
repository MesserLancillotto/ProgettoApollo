package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Server.Engine.Helper.DateIntervalCalculator;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.DeleteVoluntaryReply;

public class DeleteVoluntaryEngine extends AuthenticatedEngine
{
    private static final String [] QUERIES = {
        "DELETE FROM users WHERE userID = ?",
        "DELETE FROM placesData WHERE userID = ?",
        "DELETE FROM userPermissions WHERE userID = ?",
        "DELETE FROM eventsVoluntaries WHERE userID = ?",
        "DELETE FROM voluntaryDisponibilities WHERE userID = ?"
    };

    private String targetID;

    public DeleteVoluntaryEngine
    (
        String data
    ) {
        super(data);
        this.targetID = json.getString("targetID"); 
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {          
        if(!petitionerIsConfigurator())
        {
            return new DeleteVoluntaryReply(false, false);
        }

        int totalRows = 0;

        for (String query : QUERIES) 
        {
            PreparedStatement statement = connection.prepareStatement(query); 
            
            statement.setString(1, targetID);
            totalRows += statement.executeUpdate();
            
        }

        return new DeleteVoluntaryReply(true, totalRows > 0);    
    }
}
