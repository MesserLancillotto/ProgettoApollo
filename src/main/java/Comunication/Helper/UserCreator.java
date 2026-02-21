package Helper;

import Comunication.ComunicationType.*;
import Comunication.DatabaseObjects.*;

import java.sql.*;
import java.util.*;

public class UserCreator
{
    public static User createUserFromResultSet(
        Connection connection, 
        ResultSet result
    ) throws SQLException {
        List<List<Integer>> disponibilities = new ArrayList<>();
        List<String> allowedVisits = new ArrayList<String>();
        List<String> voluntaryEventName = new ArrayList<String>();
        List<Integer> voluntaryEventDate = new ArrayList<Integer>();

        String disponibilityQuery = """
            SELECT start_date, end_date FROM VOLUNTARYDISPONIBILITIES
            WHERE userID = ? ;
        """;

        PreparedStatement disponibilityStatement 
            = connection.prepareStatement(disponibilityQuery);
        disponibilityStatement.setString(1, result.getString("userID"));
        ResultSet disponibilityResult 
            = disponibilityStatement.executeQuery();

        while(disponibilityResult.next())
        {
            List<Integer> inner = new ArrayList<Integer>();

            inner.add(disponibilityResult.getInt("start_date"));
            inner.add(disponibilityResult.getInt("end_date"));

            disponibilities.add(inner);
        }

        String allowedVisitsQuery = """
            SELECT visitType FROM USERPERMISSIONS
            WHERE userID = ? ;
        """;

        PreparedStatement allowedVisitsStatement 
            = connection.prepareStatement(allowedVisitsQuery);
        allowedVisitsStatement.setString(1, result.getString("userID"));
        ResultSet allowedVisitsResult 
            = allowedVisitsStatement.executeQuery();

        while(allowedVisitsResult.next())
        {
            allowedVisits.add(allowedVisitsResult.getString("visitType"));
        }

        String eventVoluntaryQuery = """
            SELECT name, date FROM EVENTSVOLUNTARIES
            WHERE userID = ? ;
        """; 

        PreparedStatement eventVoluntaryStatement 
            = connection.prepareStatement(eventVoluntaryQuery);
        eventVoluntaryStatement.setString(1, result.getString("userID"));
        ResultSet eventVoluntaryResult 
            = eventVoluntaryStatement.executeQuery();

        while(eventVoluntaryResult.next())
        {
            voluntaryEventName.add(eventVoluntaryResult.getString("name"));
            voluntaryEventDate.add(eventVoluntaryResult.getInt("date"));
        }

        return new User(
            result.getString("userID"),
            result.getString("userName"),
            result.getString("userSurname"),
            result.getString("city"),
            result.getInt("birth_dd"),
            result.getInt("birth_mm"),
            result.getInt("birth_yy"),
            result.getInt("user_since"),
            UserRole.valueOf(result.getString("role")),
            result.getBoolean("changePasswordDue"),
            result.getString("organization"),
            disponibilities,
            allowedVisits,
            voluntaryEventName,
            voluntaryEventDate
        );
    }
}