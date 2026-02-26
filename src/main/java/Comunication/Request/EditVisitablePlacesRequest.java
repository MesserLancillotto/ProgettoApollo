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
        String visitType,
        String defaultVoluntary,
        String newDefauldVoluntary
    ) {
        super(ComunicationType.EDIT_VISITABLE_PLACES, userID, password);
        json.put("city", city);
        json.put("address", address);
        json.put("visitType", visitType);
        json.put("defaultVoluntary", defaultVoluntary);
        json.put("newDefauldVoluntary", newDefauldVoluntary);    
    }
}