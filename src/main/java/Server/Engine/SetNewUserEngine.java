package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.SetNewUserReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import java.time.Instant;

public class SetNewUserEngine extends AuthenticatedEngine
{
    private static final String INSERT_USER_IF_NOT_EXISTS = 
    """
        INSERT INTO users (userID, password, userName, userSurname, city, birth_dd, birth_mm, birth_yy, userSince, organization, role, changePasswordDue)
        SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
        WHERE NOT EXISTS (SELECT 1 FROM users WHERE userID = ?)
    """;

    public SetNewUserEngine(String data) 
    {
        super(data);
    }
    
    private String generateUserID() throws JSONException
    {
        String name = json.getString("name").trim().replaceAll(" +", ".");
        String surname = json.getString("surname").trim().replaceAll(" +", ".");
        return new StringBuilder()
            .append(name)
            .append(".")
            .append(surname)
            .append(".")
            .append(String.format("%02d", json.getInt("birth_yy") % 100))
            .toString();
    }

    @Override
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        String userID = generateUserID();
        
        PreparedStatement statement 
            = connection.prepareStatement(INSERT_USER_IF_NOT_EXISTS);
            
        statement.setString(1, userID);
        statement.setString(2, json.optString("newUserPassword", json.optString("password", "")));
        statement.setString(3, json.getString("name"));
        statement.setString(4, json.getString("surname"));
        statement.setString(5, json.getString("city"));
        statement.setInt(6, json.getInt("birth_dd"));
        statement.setInt(7, json.getInt("birth_mm"));
        statement.setInt(8, json.getInt("birth_yy"));
        statement.setInt(9, json.optInt("user_since", (int) Instant.now().getEpochSecond()));
        statement.setString(10, json.optString("organization", ""));
        statement.setString(11, json.optString("role", "VOLUNTARY"));
        statement.setBoolean(12, true);
        statement.setString(13, userID);

        int linesChanged = statement.executeUpdate();

        boolean updateSuccessful = (linesChanged == 1);
        
        return new SetNewUserReply(true, updateSuccessful, userID);
    }
}