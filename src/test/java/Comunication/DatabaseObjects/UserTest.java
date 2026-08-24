package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserTest 
{
    @Test
    void testDefaultConstructor() 
    {
        User user = new User();
        JSONObject json = user.getJSONObject();

        assertNotNull(json);
        assertEquals("NONE", json.getString("userID"));
        assertEquals("{\"userID\":\"NONE\"}", user.toJSONString());
    }

    @Test
    void testFullConstructorAndJSONStructure() 
    {
        String userID = "Arlecchino.Valcalepio.89";
        String name = "Arlecchino";
        String surname = "Valcalepio";
        String city = "Desenzano";
        Integer birthDd = 15;
        Integer birthMm = 10;
        Integer birthYy = 1989;
        Integer userSince = 1767778200;
        UserRole role = UserRole.VOLUNTARY;
        Boolean changePasswordDue = false;
        String organization = "San Genesio";

        List<String> allowedVisits = List.of("Cinema");

        List<List<Integer>> disponibilities = List.of(
            List.of(1772830800, 1772838000),
            List.of(1772917200, 1772924400)
        );

        Event event = new Event(
            "Cinema in castello",
            "Rassegna cinematografica nel castello di Desenzano.",
            "Cinema",
            "San Genesio",
            "Desenzano",
            "Via Castello 63",
            "Ingresso principale sull'ex ponte levatoio.",
            new ArrayList<>()
        );
        List<Event> voluntaryEvents = List.of(event);

        User user = new User(
            userID, name, surname, city,
            birthDd, birthMm, birthYy, userSince,
            role, changePasswordDue, organization,
            allowedVisits, disponibilities, voluntaryEvents
        );

        JSONObject json = user.getJSONObject();

        assertEquals(userID, json.getString("userID"));
        assertEquals(name, json.getString("name"));
        assertEquals(surname, json.getString("surname"));
        assertEquals(city, json.getString("city"));
        assertEquals(birthDd, json.getInt("birth_dd"));
        assertEquals(birthMm, json.getInt("birth_mm"));
        assertEquals(birthYy, json.getInt("birth_yy"));
        assertEquals(userSince, json.getInt("user_since"));
        assertEquals(role, json.get("role"));
        assertEquals(changePasswordDue, json.getBoolean("changePasswordDue"));
        assertEquals(organization, json.getString("organization"));

        // Verifica disponibilità (array nidificato)
        JSONArray dispJSON = json.getJSONArray("disponibilities");
        assertEquals(2, dispJSON.length());
        assertEquals(1772830800, dispJSON.getJSONArray(0).getInt(0));
        assertEquals(1772838000, dispJSON.getJSONArray(0).getInt(1));

        // Verifica mapping eventi su chiave allowedVisits
        JSONArray allowedVisitsJSON = json.getJSONArray("allowedVisits");
        assertEquals(1, allowedVisitsJSON.length());
        assertEquals("Cinema in castello", allowedVisitsJSON.getJSONObject(0).getString("name"));
    }

    @Test
    void testToJSONString() 
    {
        User user = new User();
        String jsonString = user.toJSONString();
        
        assertNotNull(jsonString);
        JSONObject parsed = new JSONObject(jsonString);
        assertEquals("NONE", parsed.getString("userID"));
    }
}