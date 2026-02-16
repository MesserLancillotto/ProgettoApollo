package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.EditVisitablePlacesReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;

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
        this.arrayLength = (
            visitTypesArray.length() < userIDsArray.length() ? 
            visitTypesArray.length() : userIDsArray.length());
        for(
            int i = 0; 
            i < this.arrayLength
                && i < MAX_EDIT_SIZE;
            i++
        ) {
            this.visitTypes.add(visitTypesArray.getString(i));
            this.userIDs.add(userIDsArray.getString(i));
        }
    }
    
    public AuthenticatedReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new EditVisitablePlacesReply(false, false);
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new EditVisitablePlacesReply(false, false);
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"CONFIGURATOR".equals(role))
            {
                return new EditVisitablePlacesReply(false, false);
            }
            StringBuilder query = new StringBuilder();
            List<String> params = new ArrayList<String>();
            for(int i = 0; i < this.arrayLength && i < MAX_EDIT_SIZE; i++)
            {
                query.append(
                    "INSERT INTO placesData VALUES ( ?, ?, ?, ? );\n"); 
                params.add(city);
                params.add(address);
                params.add(this.visitTypes.get(i));
                params.add(this.userIDs.get(i));
            }
            
            this.statement =
                connection.prepareStatement(query.toString());
            
            for(int i = 0; i < params.size() && i < MAX_EDIT_SIZE * 4; i++)
            {
                this.statement.setString(i + 1, params.get(i));
            }

            Integer modifiedLines = this.statement.executeUpdate();
            
            if (modifiedLines == 0)
            {
                return new EditVisitablePlacesReply(true, false);
            }
            
            return new EditVisitablePlacesReply(true, true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new EditVisitablePlacesReply(false, false);
        }
        finally
        {
            disconnectDB();
        }
    }
}