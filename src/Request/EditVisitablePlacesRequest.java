package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.DatabaseObjects.Place;
import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 

public class EditVisitablePlacesRequest extends AuthenticatedRequest
{   
    private static final int MAX_EDIT_SIZE = 50;

    private String city;
    private String address;
    private List<String> visitTypes;
    private List<String> userIDs;
    
    /**
     * Classe per la richiesta di modifica di un posto: la tupla 
     *      (city, address) indica univocamente un posto
     * @param city la città
     * @param address l'indirizzo
     * @param visitTypes ogni valore della lista è un tipo di visita da 
     *      aggiungere
     * @param userIDs l'i-esimo valore di questa lista corrisponde al 
     *      volontario assegnato all'i-esimo tipo di visita di visitTypes
     * visitTypes e userIDs devono essere della stessa dimensione
    */
    public EditVisitablePlacesRequest
    (
        String userID,
        String password,
        String city,
        String address,
        List<String> visitTypes,
        List<String> userIDs
    ) {
        super(ComunicationType.EDIT_VISITABLE_PLACES, userID, password);
        this.city = city;
        this.address = address;
        this.visitTypes = new ArrayList<String>(visitTypes);
        this.userIDs = new ArrayList<String>(userIDs);
    }

    /**
     * Classe che ritorna una (String) json
     * Le chiavi sono 
     * @param visitTypes i tipi di visita
     * @param userIDs gli ID degli utenti
     * @see EditVisitablePlacesRequest
     */

    public String toJSONString()
    {
        JSONArray visitTypesArray = new JSONArray();
        JSONArray userIDsArray = new JSONArray();
        int arrayLength = (
            visitTypes.length() < userIDs.length() ? 
            visitTypes.length() : userIDs.length());

        for(
            int i = 0; 
            i < arrayLength 
                && i < MAX_EDIT_SIZE; 
            i++
        ) {
            visitTypesArray.put(visitTypes.get(i));
            userIDsArray.put(userIDs.get(i));
        }
        json.put("visitTypes", visitTypesArray);
        json.put("userIDs", userIDsArray);
        return json.toString();
    }
}