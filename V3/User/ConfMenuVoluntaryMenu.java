package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class ConfMenuVoluntaryMenu extends UserMenu
{
    private static final String MENU_TITLE = "MENU GESTIONE VOLONTARI";
    private static final String GET_DATA_REMOVE_WHICHVOLUNTARY = "Scegli l'ID di quale volontario rimuovere";
    private static final String MSG_SHOW_VOLUNTARY_LIST = "Ecco l'elenco dei volontari iscritti:";
    private static final String MSG_ADDVOLTOEXVISIT_CHOOSE_EVENT = "Scegli a quale evento aggiungere un volontario";
    private static final String MSG_ADDVOLTOEXVISIT_CHOOSE_VOLUNTARY = "Scegli quale volontario aggiungere all'evento";
    private static final String MSG_CLOSE_DISP_COLLECTION = "Sei sicuro di voler chiudere la raccolta delle disponibilità dei volontari";
    private static final String MSG_OPEN_DISP_COLLECTION = "Sei sicuro di voler aprire la raccolta delle disponibilità dei volontari";
    private static final String ERROR_VOLUNTARY_LIST_EMPTY = "\nERRORE! Lista volontari vuota";
    private static final String ERROR_DURING_ACQUISITION = "\nERRORE! Acquisizione non riuscita\n";
    private static final String ERROR_EVENT_LIST_EMPTY = "\nERRORE! Lista eventi vuota";
    private static final String ERROR_SERVER = "\nERRORE! Connessione col server non riuscita\n";
    private String organization;
    
    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> view_voluntary_list());
        //iterazione 3
        menuSelection.put(2, () -> remove_voluntary());
        menuSelection.put(3, () -> close_disponibility_collection());
        menuSelection.put(4, () -> open_disponibility_collection());
        menuSelection.put(5, () -> add_voluntary_to_existing_visit());

        menuOptionList.add("Visualizza l'elenco volontari");
        menuOptionList.add("Rimuovi un volontario");
        menuOptionList.add("Chiudi la raccolta delle disponibilità dei volontari");
        menuOptionList.add("Apri la raccolta delle disponibilità dei volontari");
        menuOptionList.add("Aggiungi un volontario a una visita esistente");
    }

    public ConfMenuVoluntaryMenu (String organization)
    {
        UserTui.printCenteredTitle(MENU_TITLE);
        this.organization = organization;
        initialize_menu_selection();
        UserTui.stampSeparator();
        manage_options(MENU_TITLE);  
    }

    public void view_voluntary_list()   
    {
        Set<String> voluntaryList =  get_voluntary_list();
        if (voluntaryList != null && !voluntaryList.isEmpty())
        {
            UserTui.stamp_list(MSG_SHOW_VOLUNTARY_LIST, voluntaryList);
        }
        else
            System.out.println (ERROR_VOLUNTARY_LIST_EMPTY);
    }

    public void remove_voluntary()     
    {
        Set<String> voluntaryList =  get_voluntary_list();
        if (voluntaryList != null && !voluntaryList.isEmpty())
        {
            HashMap <Integer, String> voluntaryToChooseMap = UserTui.fromListToMap(voluntaryList);
            String voluntaryID = UserTui.getChoiceFromMap(GET_DATA_REMOVE_WHICHVOLUNTARY, voluntaryToChooseMap);
            StringBuilder msgToDecisionForVoluntary = new StringBuilder();
            msgToDecisionForVoluntary.append("Hai scelto ");
            msgToDecisionForVoluntary.append(voluntaryID);
            msgToDecisionForVoluntary.append(" confermi ");
            boolean confirmDecisionForVoluntary = UserTui.getYesNoAnswer (msgToDecisionForVoluntary.toString());
            if (!voluntaryID.trim().isEmpty() && confirmDecisionForVoluntary)
            {
                Client.getInstance().delete_voluntary(voluntaryID);
                String removeVoluntaryResponse = Client.getInstance().make_server_request();
                if (!removeVoluntaryResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(removeVoluntaryResponse))
                {
                    JSONObject dictionary = new JSONObject(removeVoluntaryResponse);
                    UserTui.operationIsSuccessful (dictionary.getBoolean("loginSuccessful"));    
                }
                else
                    System.out.println (ERROR_SERVER);
            }
            else
            {
                System.out.println (ERROR_DURING_ACQUISITION);
            }
        }
        else
            System.out.println (ERROR_VOLUNTARY_LIST_EMPTY);
    }

    public void close_disponibility_collection()    // manca chiamata al server
    {
        boolean makeServerCall = UserTui.getYesNoAnswer(MSG_CLOSE_DISP_COLLECTION);
        if (makeServerCall)
        {
            HashMap <String, Object> filter = new HashMap<>();
            filter.put ("voluntariesCanSubmit", false);
            Client.getInstance().get_event(filter);
            String closeVoluntaryDisponibilityCollectionResponse = Client.getInstance().make_server_request();
            if (!closeVoluntaryDisponibilityCollectionResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(closeVoluntaryDisponibilityCollectionResponse))
            {
                JSONObject dictionary = new JSONObject(closeVoluntaryDisponibilityCollectionResponse);
                UserTui.operationIsSuccessful (dictionary.getBoolean("editSuccesful"));    // controlla key word
            }
        }
    }

    public void open_disponibility_collection()     // manca chiamata al server
    {
        boolean makeServerCall = UserTui.getYesNoAnswer(MSG_OPEN_DISP_COLLECTION);
        if (makeServerCall)
        {
            HashMap <String, Object> filter = new HashMap<>();
            filter.put ("voluntariesCanSubmit", true);
            Client.getInstance().get_event(filter);
            String closeVoluntaryDisponibilityCollectionResponse = Client.getInstance().make_server_request();
            if (!closeVoluntaryDisponibilityCollectionResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(closeVoluntaryDisponibilityCollectionResponse))
            {
                JSONObject dictionary = new JSONObject(closeVoluntaryDisponibilityCollectionResponse);
                UserTui.operationIsSuccessful (dictionary.getBoolean("editSuccesful"));    // controlla key word
            }
        }
    }   

    public void add_voluntary_to_existing_visit()
    {
        Set <String> eventNameList = get_event_name_list();
        Set <String> voluntaryList = get_voluntary_list();
        if (eventNameList != null && !eventNameList.isEmpty())
        {
            HashMap <Integer, String> eventToChooseMap = UserTui.fromListToMap(eventNameList);
            String targetEvent = UserTui.getChoiceFromMap(MSG_ADDVOLTOEXVISIT_CHOOSE_EVENT, eventToChooseMap);
            boolean confirmDecisionForEvent = UserTui.getYesNoAnswer ("Hai scelto "+ targetEvent+ " confermi ");
            if (!targetEvent.trim().isEmpty() && confirmDecisionForEvent)
            {
                HashMap <Integer, String> voluntaryToChooseMap = UserTui.fromListToMap(voluntaryList);
                String targetVoluntary = UserTui.getChoiceFromMap(MSG_ADDVOLTOEXVISIT_CHOOSE_VOLUNTARY, voluntaryToChooseMap);
                boolean confirmDecisionForVoluntary = UserTui.getYesNoAnswer ("Hai scelto "+ targetVoluntary+ " confermi ");
                if (!targetVoluntary.trim().isEmpty() && confirmDecisionForVoluntary)
                {
                    StringBuilder thingToSayForDay = new StringBuilder();
                    thingToSayForDay.append("Inserisci il giorno in cui ");
                    thingToSayForDay.append(targetVoluntary);
                    thingToSayForDay.append(" si aggiunge all'evento ");
                    thingToSayForDay.append(targetEvent);
                    thingToSayForDay.append(" (formato: DD/MM/YYYY):");
                    int unixDate = DataManager.acquireDateFromUser(thingToSayForDay.toString());
                    Client.getInstance().set_voluntary_to_event(targetEvent, targetVoluntary, unixDate);
                    String setVoluntaryToEventResponse = Client.getInstance().make_server_request();
                    if (!setVoluntaryToEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(setVoluntaryToEventResponse))
                    {
                        JSONObject dictionary = new JSONObject(setVoluntaryToEventResponse);
                        boolean operationWasSuccessful = dictionary.getBoolean("accessSuccesful") && dictionary.getBoolean("querySuccessful");
                        UserTui.operationIsSuccessful (operationWasSuccessful); 
                    }
                }
            }
        }
        else
            System.out.println (ERROR_EVENT_LIST_EMPTY);
    }

    // metodi per GESTIONE INTERNA

    private Set<String> get_event_name_list ()
    {
        HashMap <String, Object> filters = new HashMap<>();
        Set <String> eventNameList = new HashSet<>();
        filters.put ("eventName", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                eventNameList.add(event.getString("eventName"));
            }
        }
        return eventNameList;
    }

    private Set<String> get_voluntary_list()   
    {
        Set <String> voluntaryList = new HashSet<>();

        Client.getInstance().get_voluntaries(new HashMap<>());
        String getVoluntariesResponse = Client.getInstance().make_server_request();
        if (!getVoluntariesResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getVoluntariesResponse))
        {
            JSONArray voluntariesArray = new JSONArray(getVoluntariesResponse);
            for (int i = 0; i < voluntariesArray.length(); i++)
            {
                JSONObject voluntary = voluntariesArray.getJSONObject(i);
                String voluntaryID = voluntary.getString("userName");
                voluntaryList.add(voluntaryID);
            }
            return voluntaryList;
        }
        else
            return null;
    }
}
