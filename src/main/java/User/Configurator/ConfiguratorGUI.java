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
}