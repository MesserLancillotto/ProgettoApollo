package Server.Engine;

import org.json.*;
import java.sql.*;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.SetVoluntarySubscriptionToEventReply; // oppure una reply booleana generica
import Server.Engine.Interfaces.AuthenticatedEngine;

public class DeleteVoluntarySubscriptionToEventEngine extends AuthenticatedEngine {

    private static final String QUERY = """
        DELETE FROM eventsVoluntaries 
        WHERE userID = ? AND name = ? AND date = ?;
    """;

    private String eventName;
    private int date;

    public DeleteVoluntarySubscriptionToEventEngine(String data) {
        super(data);
        this.eventName = json.getString("eventName");
        this.date = json.getInt("date");
    }

    @Override
    public AuthenticatedReply processWithConnection() throws SQLException {
        if (!petitionerCanLogIn() || !"VOLUNTARY".equals(getRole())) {
            return new SetVoluntarySubscriptionToEventReply(false, false);
        }

        int affectedRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(QUERY)) {
            statement.setString(1, getUserID());
            statement.setString(2, this.eventName);
            statement.setInt(3, this.date);
            affectedRows = statement.executeUpdate();
        }

        return new SetVoluntarySubscriptionToEventReply(true, affectedRows > 0);
    }
}