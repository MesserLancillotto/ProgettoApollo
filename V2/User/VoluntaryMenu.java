import org.json.*;
import java.util.*;

import Client.Client;

public class VoluntaryMenu extends UserMenu
{
    private static final String MENU_TITLE = "MENU PRINCIPALE VOLONTARI";
    private static final String ERROR_NO_VISIT_TYPE_ASSOCIATED = "Non ci sono tipi di visita associati a te";
    private static final String ERROR_EMPTY_EVENT_LIST = "Non ci sono eventi in quel giorno";
    private String voluntaryUserName;
    private ArrayList <String> associatedVisitType;

    public void initialize_menu_selection()
	{
		menuSelection.put(1, () -> view_associated_visit_type());
        menuSelection.put (2, () -> give_disponibility());

        menuOptionList.add("Visualizza il tipo di visite a cui sei associato");
        menuOptionList.add("Fornisci i giorni in cui puoi lavorare");
	}
   
    //COSTRUTTORE
    public VoluntaryMenu (String voluntaryUserName, ArrayList <String> associatedVisitType)
    {
        UserTui.printCenteredTitle(MENU_TITLE);
        this.voluntaryUserName = voluntaryUserName;
        this.associatedVisitType = associatedVisitType;
        initialize_menu_selection();
        manage_options(MENU_TITLE);
    }

    private void view_associated_visit_type ()
    {
        UserTui.stampSeparator();
        Set <String> visitType = new HashSet<>(associatedVisitType);
        if (!visitType.isEmpty() && visitType != null)
        {
            StringBuilder msg = new StringBuilder();
            msg.append("Ecco i tipi di visita a cui sei associato (");
            msg.append(voluntaryUserName);
            msg.append (")");
            UserTui.stamp_list(msg.toString(), visitType);
        }
        else
        {
            System.out.println(ERROR_NO_VISIT_TYPE_ASSOCIATED);
        }
    }

    private void give_disponibility()
    {
        DataManagerDisponibility date = new DataManagerDisponibility(2);
        boolean addAnotherDate;
        do
        {
            int disponibilityDay = date.getReferenceDay("Inserire il giorno dove puoi lavorare");
            if (disponibilityDay > 0)   // -1 indica errore durante l'acquisizione
            {
                int unixDate = date.getUnixDate(disponibilityDay);
                String eventName = show_events_by_specific_day (unixDate);  //ottengo quale evento vuole
                if (!eventName.trim().isEmpty())
                {
                    Client.getInstance().set_disponibility(eventName, unixDate);
                    String voluntaryDisponibilityResponse = Client.getInstance().make_server_request();
                    if (!voluntaryDisponibilityResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(voluntaryDisponibilityResponse))
                    {
                        JSONObject dictionary = new JSONObject(voluntaryDisponibilityResponse);
                        UserTui.operationIsSuccessful (dictionary.getBoolean("querySuccesful"));
                    }
                } 
            }
            addAnotherDate = UserTui.getYesNoAnswer("Vuoi inserire un'altra data");
        }while (addAnotherDate);
    }

    private String show_events_by_specific_day (int date)
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("withStartDate", date);
        filters.put ("eventName", "%");
        HashMap <Integer, String> validEventName = new HashMap<>();

        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();

        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
            int loopCount = 1;
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                int eventDate = event.getInt("startDate");   
                if (DataManager.isSameDay(eventDate, date))
                    validEventName.put (loopCount, event.getString("eventName"));   // CONTROLLA potrebbero esserci duplicati di eventName nella mappa
            }
            if (!validEventName.isEmpty())
                return UserTui.getChoiceFromMap("Scegli l'evento a cui vuoi dare la disponibilità:", validEventName);
            else
            {
                System.out.println (ERROR_EMPTY_EVENT_LIST);
                return "";
            }
        }
        return "";
    }
}
