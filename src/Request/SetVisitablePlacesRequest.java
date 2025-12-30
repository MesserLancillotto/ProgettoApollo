package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.DatabaseObjects.Place;
import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class SetVisitablePlacesRequest extends AuthenticatedRequest
{
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
        List<String> visitTypes,
        List<String> voluntaries
    ) {
        Place place = new Place(
            city, 
            address, 
            description, 
            organization, 
            visitTypes, 
            voluntaries
        );
        places.add(place);
    }

    public String toJSONString()
    {
        JSONArray placesArray = new JSONArray();
        for(Place p : places)
        {
            placesArray.put(new JSONObject(p.toJSONString()));
        }
        json.put("places", placesArray);
        return json.toString();
    }
}