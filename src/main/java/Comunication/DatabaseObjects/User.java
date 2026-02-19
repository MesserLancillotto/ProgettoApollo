package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import java.sql.*;

import Comunication.DatabaseObjects.UserRole;


public class User 
{
    private String userID;
    private String name;
    private String surname;
    private String city;
    private Integer birth_dd;
    private Integer birth_mm;
    private Integer birth_yy;
    private Integer user_since;
    private UserRole role;
    private Boolean changePasswordDue;
    private String organization;

    private List<List<Integer>> disponibilities = new ArrayList<>();
    private List<String> allowedVisits = new ArrayList<String>();
    private List<String> voluntaryEventName = new ArrayList<String>();
    private List<Integer> voluntaryEventDate = new ArrayList<Integer>();

    private JSONObject json;

    public User
    (
        String userID,
        String name,
        String surname,
        String city,
        Integer birth_dd,
        Integer birth_mm,
        Integer birth_yy,
        Integer user_since,
        UserRole role,
        Boolean changePasswordDue,
        String organization,
        List<List<Integer>> disponibilities,
        List<String> allowedVisits,
        List<String> voluntaryEventName,
        List<Integer> voluntaryEventDate
    ) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.birth_dd = birth_dd;
        this.birth_mm = birth_mm;
        this.birth_yy = birth_yy;
        this.user_since = user_since;
        this.role = role;
        this.changePasswordDue = changePasswordDue;
        this.organization = organization;
        this.disponibilities = new ArrayList<>(disponibilities);
        this.allowedVisits = new ArrayList<>(allowedVisits);
        this.voluntaryEventName = new ArrayList<>(voluntaryEventName);
        this.voluntaryEventDate = new ArrayList<>(voluntaryEventDate);
    }

    public User
    (
        String data
    ) {
        this.json = new JSONObject(data);

        this.userID = json.getString("userID");
        this.name = json.getString("name");
        this.surname = json.getString("surname");
        this.city = json.getString("city");
        this.birth_dd = json.getInt("birth_dd");
        this.birth_mm = json.getInt("birth_mm");
        this.birth_yy = json.getInt("birth_yy");
        this.user_since = json.getInt("user_since");
        this.role = UserRole.valueOf(json.getString("role"));
        this.changePasswordDue = json.getBoolean("changePasswordDue");
        this.organization = json.getString("organization");
    }

    public JSONObject toJSONObject()
    {
        if(this.json != null)
        {
            return this.json;
        }
        JSONObject json = new JSONObject();
        json.put("userID", userID);
        json.put("name", name);
        json.put("surname", surname);
        json.put("city", city);
        json.put("birth_dd", birth_dd);
        json.put("birth_mm", birth_mm);
        json.put("birth_yy", birth_yy);
        json.put("user_since", user_since);
        json.put("role", role);
        json.put("changePasswordDue", changePasswordDue);
        json.put("organization", organization);

        JSONArray disponibilitiesJSON = new JSONArray();
        for(List<Integer> interval : disponibilities)
        {
            JSONArray inner = new JSONArray();
            inner.put(interval.get(0));
            inner.put(interval.get(1));
            disponibilitiesJSON.put(inner);
        }
        json.put("disponibilities", disponibilitiesJSON);

        JSONArray allowedVisitsJSON = new JSONArray();
        for(String name : allowedVisits)
        {
            allowedVisitsJSON.put(name);
        }
        json.put("allowedVisits", allowedVisitsJSON);

        JSONArray voluntaryEventNameJSON = new JSONArray();
        for(String name : voluntaryEventName)
        {
            voluntaryEventNameJSON.put(name);
        }
        json.put("voluntaryEventName", voluntaryEventNameJSON);

        JSONArray voluntaryEventDateJSON = new JSONArray();
        for(Integer date : voluntaryEventDate)
        {
            voluntaryEventDateJSON.put(date);
        }
        json.put("voluntaryEventDate", voluntaryEventDateJSON);

        return json;
    }

    public String toJSONString()
    {
        return toJSONObject().toString();
    }

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