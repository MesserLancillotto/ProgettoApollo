package Server.Engine;

import org.json.*;
import java.util.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.SetVoluntarySubscriptionToEventReply;

public class SetVoluntarySubscriptionToEventEngine 
extends AuthenticatedEngine
{
    private static final String QUERY = """
        INSERT INTO eventsVoluntaries (userID, name, date)
        SELECT ? , ? , ?
        FROM users
        WHERE userID = ? AND ROLE = 'VOLUNTARY';
    """;

    private JSONArray friends;
    private String eventName;
    private Integer date;

    public SetVoluntarySubscriptionToEventEngine(
        String data
    ) {
        super(data);
        this.friends = json.optJSONArray("friends");
        if (this.friends == null) {
            this.friends = new JSONArray();
        }
        this.eventName = json.getString("eventName");
        this.date = json.getInt("date");
    }

    public AuthenticatedReply processWithConnection() throws SQLException
    {
        String role = getRole();
        if(!"VOLUNTARY".equals(role) || !petitionerCanLogIn())
        {
            return new SetVoluntarySubscriptionToEventReply(false, false);
        }

        int totalRows = 0;

        try (PreparedStatement statement = connection.prepareStatement(QUERY)) {
            statement.setString(1, getUserID());
            statement.setString(2, this.eventName);
            statement.setInt(3, this.date);
            statement.setString(4, getUserID());
            totalRows += statement.executeUpdate();

            for(int i = 0; i < this.friends.length(); i++)
            {
                String friendID = this.friends.getString(i);
                statement.setString(1, friendID);
                statement.setString(2, this.eventName);
                statement.setInt(3, this.date);
                statement.setString(4, friendID);
                totalRows += statement.executeUpdate();
            }
        }
        return new SetVoluntarySubscriptionToEventReply(true, totalRows > 0);
    }
}
