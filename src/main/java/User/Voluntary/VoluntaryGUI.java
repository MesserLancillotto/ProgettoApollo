package User.Voluntary;

import User.SetNewPasswordGUI;
import User.Interfaces.*;
import User.Configurator.*;
import org.json.JSONObject;

public class VoluntaryGUI extends AbstractConfiguratorGUI {

    private final UserViewInterface setNewPasswordView;

    public VoluntaryGUI() {
        this(new SetNewPasswordGUI());
    }

    public VoluntaryGUI(UserViewInterface setNewPasswordView) {
        this.setNewPasswordView = setNewPasswordView;

        configMap.put("GetAllowedVisitTypes", new ActionConfig(
                "Visite autorizzate",
                "Ottieni il tipo di visite autorizzate",
                this::getAllowedVisitTypes));

        configMap.put("GetEvent", new ActionConfig(
                "Eventi",
                "Ottieni dati su visite",
                this::getEvent));

        configMap.put("GetPersonalData", new ActionConfig(
                "Dati personali",
                "Stampa a video i dati dell'utente",
                this::getPersonalData));

        configMap.put("GetPlaces", new ActionConfig(
                "Visualizza posto",
                "Cerca tra i posti dell'organizzazione in base a dei filtri",
                this::getPlaces));

        configMap.put("GetPossibleVisits", new ActionConfig(
                "Visualizza autorizzazioni",
                "Visualizza le autorizzazioni permesse",
                this::getPossibleVisits));

        configMap.put("SetNewPassword", new ActionConfig(
                "Cambia password",
                "Cambia la password",
                this::setNewPasswordRequest));
    }

    private void getAllowedVisitTypes() 
    {
        System.out.println("[VoluntaryGUI] getAllowedVisitTypes()");
        
    }

    private void getEvent() {
        System.out.println("[VoluntaryGUI] getEvent()");
        // TODO: implementare
    }

    private void getPersonalData() {
        System.out.println("[VoluntaryGUI] getPersonalData()");
        // TODO: implementare
    }

    private void getPlaces() {
        System.out.println("[VoluntaryGUI] getPlaces()");
        // TODO: implementare
    }

    private void getPossibleVisits() {
        System.out.println("[VoluntaryGUI] getPossibleVisits()");
        // TODO: implementare
    }

    private void setNewPasswordRequest() {
        setNewPasswordView.paint(new JSONObject());
    }
}