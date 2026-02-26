package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Comunication.DatabaseObjects.*;

public class EventInstanceTest 
{
    
    private static EventInstanceTest event;
    
    @Test
    void testEventToJSON() 
    {
        Integer start_date = 1772830800;
        Integer end_date = 1772838000;
        String state = "CONFIRMED";
        
        List<String> voluntaries = new ArrayList<>();
        voluntaries.put("Arlecchino.Valcalepio.89");
        voluntaries.put("Colombina.Lison.98")
        
        List<String> users = new ArrayList<>();
        users.put("Paolo.Malatesta.82");
        users.put("Francesca.Polenta.89");
        
        EventInstance eventInstance = new EventInstance(
            start_date, end_date, state, voluntaries, users);
        
        JSONObject json = new JSONObject(eventInstance.toJSONString());

        assertEquals(start_date, json.getInt("start_date"));
        assertEquals(end_date, json.getInt("end_date"));
        assertEquals(state, json.getString("state"));

    }
}