package User.Configurator;

import User.SetNewPasswordGUI;
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
                "Ottiene i tipi di visita possibili per un'organizzazione", 
                this::getPossibleVisitsRequest));
        configMap.put(
            "GetVoluntariesRequest", 
            new ActionConfig(
                "Visualizza volontari", 
                "Visualizza e filtra l'elenco dei volontari", 
                this::getVoluntariesRequest));
        configMap.put(
            "SetVisitablePlacesRequest", 
            new ActionConfig(
                "Aggiungi posto visitabile", 
                "Inserisce un nuovo posto visitabile per un'organizzazione", 
                this::setVisitablePlacesRequest));
        configMap.put(
            "SetMaximumFriendsRequest", 
            new ActionConfig(
                "Imposta massimo amici", 
                "Imposta il numero massimo di amici per iscrizione", 
                this::setMaximumFriendsRequest));
        configMap.put(
            "SetNewPasswordRequest", 
            new ActionConfig(
                "Modifica password", 
                "Modifica la password del configuratore", 
                this::setNewPasswordRequest));
        configMap.put(
            "SetClosedDaysRequest", 
            new ActionConfig(
                "Imposta giorni chiusura", 
                "Imposta il periodo di chiusura per l'organizzazione", 
                this::setClosedDaysRequest));
        configMap.put(
            "SetNewVoluntary", 
            new ActionConfig(
                "Nuovo volontario", 
                "Crea ed inserisci i dati di un nuovo volontario", 
                this::setNewVoluntaryRequest));
        configMap.put(
            "SetUserPermissions", 
            new ActionConfig(
                "Permessi volontario", 
                "Assegna permessi di visita a un volontario", 
                this::setUserPermissionsRequest));
        configMap.put(
            "DeleteEventRequest", 
            new ActionConfig(
                "Elimina evento", 
                "Elimina l'evento specificato e le relative istanze", 
                this::deleteEventRequest));
        configMap.put(
            "DeleteEventInstanceRequest", 
            new ActionConfig(
                "Elimina istanza evento", 
                "Elimina una singola istanza temporale di un evento", 
                this::deleteEventInstanceRequest));
        configMap.put(
            "SetNewEventRequest", 
            new ActionConfig(
                "Nuovo evento", 
                "Crea un nuovo evento per l'organizzazione", 
                this::setNewEventRequest));
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

    private void getMaximumFriendsRequest() 
    {
        System.out.println("getMaximumFriendsRequest()");
        System.out.println("Richiesta numero amici invitabili");
        UserViewInterface userView = new GetMaximumFriendsGUI();
        userView.paint(new JSONObject());
    }

    private void getPersonalDataRequest() 
    {
        System.out.println("getPersonalDataRequest()");
        System.out.println("Richiesta dati personali");
        UserViewInterface userView = new GetPersonalDataGUI();
        userView.paint(new JSONObject());
    }

    private void getPlacesRequest() 
    {
        System.out.println("getPlacesRequest()");
        System.out.println("Richiesta luoghi");
        UserViewInterface userView = new GetPlacesGUI();
        userView.paint(new JSONObject());
    }

    private void getPossibleVisitsRequest() 
    {
        System.out.println("getPossibleVisitsRequest()");
        System.out.println("Richiesta tipi di visita possibili");
        UserViewInterface userView = new GetPossibleVisitsGUI();
        userView.paint(new JSONObject());
    }

    private void getVoluntariesRequest() 
    {
        System.out.println("getVoluntariesRequest()");
        System.out.println("Visualizza e filtra volontari");
        UserViewInterface userView = new GetVoluntariesGUI();
        userView.paint(new JSONObject());
    }

    private void setVisitablePlacesRequest() 
    {
        System.out.println("setVisitablePlacesRequest()");
        System.out.println("Aggiunta posto visitabile");
        UserViewInterface userView = new SetVisitablePlacesGUI();
        userView.paint(new JSONObject());
    }

    private void setMaximumFriendsRequest() 
    {
        System.out.println("setMaximumFriendsRequest()");
        System.out.println("Imposta massimo amici");
        UserViewInterface userView = new SetMaximumFriendsGUI();
        userView.paint(new JSONObject());
    }

    private void setNewPasswordRequest() 
    {
        System.out.println("setNewPasswordRequest()");
        System.out.println("Modifica password");
        UserViewInterface userView = new SetNewPasswordGUI();
        userView.paint(new JSONObject());
    }

    private void setClosedDaysRequest() 
    {
        System.out.println("setClosedDaysRequest()");
        System.out.println("Imposta giorni di chiusura");
        UserViewInterface userView = new SetClosedDaysGUI();
        userView.paint(new JSONObject());
    }

    private void setNewVoluntaryRequest() 
    {
        System.out.println("setNewVoluntaryRequest()");
        System.out.println("Crea un nuovo volontario dell'organizzazione");
        UserViewInterface userView = new SetNewVoluntaryGUI();
        userView.paint(new JSONObject());
    }

    private void setUserPermissionsRequest() 
    {
        System.out.println("setUserPermissionsRequest()");
        System.out.println("Assegna permessi di visita");
        UserViewInterface userView = new SetUserPermissionsGUI();
        userView.paint(new JSONObject());
    }

    private void deleteEventRequest() 
    {
        System.out.println("deleteEventRequest()");
        UserViewInterface userView = new DeleteEventGUI();
        userView.paint(new JSONObject());
    }

    private void deleteEventInstanceRequest() 
    {
        System.out.println("deleteEventInstanceRequest()");
        UserViewInterface userView = new DeleteEventInstanceGUI();
        userView.paint(new JSONObject());
    }

    private void setNewEventRequest() 
    {
        System.out.println("setNewEventRequest()");
        UserViewInterface userView = new SetNewEventGUI();
        userView.paint(new JSONObject());
    }
}