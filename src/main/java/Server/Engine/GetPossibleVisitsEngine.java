package Server.Engine;

import java.util.ArrayList;
import java.util.List;  // AGGIUNTO: import mancante
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.*;
import Comunication.Reply.Interfaces.*;

public class GetPossibleVisitsEngine extends AuthenticatedEngine
{
    private static final String QUERY = 
    """
        SELECT DISTINCT visitType
        FROM USERS u
        JOIN USERPERMISSIONS p
        ON u.userID = p.userID
        WHERE u.organization = ?;
    """;

    public GetPossibleVisitsEngine(String data) 
    {
        super(data);
    }

    @Override
    public AuthenticatedReply processWithConnection() throws SQLException
    {        
        String organization = json.getString("organization"); 
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, organization);
        
        List<String> permissions = new ArrayList<String>();
        ResultSet result = statement.executeQuery();
        
        boolean query_successful = false;
        while(result.next())
        {
            query_successful = true;
            permissions.add(result.getString("visitType"));
        }
        
        return new GetPossibleVisitsReply(query_successful, permissions);
    } 
}