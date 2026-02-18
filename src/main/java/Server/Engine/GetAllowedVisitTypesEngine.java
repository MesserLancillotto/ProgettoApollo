package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetAllowedVisitTypesReply;

public class GetAllowedVisitTypesEngine extends AuthenticatedEngine
{

    private String data;

    private static final String QUERY = """
        SELECT visitType FROM userPermissions
        WHERE userID = ? ;
    """;

    public GetAllowedVisitTypesEngine(String data) 
    {
        super(data);
        this.data = data;
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    { 

        if(!petitionerIsVoluntary())
        {
            return new GetAllowedVisitTypesReply(false, new ArrayList<>());
        }
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, getUserID());
            
        ResultSet result = statement.executeQuery();
            
        List<String> visitTypes = new ArrayList<>();
            
        while(result.next())
        {
            visitTypes.add(result.getString("visitType"));
        }
            
        return new GetAllowedVisitTypesReply(true, visitTypes);
    }
}