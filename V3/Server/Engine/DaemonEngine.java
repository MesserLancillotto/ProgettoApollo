package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

public class DaemonEngine extends Engine
{

    public String handleRequest()
    {
        String query;
        PreparedStatement statement;
        int x;
        // chiudo prenotazioni tre gg prima
        query = """
            UPDATE events 
            SET 
                VOLUNTARIESCANSUBMIT = false, 
                USERSCANSUBMIT = false,
                state = CASE 
                    WHEN events.endDate < EXTRACT(EPOCH FROM CURRENT_TIMESTAMP()) THEN 'DONE'
                    WHEN COALESCE(user_counts.user_count, 0) >= events.maxUsers THEN 'COMPLETE'
                    WHEN COALESCE(user_counts.user_count, 0) < events.minUsers THEN 'DELETED'
                    ELSE 'CONFIRMED'
                END
            FROM events
            LEFT JOIN (
                SELECT event, COUNT(*) as user_count 
                FROM eventsUsers 
                GROUP BY event
            ) user_counts ON events.eventName = user_counts.event
            WHERE events.startDate <= (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP()) + 259200);
        """;
        try
        (
            Connection connection = connectDB(dbUrl, "sa", "");
        ) {
            statement = connection.prepareStatement(query);
            x = statement.executeUpdate();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
}