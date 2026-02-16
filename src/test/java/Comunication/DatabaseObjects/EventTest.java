package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Comunication.DatabaseObjects.Event;


public class EventTest 
{
    
    private static Event event;
    
    @Test
    void testEventToJSON() 
    {
        String name = "Cinema in castello";
        String description = "Proiezione pellicola \"La corazzata Potëmkin\" (Бронено́сец «Потёмкин»)";
        String type = "Cinema all'aperto";
        String organization = "San Genesio"; 
        String city = "Brescia";
        String address = "Via Castello 9";
        String rendezvous = "Ingresso principale sotto il leone di San Marco";
        String state = "CONFIRMED";
        
        List<String> voluntaries = new ArrayList<String>();
        voluntaries.add("Arlecchino.Valcalepio.99");
        voluntaries.add("Pulcinella.Qualcosa.98");
        
        List<List<Integer>> singleEvent = new ArrayList<>();
        List<Integer> innerList = new ArrayList<>();
        innerList.add(110101);
        innerList.add(111101);
        singleEvent.add(innerList);
        
        Event event = new Event(
            name, description, type, organization, city, 
            address, rendezvous, state, voluntaries, singleEvent
        );
    
        JSONObject json = new JSONObject(event.toJSONString());
        
        assertEquals(name, json.getString("name"));
        assertEquals(description, json.getString("description"));
        assertEquals(type, json.getString("type"));
        assertEquals(organization, json.getString("organization"));
        assertEquals(city, json.getString("city"));
        assertEquals(address, json.getString("address"));
        assertEquals(rendezvous, json.getString("rendezvous"));
        assertEquals(state, json.getString("state"));
    
        assertNotNull(json.getJSONArray("voluntaries"));
        assertNotNull(json.getJSONArray("singleEvent"));
    }
}