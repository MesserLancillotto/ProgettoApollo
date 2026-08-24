package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlaceTest 
{
    @Test
    void testPlaceGetters()
    {
        String city = "Brescia";
        String address = "Via Castello 9";
        String description = "Castello di Brescia";
        String organization = "San Genesio";
        String visitType = "Cinema all'aperto";
        String defaultVoluntary = "Arlecchino.Valcalepio.99";

        Place place = new Place(
            city, address, description, 
            organization, visitType, defaultVoluntary
        );

        assertEquals(city, place.getCity());
        assertEquals(address, place.getAddress());
        assertEquals(description, place.getDescription());
        assertEquals(organization, place.getOrganization());
        assertEquals(visitType, place.getVisitType());
        assertEquals(defaultVoluntary, place.getDefaultVoluntary());
    }

    @Test
    void testPlaceToJSONString() 
    {
        String city = "Brescia";
        String address = "Via Castello 9";
        String description = "Castello di Brescia";
        String organization = "San Genesio";
        String visitType = "Cinema all'aperto";
        String defaultVoluntary = "Arlecchino.Valcalepio.99";

        Place place = new Place(
            city, address, description, 
            organization, visitType, defaultVoluntary
        );

        JSONObject json = new JSONObject(place.toJSONString());

        assertEquals(city, json.getString("city"));
        assertEquals(address, json.getString("address"));
        assertEquals(description, json.getString("description"));
        assertEquals(organization, json.getString("organization"));
        assertEquals(visitType, json.getString("visitType"));
        assertEquals(defaultVoluntary, json.getString("defaultVoluntary"));
    }
}