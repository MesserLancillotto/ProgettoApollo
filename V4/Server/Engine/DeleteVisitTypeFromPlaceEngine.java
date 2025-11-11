package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

public class DeleteVisitTypeFromPlaceEngine extends Engine
{
    private String userID;
    private String password;
    private String city;
    private String address;
    private String visitType;

    public DeleteVisitTypeFromPlaceEngine
    (
        String userID, 
        String password, 
        String city, 
        String address, 
        String visitType
    ) {
        this.userID = userID;
        this.password = password;
        this.city = city;
        this.address = address;
        this.visitType = visitType;
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
                return new DeleteVisitTypeFromPlaceReply(false, 0).toJSONString(); 
            }
            
            if(!userResult.getString("role").equals("CONFIGURATOR")) {
                return new DeleteVisitTypeFromPlaceReply(true, 0).toJSONString(); 
            }
            
            String userOrganization = userResult.getString("organization");
            String query = """
                DELETE FROM events 
                WHERE organization = ? 
                AND city = ?
                AND address = ?
                AND visitType = ?
                """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userOrganization);
            statement.setString(2, city);
            statement.setString(3, address);
            statement.setString(3, visitType);
            int rowsDeleted = statement.executeUpdate();
            return new DeleteVisitTypeFromPlaceReply(true, rowsDeleted).toJSONString(); 
            
        } catch(Exception e) {
            e.printStackTrace();
            return new DeleteVisitTypeFromPlaceReply(false, 0).toJSONString();
        }
    }
}