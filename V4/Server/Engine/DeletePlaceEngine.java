package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

public class DeletePlaceEngine extends Engine
{
    private String userID;
    private String password;
    private String city;
    private String address;

    public DeletePlaceEngine(String userID, String password, String city, String address) {
        this.userID = userID;
        this.password = password;
        this.city = city;
        this.address = address;
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
                return new DeletePlaceReply(false, 0).toJSONString(); 
            }
            
            if(!userResult.getString("role").equals("CONFIGURATOR")) {
                return new DeletePlaceReply(true, 0).toJSONString(); 
            }
            
            String userOrganization = userResult.getString("organization");
            String query = """
                DELETE FROM events 
                WHERE organization = ? 
                AND city = ?
                AND address = ?
                """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userOrganization);
            statement.setString(2, city);
            statement.setString(3, address);
            int rowsDeleted = statement.executeUpdate();
            return new DeletePlaceReply(true, rowsDeleted).toJSONString(); 
            
        } catch(Exception e) {
            e.printStackTrace();
            return new DeletePlaceReply(false, 0).toJSONString();
        }
    }
}