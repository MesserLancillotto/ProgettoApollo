package Server.Engine.Interfaces;

import org.json.*;
import java.sql.*;

import java.lang.AutoCloseable;

import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.NegativeAuthenticatedReply;

public abstract class AuthenticatedEngine implements EngineInterface
{
    protected static final int MAX_TABLES = 100;
    protected static final int TABLES_NUMBER = 6;
    protected static final int MAX_EDIT_SIZE = 50;
    protected static final int MAX_FILTERS = 5;
    protected static final int MAX_RESULTS = 1000;
    protected static final int MAX_DISPONIBILITIES = 31;
    protected static final int MAX_PARAMETERS = 100;
    protected static final int MAX_PLACES = 1000;
    protected static final int MAX_VOLUNTARIES = 1000;

    protected String userID;
    protected String password;
    protected String organization;
    protected String role;
    private Boolean canLogIn;

    protected JSONObject json;
    protected Connection connection;
    protected PreparedStatement statement;

    protected static String DB_URL 
        = "jdbc:h2:~/documents/ProgettoApollo/databases/MAIN_DB";
    protected static String user = "sa";
    protected static String db_passwd = "";

    public AuthenticatedEngine(String data) 
    {
        json = new JSONObject(data);
        userID = json.getString("userID");
        password = json.getString("password");
    }

    private Boolean petitionerCanLogIn()
    {
        if(this.canLogIn != null)
        {
            return this.canLogIn;
        }
        try
        {
            String query = 
            """
                SELECT userID FROM users 
                WHERE userID = ? AND password = ?
            """;
            PreparedStatement statement 
                = this.connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            this.canLogIn = result.next();
        } 
        catch(Exception e)
        {
            e.printStackTrace();
            this.canLogIn = false;
        }
        return this.canLogIn;
    }

    private void getRoleAndOrganization()
    {
        try
        {
            String query = 
            """
                SELECT role, organization FROM users 
                WHERE userID = ? AND password = ?
            """;
            PreparedStatement statement 
                = this.connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
                        
            if(result.next())
            {
                this.role = result.getString("role");
                this.organization = result.getString("organization");
            }

        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected boolean petitionerIsConfigurator()
    {
        if(this.role == null)
        {
            getRoleAndOrganization();
        }
        return "CONFIGURATOR".equals(this.role);
    }

    protected String getRole()
    {
        if(this.role == null)
        {
            getRoleAndOrganization();
        }
        return this.role;
    }

    protected String getOrganization()
    {
        if(this.organization == null)
        {
            getRoleAndOrganization();
        }
        return this.organization;
    }

    public AuthenticatedReply handleRequest()
    {
        try
        (
            Connection connection
                = DriverManager.getConnection(DB_URL, user, db_passwd)
        ) {
            this.connection = connection;
            if(!petitionerCanLogIn())
            {
                return new NegativeAuthenticatedReply();
            }
            getRoleAndOrganization();
            return processWithConnection();
        } 
        catch(Exception e)
        {
            e.printStackTrace();
            return new NegativeAuthenticatedReply();
        }
        finally
        {
            this.connection = null;
        }
    }

    protected abstract AuthenticatedReply processWithConnection() 
        throws SQLException;
}