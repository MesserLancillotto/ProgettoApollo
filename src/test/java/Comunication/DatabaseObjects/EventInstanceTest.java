package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EventInstanceTest 
{
    @Test
    void testEventGetJSONObject() 
    {
        Integer start_date = 1772830800;
        Integer end_date = 1772838000;
        String state = "CONFIRMED";
        
        List<String> voluntaries = new ArrayList<>();
        voluntaries.add("Arlecchino.Valcalepio.89");
        voluntaries.add("Colombina.Lison.98");
        
        List<String> users = new ArrayList<>();
        users.add("Paolo.Malatesta.82");
        users.add("Francesca.Polenta.89");
        
        EventInstance eventInstance = new EventInstance(
            start_date, end_date, state, voluntaries, users);
        
        JSONObject json = eventInstance.getJSONObject();

        assertEquals(start_date, json.getInt("start_date"));
        assertEquals(end_date, json.getInt("end_date"));
        assertEquals(state, json.getString("state"));
    }

    @Test
    void testEventToJSONString() 
    {
        Integer start_date = 1772830800;
        Integer end_date = 1772838000;
        String state = "CONFIRMED";
        
        List<String> voluntaries = new ArrayList<>();
        voluntaries.add("Arlecchino.Valcalepio.89");
        voluntaries.add("Colombina.Lison.98");
        
        List<String> users = new ArrayList<>();
        users.add("Paolo.Malatesta.82");
        users.add("Francesca.Polenta.89");
        
        EventInstance eventInstance = new EventInstance(
            start_date, end_date, state, voluntaries, users);
        
        JSONObject json = new JSONObject(eventInstance.toJSONString());

        assertEquals(start_date, json.getInt("start_date"));
        assertEquals(end_date, json.getInt("end_date"));
        assertEquals(state, json.getString("state"));
    }
}