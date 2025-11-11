package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

public class DeleteVoluntaryEngine extends Engine
{
    private String userID;
    private String password;
    private String targetID;

    public DeleteVoluntaryEngine
    (
        String userID, 
        String password, 
        String targetID
    ) {
        this.userID = userID;
        this.password = password;
        this.targetID = targetID;
    }

    public String handleRequest()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) {
            String loginQuery = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, userID);
            loginStatement.setString(2, password);
            ResultSet userResult = loginStatement.executeQuery(); 
            
            if(!userResult.next()) {
                return new DeleteVoluntaryReply(false, 0).toJSONString(); 
            }
            
            if(!userResult.getString("role").equals("CONFIGURATOR")) {
                return new DeleteVoluntaryReply(true, 0).toJSONString(); 
            }
            
            String userOrganization = userResult.getString("organization");
            String query = """
                DELETE FROM users 
                WHERE organization = ? 
                AND userID = ?;
            """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userOrganization);
            statement.setString(2, targetID);
            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0)
            {
                query = "DELETE FROM allowedVisits WHERE userID = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, targetID);
                rowsDeleted += statement.executeUpdate();
            }
            return new DeleteVoluntaryReply(true, rowsDeleted).toJSONString(); 
            
        } catch(Exception e) {
            e.printStackTrace();
            return new DeleteVoluntaryReply(false, 0).toJSONString();
        }
    }
}