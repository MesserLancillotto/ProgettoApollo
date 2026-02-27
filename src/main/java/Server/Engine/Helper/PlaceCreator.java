package Server.Engine.Helper;

import Comunication.ComunicationType.*;
import Comunication.DatabaseObjects.*;

import java.sql.*;
import java.util.*;

public class PlaceCreator
{
    private static final String QUERY = """
        SELECT description, userID, organization
        FROM places
        WHERE city = ?
        AND address = ?
        AND visitType = ?
    """;

    public static Place createPlace(
        Connection connection, 
        String city,
        String address,
        String visitType
    ) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(QUERY);
        statement.setString(1, city);
        statement.setString(2, address);
        statement.setString(3, visitType);
        ResultSet result = statement.executeQuery();

        if(!result.next())
        {
            return new Place();
        }

        return new Place(
            city, address, visitType,
            result.getString("description"),
            result.getString("organization"),
            result.getString("userID")
        );
    }
}