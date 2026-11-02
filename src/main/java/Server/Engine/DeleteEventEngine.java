package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.DeleteEventReply;

public class DeleteEventEngine extends AuthenticatedEngine
{
    private static final String [] QUERIES = {
        "DELETE FROM subscriptions WHERE name = ?",
        "DELETE FROM eventsVoluntaries WHERE name = ?",
        "DELETE FROM eventsData WHERE name = ?",
        "DELETE FROM events WHERE name = ?"
    };

    public DeleteEventEngine(String data) 
    {
        super(data);
    }

    @Override
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if (!petitionerIsConfigurator()) 
        {
            return new DeleteEventReply(false, false);
        }

        String eventName = "";
        if (json.has("event") && json.optJSONObject("event") != null) 
        {
            eventName = json.getJSONObject("event").optString("name", "");
        }
        if (eventName.isEmpty() && json.has("name")) 
        {
            eventName = json.getString("name");
        }
        eventName = eventName.trim();
        if (eventName.isEmpty()) 
        {
            return new DeleteEventReply(true, false);
        }

        // Verifica che l'evento appartenga all'organizzazione del configuratore
        String checkOrgQuery = "SELECT organization FROM events WHERE name = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkOrgQuery)) {
            checkStmt.setString(1, eventName);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    return new DeleteEventReply(true, false);
                }
                String org = rs.getString("organization");
                if (org == null || !org.equals(getOrganization())) {
                    return new DeleteEventReply(true, false);
                }
            }
        }

        int totalDeleted = 0;
        for (String query : QUERIES) 
        {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, eventName);
                totalDeleted += stmt.executeUpdate();
            }
        }

        return new DeleteEventReply(true, totalDeleted > 0);
    }
}
