package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetAllowedVisitTypesReply;

public class GetAllowedVisitTypesEngine extends AuthenticatedEngine
{

    public GetAllowedVisitTypesEngine(String data) 
    {
        super(data);
        this.data = data;
    }
    
    public AuthenticatedReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new GetAllowedVisitTypesReply(false, new ArrayList<>());
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new GetAllowedVisitTypesReply(false, new ArrayList<>());
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"VOLUNTARY".equals(role))
            {
                return new GetAllowedVisitTypesReply(false, new ArrayList<>());
            }
            
            String query = """
                SELECT visitType
                FROM userPermissions
            """;
            
            statement = connection.prepareStatement(query);
            
            ResultSet result = statement.executeQuery();
            
            List<String> visitTypes = new ArrayList<>();
            
            while(result.next())
            {
                visitTypes.add(result.getString("visitType"));
            }
            
            result.close();
            statement.close();
            
            if (visitTypes.isEmpty()) 
            {
                return new GetAllowedVisitTypesReply(true, new ArrayList<>());
            }
            
            return new GetAllowedVisitTypesReply(true, visitTypes);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new GetAllowedVisitTypesReply(false, new ArrayList<>());
        }
        finally
        {
            if(statement != null)
            {
                statement.close();
            }
            disconnectDB();
        }
    }
}