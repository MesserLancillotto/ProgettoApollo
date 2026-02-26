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
        this.visitTypes = new ArrayList<>();
        this.userIDs = new ArrayList<>(); 
        
        JSONArray visitTypesArray = json.getJSONArray("visitTypes");
        JSONArray userIDsArray = json.getJSONArray("userIDs");
        
        this.arrayLength = Math.min(
            visitTypesArray.length(), 
            userIDsArray.length()
        );
        
        for(int i = 0; i < this.arrayLength && i < MAX_EDIT_SIZE; i++) 
        {
            this.visitTypes.add(visitTypesArray.getString(i));
            this.userIDs.add(userIDsArray.getString(i));
        }
    }    
    
    protected AuthenticatedReply processWithConnection() 
        throws SQLException
    {
        if(!petitionerIsConfigurator())
        {
            return new EditVisitablePlacesReply(false, false);
        }
        
        String query = """
            INSERT INTO placesData (city, address, visitType, userID) 
            VALUES (?, ?, ?, ?)
        """;
        
        PreparedStatement statement = connection.prepareStatement(query);
        
        for(int i = 0; i < this.visitTypes.size(); i++)
        {
            statement.setString(1, city);
            statement.setString(2, address);
            statement.setString(3, this.visitTypes.get(i));
            statement.setString(4, this.userIDs.get(i));
            statement.addBatch();
        }
        
        int[] results = statement.executeBatch();
        
        int successCount = 0;
        for (int result : results) 
        {
            if (result == Statement.SUCCESS_NO_INFO || result >= 0) {
                successCount++;
            }
        }
        
        return new EditVisitablePlacesReply(true, successCount > 0);
    
    }
}