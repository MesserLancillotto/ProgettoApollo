package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.DeleteEventInstanceReply;

public class DeleteEventInstanceEngine extends AuthenticatedEngine
{
    public DeleteEventInstanceEngine(String data) 
    {
        super(data);
    }

    @Override
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if (!petitionerIsConfigurator()) 
        {
            return new DeleteEventInstanceReply(false, false);
        }

        String eventName = "";
        List<Integer> startDates = new ArrayList<>();

        if (json.has("event") && json.optJSONObject("event") != null) 
        {
            JSONObject eventObj = json.getJSONObject("event");
            eventName = eventObj.optString("name", "");
            JSONArray instances = eventObj.optJSONArray("instances");
            if (instances != null) {
                for (int i = 0; i < instances.length(); i++) {
                    JSONObject inst = instances.getJSONObject(i);
                    if (inst.has("start_date")) {
                        startDates.add(inst.getInt("start_date"));
                    }
                }
            }
        }
        if (eventName.isEmpty() && json.has("name")) 
        {
            eventName = json.getString("name");
        }
        if (startDates.isEmpty()) 
        {
            if (json.has("date")) startDates.add(json.getInt("date"));
            else if (json.has("startDate")) startDates.add(json.getInt("startDate"));
            else if (json.has("start_date")) startDates.add(json.getInt("start_date"));
        }

        eventName = eventName.trim();
        if (eventName.isEmpty() || startDates.isEmpty()) 
        {
            return new DeleteEventInstanceReply(true, false);
        }

        // Verifica che l'evento appartenga all'organizzazione del configuratore
        String checkOrgQuery = "SELECT organization FROM events WHERE name = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkOrgQuery)) {
            checkStmt.setString(1, eventName);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    return new DeleteEventInstanceReply(true, false);
                }
                String org = rs.getString("organization");
                if (org == null || !org.equals(getOrganization())) {
                    return new DeleteEventInstanceReply(true, false);
                }
            }
        }

        String deleteSubscriptions = "DELETE FROM subscriptions WHERE name = ? AND date = ?";
        String deleteVoluntaries = "DELETE FROM eventsVoluntaries WHERE name = ? AND date = ?";
        String deleteData = "DELETE FROM eventsData WHERE name = ? AND start_date = ?";

        int totalDeleted = 0;
        for (int startDate : startDates) 
        {
            try (PreparedStatement stmt = connection.prepareStatement(deleteSubscriptions)) {
                stmt.setString(1, eventName);
                stmt.setInt(2, startDate);
                totalDeleted += stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(deleteVoluntaries)) {
                stmt.setString(1, eventName);
                stmt.setInt(2, startDate);
                totalDeleted += stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(deleteData)) {
                stmt.setString(1, eventName);
                stmt.setInt(2, startDate);
                totalDeleted += stmt.executeUpdate();
            }
        }

        return new DeleteEventInstanceReply(true, totalDeleted > 0);
    }
}
