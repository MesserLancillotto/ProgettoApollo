package User.Configurator;

import User.Interfaces.*;
import User.Configurator.*;
import org.json.JSONObject;

// Se DeleteVisitTypeFromPlaceGUI è in User.Configurator non serve import, 
// ma se si trova in User.User scommenta la riga sotto:
// import User.User.DeleteVisitTypeFromPlaceGUI; 

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
    }
}