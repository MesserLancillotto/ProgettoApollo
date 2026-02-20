package Server.Engine;

import org.json.*;
import java.util.*;
import java.sql.*;

import Comunication.DatabaseObjects.Place;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.SetUserSubscriptionToEventReply;

public class DeleteUserSubscriptionToEventEngine 
extends AuthenticatedEngine
{
    private static final String QUERY = """
        DELETE FROM subscriptions 
        WHERE userID = ? AND name = ? AND start_date = ?;
    """;

    private String eventName;
    private Integer date;

    public DeleteUserSubscriptionToEventEngine(
        String data
    ) {
        super(data);
        this.eventName = json.getString("eventName");
        this.date = json.getInt("date");
    }

    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if(!petitionerIsUser() || !petitionerCanLogIn())
        {
            return new SetUserSubscriptionToEventReply(false, false);
        }

        int totalRows = 0;

        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, getUserID());
        statement.setString(2, this.eventName);
        statement.setInt(3, this.date);
        totalRows += statement.executeUpdate();

        return new SetUserSubscriptionToEventReply(true, totalRows > 0);
    }
}
