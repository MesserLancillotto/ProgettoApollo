package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.SetDisponibilityReply;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;
import Comunication.DatabaseObjects.User;
import Comunication.DatabaseObjects.UserRole;

public class SetDisponibilityEngine extends AuthenticatedEngine
{
    private static String TEMPLATE = """
                INSERT INTO voluntaryDisponibilities
                VALUES ( ? , ? , ? );
    """;
    private List<List<Integer>> disponibilities;

    public SetDisponibilityEngine(String data) 
    {
        super(data);
        JSONObject jsonObject = new JSONObject(data); 
        this.disponibilities = parseDisponibilitiesFromJson(jsonObject);
    }
    
    public static List<List<Integer>> parseDisponibilitiesFromJson
    (
        JSONObject json
    ) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (json.has("disponibilities")) {
            JSONArray outerArray = json.getJSONArray("disponibilities");
            
            for (int i = 0; i < outerArray.length(); i++) 
            {
                JSONArray innerArray = outerArray.getJSONArray(i);
                List<Integer> innerList = new ArrayList<>();
                
                for (int j = 0; j < innerArray.length(); j++) 
                {
                    innerList.add(innerArray.getInt(j));
                }
                result.add(innerList);
            }
        }
        return result;
    }
    
    public ReplyInterface handleRequest()
    {
        if (!connectDB()) 
        {
            return new SetDisponibilityReply(false, false);
        }
        
        try 
        {
            if (!petitionerCanLogIn()) 
            {
                return new SetDisponibilityReply(false, false);
            }
            
            StringBuilder query = new StringBuilder();
            ArrayList<Object> params = new ArrayList<Object>();
            for(
                int i = 0; 
                i < this.disponibilities.size()
                    && i < MAX_DISPONIBILITIES;
                i++
            ) {
                query.append(TEMPLATE);
                params.add(userID);
                params.add(this.disponibilities.get(i).get(0));
                params.add(this.disponibilities.get(i).get(1));
            }

            this.statement = connection.prepareStatement(
                query.toString());
            
            for(
                int i = 0;
                i < params.size()
                    && i < MAX_PARAMETERS;
                i += 3
            ) {
                this.statement.setString(i + 1, (String) params.get(i));  
                this.statement.setInt(i + 2, (Integer) params.get(i + 1));
                this.statement.setInt(i + 3, (Integer) params.get(i + 2));
            }

            if(this.statement.executeUpdate() > 0)
            {
                return new SetDisponibilityReply(true, true);
            }
            return new SetDisponibilityReply(true, false);
        
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return new SetDisponibilityReply(false, false);
        } 
        finally 
        {
            if(this.statement != null) {
                try {
                    this.statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            disconnectDB();
        }
    }
}