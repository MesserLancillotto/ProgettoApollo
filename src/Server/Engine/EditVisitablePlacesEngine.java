package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetAllowedVisitTypesReply;

public class EditVisitablePlacesEngine extends AuthenticatedEngine
{
    private String city;
    private String address;
    private List<String> visitTypes;
    private List<String> userIDs;
    private Integer arrayLength;

    public EditVisitablePlacesEngine(String data) 
    {
        super(data);
        this.city = json.getString("city");
        this.address = json.getString("address");
        this.visitTypes = new ArrayList<String>();
        this.userIDs = new ArrayList<String>(); 
        JSONArray visitTypesArray = json.getJSONArray("visitTypes");
        JSONArray userIDsArray = json.getJSONArray("userIDs");
        int arrayLength = (
            visitTypesArray.length() < userIDsArray.length() ? 
            visitTypesArray.length() : userIDsArray.length());
        for(
            int i = 0; 
            i < arrayLength
                && i < MAX_EDIT_SIZE;
            i++
        ) {
            visitTypes.put(visitTypesArray.get(i));
            userIDs.put(userIDsArray.get(i));
        }
    }
    
    public AuthenticatedReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new AuthenticatedReply(false, new ArrayList<>());
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new AuthenticatedReply(false, new ArrayList<>());
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"CONFIGURATOR".equals(role))
            {
                return new AuthenticatedReply(false, new ArrayList<>());
            }
            StringBuilder query = new StringBuilder();
            List<String> params = new ArrayList<String>();
            for(int i = 0; i < arrayLength && i < MAX_EDIT_SIZE; i++)
            {
                query.append(
                    "INSERT INTO placesData VALUES ( ? , ? , ? , ? );\n");
                params.put(city);
                params.put(address);
                params.put(visitTypes);
                params.put(userIDs);                
            }
            
            PreparedStatement statement = connection.prepareStatement(query);
            for(int i = 0, i < params.length() && i < MAX_EDIT_SIZE * 4; i++)
            {
                statement.setString(params.get(i + 1));
            }

            Integer modifiedLines = statement.executeUpdate();
            
            List<String> visitTypes = new ArrayList<>();

            
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
            result.close();
            statement.close();
            disconnectDB();
        }
    }
}