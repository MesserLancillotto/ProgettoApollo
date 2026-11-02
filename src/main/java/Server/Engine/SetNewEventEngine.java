package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.SetNewEventReply;

public class SetNewEventEngine extends AuthenticatedEngine
{
    public SetNewEventEngine(String data) 
    {
        super(data);
    }

    @Override
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        if (!petitionerIsConfigurator()) 
        {
            return new SetNewEventReply(false, false);
        }

        String org = getOrganization();
        if (org == null || org.isEmpty()) 
        {
            return new SetNewEventReply(true, false);
        }

        JSONObject eventObj = json.optJSONObject("event");
        if (eventObj == null) 
        {
            eventObj = json;
        }

        String name = eventObj.optString("name", "").trim();
        if (name.isEmpty()) 
        {
            return new SetNewEventReply(true, false);
        }

        String description = eventObj.optString("description", "");
        String visitType = eventObj.optString("visitType", eventObj.optString("type", ""));
        String city = eventObj.optString("city", "");
        String address = eventObj.optString("address", "");
        String rendezvous = eventObj.optString("rendezvous", "");

        // Verifica che l'evento non esista già
        String checkEventQuery = "SELECT 1 FROM events WHERE name = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkEventQuery)) {
            checkStmt.setString(1, name);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    return new SetNewEventReply(true, false);
                }
            }
        }

        String insertEventQuery = """
            INSERT INTO events (name, description, visitType, organization, city, address, rendezvous)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement insertStmt = connection.prepareStatement(insertEventQuery)) {
            insertStmt.setString(1, name);
            insertStmt.setString(2, description);
            insertStmt.setString(3, visitType);
            insertStmt.setString(4, org);
            insertStmt.setString(5, city);
            insertStmt.setString(6, address);
            insertStmt.setString(7, rendezvous);
            insertStmt.executeUpdate();
        }

        JSONArray instancesArray = eventObj.optJSONArray("instances");
        if (instancesArray != null && instancesArray.length() > 0) 
        {
            String insertDataQuery = """
                INSERT INTO eventsData (name, start_date, end_date, state)
                VALUES (?, ?, ?, ?)
            """;
            String insertVoluntaryQuery = """
                INSERT INTO eventsVoluntaries (name, userID, date)
                VALUES (?, ?, ?)
            """;
            String insertSubscriptionQuery = """
                INSERT INTO subscriptions (userID, name, date)
                VALUES (?, ?, ?)
            """;

            for (int i = 0; i < instancesArray.length(); i++) 
            {
                JSONObject instanceObj = instancesArray.getJSONObject(i);
                int startDate = instanceObj.optInt("start_date", 0);
                int endDate = instanceObj.optInt("end_date", startDate);
                String state = instanceObj.optString("state", "PROPOSED");

                if (startDate > 0) {
                    try (PreparedStatement dataStmt = connection.prepareStatement(insertDataQuery)) {
                        dataStmt.setString(1, name);
                        dataStmt.setInt(2, startDate);
                        dataStmt.setInt(3, endDate);
                        dataStmt.setString(4, state);
                        dataStmt.executeUpdate();
                    }

                    JSONArray voluntaries = instanceObj.optJSONArray("voluntaries");
                    if (voluntaries != null) {
                        for (int v = 0; v < voluntaries.length(); v++) {
                            String volID = voluntaries.getString(v);
                            if (volID != null && !volID.isEmpty()) {
                                try (PreparedStatement volStmt = connection.prepareStatement(insertVoluntaryQuery)) {
                                    volStmt.setString(1, name);
                                    volStmt.setString(2, volID);
                                    volStmt.setInt(3, startDate);
                                    volStmt.executeUpdate();
                                }
                            }
                        }
                    }

                    JSONArray users = instanceObj.optJSONArray("users");
                    if (users != null) {
                        for (int u = 0; u < users.length(); u++) {
                            String uID = users.getString(u);
                            if (uID != null && !uID.isEmpty()) {
                                try (PreparedStatement subStmt = connection.prepareStatement(insertSubscriptionQuery)) {
                                    subStmt.setString(1, uID);
                                    subStmt.setString(2, name);
                                    subStmt.setInt(3, startDate);
                                    subStmt.executeUpdate();
                                }
                            }
                        }
                    }
                }
            }
        }
        else 
        {
            int startDate = eventObj.optInt("startDate", eventObj.optInt("start_date", 0));
            int endDate = eventObj.optInt("endDate", eventObj.optInt("end_date", startDate));
            String state = eventObj.optString("state", "CONFIRMED");
            if (startDate > 0) {
                String insertDataQuery = """
                    INSERT INTO eventsData (name, start_date, end_date, state)
                    VALUES (?, ?, ?, ?)
                """;
                try (PreparedStatement dataStmt = connection.prepareStatement(insertDataQuery)) {
                    dataStmt.setString(1, name);
                    dataStmt.setInt(2, startDate);
                    dataStmt.setInt(3, endDate);
                    dataStmt.setString(4, state);
                    dataStmt.executeUpdate();
                }
            }
        }

        return new SetNewEventReply(true, true);
    }
}
