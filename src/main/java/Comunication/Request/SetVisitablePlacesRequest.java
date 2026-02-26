package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.DatabaseObjects.Place;
import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetVisitablePlacesRequest extends AuthenticatedRequest
{
    private JSONArray placesArray = new JSONArray();
    private List<Place> places;
    
    public SetVisitablePlacesRequest
    (
        String userID,
        String password
    ) {
        super(ComunicationType.SET_VISITABLE_PLACES, userID, password);
        this.places = new ArrayList<Place>();
    }

    public void addPlace(
        String city,
        String address,
        String description,
        String organization,
        String visitType,
        String defaultVoluntary
    ) {
        Place place = new Place(
            city,
            address,
            description,
            organization,
            visitType,
            defaultVoluntary
        );
        placesArray.put(place.getJSONObject());
    }

    public String toJSONString()
    {
        if(json.has("places"))
        {
            json.remove("places");
        }
        json.put("places", placesArray);
        return json.toString();
    }
}