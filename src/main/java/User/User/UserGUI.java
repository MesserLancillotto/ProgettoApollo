package User.User;

import User.Interfaces.*;
import User.Configurator.*;
import org.json.JSONObject;

public class UserGUI extends AbstractConfiguratorGUI {

    public UserGUI() {
        configMap.put(
            "GetEvents",
            new ActionConfig(
                "Visualizza eventi",
                "Visualizza gli eventi presenti",
                this::getEventRequest));
        configMap.put(
            "GetPersonalData",
            new ActionConfig(
                "Visualizza dati personali",
                "Visualizza i dati personali",
                this::getPersonalDataRequest));
        configMap.put(
            "SetSubscription",
            new ActionConfig(
                "Iscriviti a evento",
                "Iscriviti a un evento con eventuali amici",
                this::setUserSubscriptionRequest));
        configMap.put(
            "DeleteSubscription",
            new ActionConfig(
                "Disiscriviti da evento",
                "Cancella la tua iscrizione a un evento",
                this::deleteUserSubscriptionRequest));
        configMap.put(
            "GetSubscribedEvents",
            new ActionConfig(
                "Visualizza eventi iscritti",
                "Visualizza gli eventi ai quali sei iscritto",
                this::getSubscribedEventsRequest));
    }

    @Override
    protected String getWindowTitle() {
        return "Utente";
    }

    private void getEventRequest() {
        System.out.println("[UserGUI] getEventRequest()");
        UserViewInterface userView = new GetEventGUI();
        userView.paint(new JSONObject());
    }

    private void getPersonalDataRequest() {
        System.out.println("[UserGUI] getPersonalDataRequest()");
        UserViewInterface userView = new GetPersonalDataGUI();
        userView.paint(new JSONObject());
    }

    private void setUserSubscriptionRequest() {
        System.out.println("[UserGUI] setUserSubscriptionRequest()");
        UserViewInterface userView = new SetUserSubscriptionToEventGUI();
        userView.paint(new JSONObject());
    }

    private void deleteUserSubscriptionRequest() {
        System.out.println("[UserGUI] deleteUserSubscriptionRequest()");
        UserViewInterface userView = new DeleteUserSubscriptionToEventGUI();
        userView.paint(new JSONObject());
    }

    private void getSubscribedEventsRequest() {
        System.out.println("[UserGUI] getSubscribedEventsRequest()");
        UserViewInterface userView = new GetSubscribedEventsGUI();
        userView.paint(new JSONObject());
    }
}