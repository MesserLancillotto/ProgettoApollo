package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;
import Comunication.Reply.SetDisponibilityReply;
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
        disponibilities = parseDisponibilitiesFromJson(data);
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
            return new AuthenticatedUpdateReply(false, null);
        }
        
        try 
        {
            if (!petitionerCanLogIn()) 
            {
                return new AuthenticatedUpdateReply(false, null);
            }
            
            StringBuilder query = new StringBuilder();
            ArrayList<Object> params = new ArrayList<Object>();
            for(
                int i = 0; 
                i < this.disponibilities.length() 
                    && i < MAX_DISPONIBILITIES;
                i++
            ) {
                query.append(TEMPLATE);
                params.add(userID);
                params.add(this.disponibilities.get(i).get(0));
                params.add(this.disponibilities.get(i).get(1));
            }

            PreparedStatement statement = connection.prepareStatement(
                query.toString());
            
            for(
                int i = 1; 
                i < params.length() 
                    && i < MAX_PARAMETERS;
                i += 3
            ) {
                statement.setString(i + 0, params.get(i + 0));
                statement.setInt(i + 1, params.get(i + 1));
                statement.setInt(i + 2, params.get(i + 2));
            }

            if(statement.executeUpdate() != 0)
            {
                return new SetDisponibilityReply(true, true);
            }
            return new SetDisponibilityReply(true, true);
        
        } catch (Exception e) {
            e.printStackTrace();
            return new GetVoluntariesReply(true, null);
        } finally {
            disconnectDB();
        }
    }
}