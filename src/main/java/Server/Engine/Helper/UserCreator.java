package Server.Engine.Helper;

import Comunication.ComunicationType.*;
import Comunication.DatabaseObjects.*;

import java.sql.*;
import java.util.*;

public class UserCreator
{
    private static final String QUERY = """
        SELECT userName, userSurname, city, birth_dd, birth_mm, 
        birth_yy, userSince, organization, role, changePasswordDue 
        FROM users 
        WHERE userID = ? 
    """;
    private static final String ALLOWED_VISITS_QUERY = """
        SELECT visitType 
        FROM userPermissions
        WHERE userID = ?
    """;
    private static final String DISPONIBILITIES_QUERY = """
        SELECT start_date, end_date 
        FROM voluntaryDisponibilities
        WHERE userID = ?
    """;
    private static final String EVENTS_VOLUNTARIES_QUERY = """
        SELECT name, date 
        FROM eventsVoluntaries
        WHERE userID = ?
    """;

    public static User createUserFromID(
        Connection connection, 
        String userID
    ) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, userID);
        ResultSet result = statement.executeQuery();

        if(!result.next())
        {
            return new User();
        }

        statement = connection.prepareStatement(ALLOWED_VISITS_QUERY);
        statement.setString(1, userID);
        ResultSet allowedVisitsResult = statement.executeQuery();

        List<String> allowedVisits = new ArrayList<String>();
        while(allowedVisitsResult.next())
        {
            allowedVisits.add(allowedVisitsResult.getString("visitType"));
        } 

        statement = connection.prepareStatement(DISPONIBILITIES_QUERY);
        statement.setString(1, userID);
        ResultSet disponibilitiesResult = statement.executeQuery();

        List<List<Integer>> disponibilities = new ArrayList<>();
        while(disponibilitiesResult.next())
        {
            List<Integer> inner = new ArrayList<Integer>();
            inner.add(disponibilitiesResult.getInt("start_date"));
            inner.add(disponibilitiesResult.getInt("end_date"));
            disponibilities.add(inner);
        }

        return new User(
            userID,
            result.getString("userName"),
            result.getString("userSurname"),
            result.getString("city"),
            result.getInt("birth_dd"),
            result.getInt("birth_mm"),
            result.getInt("birth_yy"),
            result.getInt("userSince"),
            UserRole.valueOf(result.getString("role")),
            result.getBoolean("changePasswordDue"),
            result.getString("organization"),
            allowedVisits,
            disponibilities,
            new ArrayList<Event>()
        );
    }
}