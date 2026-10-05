package User.User;

import User.Interfaces.*;
import User.Configurator.*;
import org.json.JSONObject;

public class UserGUI extends AbstractConfiguratorGUI {

    public UserGUI() {
        configMap.put(
            "DeleteUserSubscriptionToEvent",
            new ActionConfig(
                "Cancella iscrizione evento",
                "Cancella l'iscrizione a un evento selezionato",
                this::deleteUserSubscriptionToEventRequest));

        configMap.put(
            "GetEvent",
            new ActionConfig(
                "Richiesta Evento",
                "Ottiene le informazioni sull'evento dato lo stato specificato",
                this::getEventRequest));

        configMap.put(
            "GetMaximumFriends",
            new ActionConfig(
                "Numero massimo di amici",
                "Ottiene il numero massimo di amici per un'associazione",
                this::getMaximumFriendsRequest));

        configMap.put(
            "GetPersonalData",
            new ActionConfig(
                "Dati personali",
                "Ottiene le informazioni sull'utente",
                this::getPersonalDataRequest));

        configMap.put(
            "GetSubscribedEvents",
            new ActionConfig(
                "Eventi a cui si è iscritti",
                "Visualizza gli eventi a cui l'utente è iscritto",
                this::getSubscribedEventsRequest));

        configMap.put(
            "SetUserSubscriptionToEvent",
            new ActionConfig(
                "Iscrizione evento",
                "Iscrizione a un evento specificato con eventuali amici",
                this::setUserSubscriptionToEventRequest));
    }

    @Override
    protected String getWindowTitle() {
        return "Utente";
    }

    private void deleteUserSubscriptionToEventRequest() {
        System.out.println("[UserGUI] deleteUserSubscriptionToEventRequest()");
        UserViewInterface userView = new DeleteUserSubscriptionToEventGUI();
        userView.paint(new JSONObject());
    }

    private void getEventRequest() {
        System.out.println("[UserGUI] getEventRequest()");
        UserViewInterface userView = new GetEventGUI();
        userView.paint(new JSONObject());
    }

    private void getMaximumFriendsRequest() {
        System.out.println("[UserGUI] getMaximumFriendsRequest()");
        UserViewInterface userView = new GetMaximumFriendsGUI();
        userView.paint(new JSONObject());
    }

    private void getPersonalDataRequest() {
        System.out.println("[UserGUI] getPersonalDataRequest()");
        UserViewInterface userView = new GetPersonalDataGUI();
        userView.paint(new JSONObject());
    }

    private void getSubscribedEventsRequest() {
        System.out.println("[UserGUI] getSubscribedEventsRequest()");
        UserViewInterface userView = new GetSubscribedEventsGUI();
        userView.paint(new JSONObject());
    }

    private void setUserSubscriptionToEventRequest() {
        System.out.println("[UserGUI] setUserSubscriptionToEventRequest()");
        UserViewInterface userView = new SetUserSubscriptionToEventGUI();
        userView.paint(new JSONObject());
    }
}