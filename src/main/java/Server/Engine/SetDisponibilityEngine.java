package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.SetDisponibilityReply;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.Interfaces.AuthenticatedReply;
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
        
        if (json.has("disponibilities")) 
        {
            JSONArray outerArray = json.getJSONArray("disponibilities");
            
            for 
            (
                int i = 0; 
                i < outerArray.length() 
                    && i < MAX_DISPONIBILITIES; 
                i++
            ) {
                JSONArray innerArray = outerArray.getJSONArray(i);
                List<Integer> innerList = new ArrayList<>();
                
                innerList.add(innerArray.getInt(0));
                innerList.add(innerArray.getInt(1));
                
                result.add(innerList);
            }
        }
        return result;
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        StringBuilder query = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        
        for
        (
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

        this.statement = connection.prepareStatement(query.toString());
        
        for
        (
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
}