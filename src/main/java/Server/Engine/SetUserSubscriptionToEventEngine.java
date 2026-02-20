package Server.Engine;

import org.json.*;
import java.util.*;
import java.sql.*;

import Comunication.DatabaseObjects.Place;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.SetUserSubscriptionToEventReply;

public class SetUserSubscriptionToEventEngine 
extends AuthenticatedEngine
{
    private static final String QUERY = """
        INSERT INTO subscriptions (userID, name, start_date)
        SELECT ? , ? , ?
        FROM users
        WHERE userID = ? AND ROLE = 'USER';
    """;

    private JSONArray friends;
    private String eventName;
    private Integer date;

    public SetUserSubscriptionToEventEngine(
        String data
    ) {
        super(data);
        friends = json.getJSONArray("friends");
        this.eventName = json.getString("eventName");
        this.date = json.getInt("date");
    }

    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if(!petitionerIsUser())
        {
            return new SetUserSubscriptionToEventReply(false, false);
        }

        int totalRows = 0;

        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, getUserID());
        statement.setString(2, this.eventName);
        statement.setInt(3, this.date);
        statement.setString(4, getUserID());
        totalRows += statement.executeUpdate();

        for(int i = 0; i < this.friends.length(); i++)
        {
            statement.setString(1, (String) this.friends.get(i));
            statement.setString(2, this.eventName);
            statement.setInt(3, this.date);
            statement.setString(4, (String) this.friends.get(i));
            totalRows += statement.executeUpdate();
        }
        return new SetUserSubscriptionToEventReply(true, totalRows > 0);
    }
}
