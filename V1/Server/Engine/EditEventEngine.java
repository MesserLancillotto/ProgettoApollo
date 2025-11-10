package Server.Engine;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import org.json.*;
import java.util.concurrent.*;

import RequestReply.Reply.*;
import RequestReply.Request.*;
import RequestReply.ComunicationType.*;
import RequestReply.UserRoleTitle.*;
import Server.Engine.*;

public class EditEventEngine extends Engine
{
    private String userID;
    private String password;
    private Map<String, Object> fields;

    public EditEventEngine
    (
        String userID,
        String password,
        Map<String, Object> fields
    ) {
        this.userID = userID;
        this.password = password;
        this.fields = fields;
    }

    public String handleRequest()
    {
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            String loginQuery = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, userID);
            loginStatement.setString(2, password);
            ResultSet userResult = loginStatement.executeQuery(); 
            if(!userResult.next())
            {
                return new EditEventReply(false, false, false, false).toJSONString();
            }
            if(!userResult.getString("role").equals("CONFIGURATOR"))
            {
                return new EditEventReply(true, false, true, false).toJSONString();
            }
            String userOrganization = userResult.getString("organization");

            String organizationQuery = "SELECT organization FROM events WHERE eventName = ?";
            PreparedStatement organizationStatement = connection.prepareStatement(organizationQuery);
            organizationStatement.setString(1, (String)fields.get("eventName"));
            ResultSet organizationResult = organizationStatement.executeQuery();

            if (!organizationResult.next()) 
            {
                return new EditEventReply(false).toJSONString();
            }
            String eventOrganization = organizationResult.getString("organization");
            if (!userOrganization.equals(eventOrganization)) 
            {
                return new EditEventReply(false).toJSONString();
            }
            List<String> keys = new ArrayList<>(fields.keySet());
            keys.remove("eventName");
            StringBuilder query = new StringBuilder("UPDATE events SET ");
            for (int i = 0; i < keys.size(); i++) {
                if (i > 0) {
                    query.append(", ");
                }
                query.append(keys.get(i)).append(" = ?");
            }
            query.append(" WHERE eventName = ?");
            PreparedStatement statement = connection.prepareStatement(query.toString());
            int paramIndex = 1;
            for (String key : keys) {
                statement.setObject(paramIndex, fields.get(key));
                paramIndex++;
            }
            statement.setString(paramIndex, (String)fields.get("eventName"));
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 1) 
            {
               return new EditEventReply(true, true, false, false).toJSONString();
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new EditEventReply(true, false, false, false).toJSONString();
    }
}