package Server.Engine.Interfaces;

import org.json.*;
import java.sql.*;

import Comunication.Reply.Interfaces.ReplyInterface;

public abstract class AuthenticatedEngine implements EngineInterface
{
    protected String userID;
    protected String password;
    protected String organization;
    protected String role;

    protected JSONObject json;
    protected Connection connection;

    protected static String dbUrl = 
    """
        jdbc:h2:~/documents/IngegneriaDelSoftware2025/databases/MAIN_DB
    """;
    protected static String user = "sa";
    protected static String db_passwd = "";

    public AuthenticatedEngine(String data) 
    {
        json = new JSONObject(data);
        userID = json.getString("userID");
        password = json.getString("password");
    }

    public Boolean connectDB()
    {
        try
        {
            this.connection = DriverManager.getConnection(
                dbUrl, 
                user, 
                db_passwd
            );
            return true;
        } catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean petitionerCanLogIn()
    {
        try
        {
            if(connection == null || connection.isClosed())
            {
                connectDB();
            }
            String query = 
            """
                SELECT userID FROM users 
                WHERE userID = ? AND password = ?
            """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if(result.next())
            {
                return true;
            }
        } catch(Exception e)
        {
            e.printStackTrace(); 
        }
        return false;
    }

    public String[] getRoleAndOrganization()
    {
        try
        {
            if(connection == null || connection.isClosed())
            {
                connectDB();
            }
            String query = 
            """
                SELECT role, organization FROM users 
                WHERE userID = ? AND password = ?
            """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if(result.next())
            {
                role = result.getString("role");
                organization = result.getString("organization");
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new String[]{role, organization};
    }

    public Boolean petitionerIsConfigurator()
    {
        if(role == null)
        {
            String[] roleAndOrg = getRoleAndOrganization();
            role = roleAndOrg[0];
            organization = roleAndOrg[1];
        }
        return "CONFIGURATOR".equals(role);
    }

    public abstract ReplyInterface handleRequest();
}