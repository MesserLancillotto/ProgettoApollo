package User;

import java.util.*;
import Client.Client;

import org.json.*;

public class ConfiguratorMenu extends UserMenu
{
    private static final String MENU_TITLE = "MENU PRINCIPALE CONFIGURATORE";
    private static final String VISIT_STATE_MSG = "\nEcco l'elenco delle visite attualmente nello stato di proposto/completato/confermato/cancellato/effettuato";
    String organization; 

    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> handle_voluntaries());
        menuSelection.put(2, () -> handle_places());
        menuSelection.put(3, () -> handle_generic_stuff());  
        menuSelection.put(4, () -> view_visit_state());    

        menuOptionList.add("Apri menu gestione volontari");
        menuOptionList.add("Apri menu gestione luoghi visitabili");
        menuOptionList.add("Apri menu gestione opzioni generiche");
        menuOptionList.add("Visualizza le visite in stato di visita");	
    }

    //COSTRUTTORE
    public ConfiguratorMenu (String organization)
    {
        UserTui.printCenteredTitle(MENU_TITLE);
        this.organization = organization;   
        initialize_menu_selection();
        manage_options(MENU_TITLE);
    }
    // COSTRUTTORE PER IL PRIMO ACCESSO DEL CONFIGURATORE
    public ConfiguratorMenu (Boolean isFirstAccess, String organization)
    {
        new ConfMenuPlaceMenu(isFirstAccess, organization); 
    }

    public void handle_voluntaries ()
    {
        new ConfMenuVoluntaryMenu (organization);
    }
	
    public void handle_places ()
    {
        new ConfMenuPlaceMenu (organization);
    }

    public void handle_generic_stuff()
    {
        new ConfMenuGenericOptions (organization);
    }

    private void view_visit_state ()
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        filters.put ("state", "%");
        filters.put ("startDate", "%");
        filters.put ("eventName", "%");
        filters.put ("description", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
            int cycleCount = 1;

            System.out.println (VISIT_STATE_MSG);
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                String tmpEventName = event.getString("eventName");
                String tmpDescription = event.getString("description");
                String tmpCity = event.getString("city");
                String tmpAddress = event.getString("address");
                int tmpStartDate = event.getInt("startDate");
                StateOfVisit visitState = StateOfVisit.fromString(event.getString("state"));
                String formattedDate = DataManager.fromUnixToNormal(tmpStartDate);

                if (visitState == StateOfVisit.CANCELLATA || visitState == StateOfVisit.PROPOSTA || visitState == StateOfVisit.CONFERMATA
                        || visitState == StateOfVisit.COMPLETA || visitState == StateOfVisit.EFFETTUATA)
                {
                    
                    UserTui.stampEventInfo (cycleCount, tmpEventName, tmpDescription, tmpCity, tmpAddress, formattedDate, visitState);
                } 
                cycleCount++;  
            }
        }
    }
}
