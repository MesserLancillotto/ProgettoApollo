package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EventTest 
{
    @Test
    void testGetJSONObject() 
    {
        String name = "Cinema in castello";
        String description = "Rassegna cinematografica nel castello di Desenzano.";
        String visitType = "Cinema";
        String organization = "San Genesio";
        String city = "Desenzano";
        String address = "Via Castello 63";
        String rendezvous = "Ingresso principale sull'ex ponte levatoio.";

        List<String> voluntaries = List.of("Arlecchino.Valcalepio.89");
        List<String> users = List.of("Renzo.Tramaglino.94");
        
        EventInstance instance = new EventInstance(1772830800, 1772838000, "CONFIRMED", voluntaries, users);
        List<EventInstance> instances = List.of(instance);

        Event event = new Event(name, description, visitType, organization, city, address, rendezvous, instances);
        JSONObject json = event.getJSONObject();

        assertNotNull(json);
        assertEquals(name, json.getString("name"));
        assertEquals(description, json.getString("description"));
        assertEquals(visitType, json.getString("type"));
        assertEquals(visitType, json.getString("visitType"));
        assertEquals(organization, json.getString("organization"));
        assertEquals(city, json.getString("city"));
        assertEquals(address, json.getString("address"));
        assertEquals(rendezvous, json.getString("rendezvous"));

        JSONArray instancesJSON = json.getJSONArray("instances");
        assertEquals(1, instancesJSON.length());
        
        JSONObject instanceJSON = instancesJSON.getJSONObject(0);
        assertEquals(1772830800, instanceJSON.getInt("start_date"));
        assertEquals("CONFIRMED", instanceJSON.getString("state"));
    }

    @Test
    void testToJSONStringAndCaching() 
    {
        String name = "Teatro in rocca";

        Event event = new Event(
            name, "Rassegna teatrale", "Teatro", 
            "San Genesio", "Lonato", "Via Rocca 2", 
            "Ingresso principale", new ArrayList<>()
        );

        // controlla l'implementazione pigra
        
        String jsonStringFirstCall = event.toJSONString();
        String jsonStringSecondCall = event.toJSONString();

        assertNotNull(jsonStringFirstCall);
        assertEquals(jsonStringFirstCall, jsonStringSecondCall);

        JSONObject json = new JSONObject(jsonStringFirstCall);
        assertEquals(name, json.getString("name"));
        assertEquals(0, json.getJSONArray("instances").length());
    }
}