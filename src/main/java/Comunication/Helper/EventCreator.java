package Helper;

import Comunication.ComunicationType.*;
import Comunication.DatabaseObjects.*;

import java.sql.*;
import java.util.*;

public class EventCreator
{
    private static final String QUERY = """
        SELECT DISTINCT 
            e.name,
            ed.start_date, 
            ed.end_date 
        FROM events e
        JOIN subscriptions ON e.name = subscriptions.name
        JOIN eventsData ed ON ed.name = e.name
        WHERE subscriptions.userID = ? ;
    """;
    public static Event createEventFromResultSet(
        Connection connection, 
        ResultSet result,
        String targetID
    ) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, targetID);
        ResultSet eventsResult = statement.executeQuery();
        List<List<Integer>> singleEvent = new ArrayList<>();
        while (eventsResult.next()) 
        {
            List<Integer> datePair = new ArrayList<>();
            datePair.add(eventsResult.getInt("start_date"));
            datePair.add(eventsResult.getInt("end_date"));
            singleEvent.add(datePair);
        }
        return new Event(
            result.getString("name"),
            result.getString("description"),
            result.getString("visitType"),
            result.getString("organization"),
            result.getString("city"),
            result.getString("address"),
            result.getString("rendezvous"),
            result.getString("state"),
            new ArrayList<String>(),
            singleEvent
        );
    }
}