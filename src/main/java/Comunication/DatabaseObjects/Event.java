package Comunication.DatabaseObjects;

import org.json.*;
import java.util.*;

public class Event 
{   
/**
* @param name
* @param description
* @param type
* @param organization
* @param city
* @param address
* @param rendezvous
* @param state
* @param voluntaries lista di stringhe con lo userID dei volontari per 
* l'evento.
* @param singleEvent indica le singole istanze dell'evento. La sua lunghezza è 
* quella del numero di volte che si ripeterà quell'evento e le sottoliste
* saranno tutte di dimensione 2, il primo valore è l'inizio ed il secondo la
* fine, entrambi usando gli UNIX Epoch timestamp IN SECONDI NO MILLISECONDI.
* Esempio proiezione di un film "NOME DEL FILM" per tre serate. La prima sera
* in data 01/01/2000 21:00 - 23:00, la seconda sera in data 02/01/2000 20:00
* - 22:00 e la terza 03/01/2000 19:00 - 21:00. Convertite le date in UNIX 
* Epoch timestamp, inizio1 = 946760400, fine1 = 946767600, inizio2 = ...
* allora singleEvent apparirà come
* {{inizio1, fine1}, {inizio2, fine2}, {inizio3, fine3}}
*/

    private static final int MAX_VOLUNTARIES = 100;

    private String name;
    private String description;
    private String type;
    private String organization;
    private String city;
    private String address;
    private String rendezvous;
    private String state;
    private List<String> voluntaries;
    private List<List<Integer>> singleEvent;

     public Event(
        String name,
        String description,
        String type,
        String organization,
        String city,
        String address,
        String rendezvous,
        String state,
        List<String> voluntaries,
        List<List<Integer>> singleEvent
    ) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.organization = organization;
        this.city = city;
        this.address = address;
        this.rendezvous = rendezvous;
        this.state = state;
        this.voluntaries = List.copyOf(voluntaries);
        this.singleEvent = List.copyOf(singleEvent);
    }

    public String toJSONString() {

    /**
     * Partendo dai dati del costruttore crea un JSON
     * @return Stringa JSON
    */

        JSONObject json = new JSONObject();
        
        json.put("name", name);
        json.put("description", description);
        json.put("type", type);
        json.put("organization", organization);
        json.put("city", city);
        json.put("address", address);
        json.put("rendezvous", rendezvous);
        json.put("state", state);
        
        JSONArray voluntariesArray = new JSONArray();
        for(int i = 0; i < voluntaries.size() && i < MAX_VOLUNTARIES; i++) 
        {
            voluntariesArray.put(voluntaries.get(i));
        }
        json.put("voluntaries", voluntariesArray);
        
        JSONArray singleEventArray = new JSONArray();
        for(List<Integer> event : singleEvent) 
        {
            JSONArray eventArray = new JSONArray();
            for(Integer value : event) 
            {
                eventArray.put(value);
            }
            singleEventArray.put(eventArray);
        }
        json.put("singleEvent", singleEventArray);
        
        return json.toString();
    }
}