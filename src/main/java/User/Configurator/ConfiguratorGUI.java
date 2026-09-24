package User.Configurator;

import User.Interfaces.*;
import User.Configurator.*;
import org.json.JSONObject;

public class ConfiguratorGUI extends AbstractConfiguratorGUI {

    public ConfiguratorGUI() {
        configMap.put(
            "DeletePlaceRequest", 
            new ActionConfig(
                "Elimina posto", 
                "Elimina il posto selezionato", 
                this::deletePlaceRequest));
        configMap.put(
            "DeleteVisitTypeFromPlaceRequest", 
            new ActionConfig(
                "Elimina tipo di visita", 
                "Elimina il tipo di visita selezionato da un posto specifico", 
                this::deleteVisitTypeFromPlaceRequest));
        configMap.put(
            "DeleteVoluntaryRequest", 
            new ActionConfig(
                "Elimina utente", 
                "Elimina l'utente selezionato dal pool di volontari", 
                this::deleteVoluntaryRequest));
        configMap.put(
            "EditVisitablePlacesRequest", 
            new ActionConfig(
                "Modifica posto visitabile", 
                "Modifica le informazioni del posto visitabile selezionato", 
                this::editVisitablePlaceRequest));
        configMap.put(
            "GetEventRequest", 
            new ActionConfig(
                "Richiesta Evento", 
                "Ottiene le informazioni sull'evento dato lo stato specificato", 
                this::getEventRequest));
        
        
        configMap.put(
            "GetMaximumFriendsRequest", 
            new ActionConfig(
                "Numero massimo di amici", 
                "Ottiene il numero massimo di amici per un'associazione", 
                this::getMaximumFriendsRequest));
        configMap.put(
            "GetPersonalDataRequest", 
            new ActionConfig(
                "Dati personali", 
                "Ottiene le informazioni sull'utente", 
                this::getPersonalDataRequest));
        configMap.put(
            "GetPlacesRequest", 
            new ActionConfig(
                "Dati posto", 
                "Ottiene le informazioni sul posto specificato ", 
                this::getPlacesRequest));
        configMap.put(
            "GetPossibleVisitsRequest", 
            new ActionConfig(
                "Visite organizzazione", 
                "Ottiene le informazioni sul posto specificato ", 
                this::getPossibleVisitsRequest));
}

    private void deletePlaceRequest() 
    {
        System.out.println("deletePlaceRequest()");
        UserViewInterface userView = new DeletePlaceRequestGUI();
        userView.paint(new JSONObject());
    }

    private void deleteVisitTypeFromPlaceRequest() {
        System.out.println("Cancella tipo di visita dal posto");
        UserViewInterface userView = new DeleteVisitTypeFromPlaceGUI();
        userView.paint(new JSONObject());
    }

    private void deleteVoluntaryRequest() {
        System.out.println("deleteVoluntaryRequest()");
        System.out.println("Cancella utente");
        UserViewInterface userView = new DeleteVoluntaryGUI();
        userView.paint(new JSONObject());
    }

    private void editVisitablePlaceRequest() {
        System.out.println("editVisitablePlaceRequest()");
        System.out.println("Modifica posto visitabile");
        UserViewInterface userView = new EditVisitablesPlacesGUI();
        userView.paint(new JSONObject());
    }

    private void getEventRequest() {
        System.out.println("getEventRequest()");
        System.out.println("Richiesta evento");
        UserViewInterface userView = new GetEventGUI();
        userView.paint(new JSONObject());
    }

    private void getMaximumFriendsRequest() {
        System.out.println("getMaximumFriendsRequest()");
        System.out.println("Richiesta numero amici invitabili");
        UserViewInterface userView = new GetMaximumFriendsGUI();
        userView.paint(new JSONObject());
    }

    private void getPersonalDataRequest() {
        System.out.println("getMaximumFriendsRequest()");
        System.out.println("Richiesta numero amici invitabili");
        UserViewInterface userView = new GetMaximumFriendsGUI();
        userView.paint(new JSONObject());
    }

    private void getPlacesRequest() {
        System.out.println("getMaximumFriendsRequest()");
        System.out.println("Richiesta numero amici invitabili");
        UserViewInterface userView = new GetMaximumFriendsGUI();
        userView.paint(new JSONObject());
    }

    private void getPossibleVisitsRequest() {
        System.out.println("getMaximumFriendsRequest()");
        System.out.println("Richiesta numero amici invitabili");
        UserViewInterface userView = new GetMaximumFriendsGUI();
        userView.paint(new JSONObject());
    }
}