package Server.Engine;

import org.json.*;
import java.sql.*;
import java.time.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.SetNewUserReply;  // Cambiato da SetNewPasswordReply
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class SetNewUserEngine extends AuthenticatedEngine
{
    private static final String INSERT_USER_IF_NOT_EXISTS = 
    """
        INSERT INTO users (userID, password, name, surname, city, birth_dd, birth_mm, birth_yy, user_since, organization, role, change_password_due)
        SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
        WHERE NOT EXISTS (SELECT 1 FROM users WHERE userID = ?)
    """;

    public SetNewUserEngine(String data) 
    {
        super(data);
    }
    
    private String generateUserID() throws JSONException
    {
        return new StringBuilder()
            .append(json.getString("name"))
            .append(".")
            .append(json.getString("surname"))
            .append(".")
            .append(json.getInt("birth_yy") % 100)
            .toString();
    }

    @Override
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        String userID = generateUserID();
        
        PreparedStatement statement 
            = connection.prepareStatement(INSERT_USER_IF_NOT_EXISTS);
        statement.setString(1, userID);
        statement.setString(2, json.getString("password"));
        statement.setString(3, json.getString("name"));
        statement.setString(4, json.getString("surname"));
        statement.setString(5, json.getString("city"));
        statement.setInt(6, json.getInt("birth_dd"));
        statement.setInt(7, json.getInt("birth_mm"));
        statement.setInt(8, json.getInt("birth_yy"));
        statement.setLong(9, LocalDate.now(ZoneId.of("Europe/Rome"))
            .toEpochSecond(
                LocalTime.now(ZoneId.of("Europe/Rome")), ZoneOffset.UTC));
        statement.setString(10, json.getString("organization"));
        statement.setString(11, json.getString("role"));
        statement.setBoolean(12, false);
        statement.setString(13, userID);

        int linesChanged = statement.executeUpdate();

        boolean updateSuccessful = (linesChanged == 1);
        
        return new SetNewUserReply(true, updateSuccessful, userID);
    }
}