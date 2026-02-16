package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Comunication.DatabaseObjects.Place;


public class PlaceTest 
{
    
    private static Event event;
    
    @Test
    void testPlaceGetters()
    {
        String city = "Brescia";
        String address = "Via Castello 9";
        String description = "Castello di Brescia";
        String organization = "San Genesio";

        List<String> visitTypes = new ArrayList<String>();
        visitTypes.add("Cinema all'aperto");
        List<String> voluntaries = new ArrayList<String>();
        voluntaries.add("Arlecchino.Valcalepio.99");

        Place place = new Place(
            city, address, description, 
            organization, visitTypes, voluntaries
        );

        JSONObject json = new JSONObject(place.toJSONString());

        assertEquals(city, place.getCity());
        assertEquals(address, place.getAddress());
        assertEquals(description, place.getDescription());
        assertEquals(organization, place.getOrganization());

        assertTrue(place.getVisitTypes().size() == visitTypes.size() && 
                  place.getVisitTypes().containsAll(visitTypes) && 
                  visitTypes.containsAll(place.getVisitTypes()));

        assertTrue(place.getVoluntaries().size() == voluntaries.size() && 
                  place.getVoluntaries().containsAll(voluntaries) && 
                  voluntaries.containsAll(place.getVoluntaries()));
    }

    @Test
    void testPlaceToJSON() 
    {
        String city = "Brescia";
        String address = "Via Castello 9";
        String description = "Castello di Brescia";
        String organization = "San Genesio";

        List<String> visitTypes = new ArrayList<String>();
        visitTypes.add("Cinema all'aperto");
        List<String> voluntaries = new ArrayList<String>();
        voluntaries.add("Arlecchino.Valcalepio.99");

        Place place = new Place(
            city, address, description, 
            organization, visitTypes, voluntaries
        );

        JSONObject json = new JSONObject(place.toJSONString());

        assertEquals(city, json.getString("city"));
        assertEquals(address, json.getString("address"));
        assertEquals(description, json.getString("description"));
        assertEquals(organization, json.getString("organization"));

        assertNotNull(json.getJSONArray("visitTypes"));
        assertNotNull(json.getJSONArray("voluntaries"));
    }
}