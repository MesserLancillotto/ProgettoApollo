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

        configMap.put("GetPersonalData", new ActionConfig(
                "Dati personali",
                "Visualizza dati personali, posti e visite",
                this::getPersonalData));

        configMap.put("GetAvailableEvents", new ActionConfig(
                "Eventi Disponibili",
                "Visualizza gli eventi a cui potresti iscriverti",
                this::getAvailableEvents));

        configMap.put("SubscribeEvent", new ActionConfig(
                "Iscrizione Evento",
                "Iscriviti a un evento",
                this::subscribeEventRequest));

        configMap.put("UnsubscribeEvent", new ActionConfig(
                "Cancellazione Iscrizione",
                "Cancella l'iscrizione a un evento",
                this::unsubscribeEventRequest));

        configMap.put("GetSubscribedEvents", new ActionConfig(
                "Eventi Iscritti",
                "Visualizza gli eventi a cui sei iscritto",
                this::getSubscribedEvents));

        configMap.put("SetNewPassword", new ActionConfig(
                "Cambia password",
                "Cambia la password",
                this::setNewPasswordRequest));
    }

    @Override
    protected String getWindowTitle() {
        return "Volontario";
    }

    private void getPersonalData() {
        new GetPersonalDataGUI().paint(new JSONObject());
    }

    private void getAvailableEvents() {
        new GetEventGUI().paint(new JSONObject());
    }

    private void subscribeEventRequest() {
        new SetVoluntarySubscriptionToEventGUI().paint(new JSONObject());
    }

    private void unsubscribeEventRequest() {
        new DeleteVoluntarySubscriptionToEventGUI().paint(new JSONObject());
    }

    private void getSubscribedEvents() {
        new GetSubscribedEventsGUI().paint(new JSONObject());
    }

    private void setNewPasswordRequest() {
        setNewPasswordView.paint(new JSONObject());
    }
}