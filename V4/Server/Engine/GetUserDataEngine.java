package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

public class GetUserDataEngine extends Engine 
{
    private String userID;
    private String userPassword;
    private String targetID; // chiedi login se target = user, chiedi dati sottoposto se user != target  

    public GetUserDataEngine(String userID, String userPassword, String targetID) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.targetID = targetID;
    }

    private String getDataAsUser()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) {
            String query = "SELECT userName, cityOfResidence, birthYear, role, organization, changePasswordDue FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            ResultSet result = statement.executeQuery();
            String visitTypeQuery = "SELECT visitType FROM allowedVisits WHERE userID = ?";
            PreparedStatement visitTypeStatement = connection.prepareStatement(visitTypeQuery);
            visitTypeStatement.setString(1, userID);
            ResultSet visitTypes = visitTypeStatement.executeQuery();
            ArrayList<String> allowedVisitTypes = new ArrayList<>();
            while (visitTypes.next()) {
                String visitType = visitTypes.getString("visitType");
                allowedVisitTypes.add(visitType);
            }
            if(result.next())
            {
                return new GetUserDataReply(
                    result.getBoolean("changePasswordDue"),
                    result.getString("userName"),
                    result.getString("cityOfResidence"),
                    result.getInt("birthYear"),
                    result.getString("role"),
                    result.getString("organization"),
                    allowedVisitTypes
                ).toJSONString();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new GetUserDataReply().toJSONString();
    }

    private String getDataAsConfigurator()
    {
        try (Connection connection = connectDB(dbUrl, "sa", "")) {
            String query = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, userPassword);
            ResultSet result = statement.executeQuery();
            if(!result.next() || !result.getString("role").equals("CONFIGURATOR"))
            {
                return new GetUserDataReply().toJSONString();
            }
            String configuratorOrganization = result.getString("organization");
            String userDataQuery = "SELECT userName, cityOfResidence, birthYear, role, organization FROM users WHERE userID = ?";
            PreparedStatement userStatement = connection.prepareStatement(userDataQuery);
            userStatement.setString(1, targetID);
            ResultSet userResult = userStatement.executeQuery();
            if(!userResult.next() || !userResult.getString("organization").equals(configuratorOrganization))
            {
                return new GetUserDataReply().toJSONString();
            }
            String visitTypeQuery = "SELECT visitType FROM allowedVisits WHERE userID = ?";
            PreparedStatement visitTypeStatement = connection.prepareStatement(visitTypeQuery);
            visitTypeStatement.setString(1, targetID);
            ResultSet visitTypes = visitTypeStatement.executeQuery();
            ArrayList<String> allowedVisitTypes = new ArrayList<>();
            while (visitTypes.next()) {
                String visitType = visitTypes.getString("visitType");
                allowedVisitTypes.add(visitType);
            }

            return new GetUserDataReply(
                false,
                userResult.getString("userName"),
                userResult.getString("cityOfResidence"),
                userResult.getInt("birthYear"),
                userResult.getString("role"),
                userResult.getString("organization"),
                allowedVisitTypes
            ).toJSONString();

        } catch(Exception e) {
            e.printStackTrace();
            return new GetUserDataReply().toJSONString();
        }
    }
    
    public String handleRequest()
    {
        if(userID.equals(targetID))
        {
            return getDataAsUser();
        }
        return getDataAsConfigurator();
    }
}