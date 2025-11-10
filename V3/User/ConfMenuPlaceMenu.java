package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class ConfMenuPlaceMenu extends UserMenu
{
    private static final String MENU_TITLE = "MENU GESTIONE POSTI VISITABILI";
    private static final String ERROR_CONNECTION_SERVER = "Errore! Impossibile contattare il server";
    private static final String MSG_EVENTS_BY_PLACE = "Ecco gli eventi associati a ciascun luogo";
    private static final String MSG_REMOVEPLACE_CHOOSEPLACE = "Scegli il luogo da rimuovere";
    private static final String MSG_REMOVEVISIT_CHOOSEPLACE = "Scegli da quale luogo rimuovere il tipo di visita";
    private static final String MSG_CHOOSE_PLACE = "Scegli in che luogo aggiungere una nuova visita";
    private static final String MSG_ADD_ANOTHERVISIT_SAME_PLACE = "Vuoi inserire un'altro tipo di visita associato a questo luogo";
    private static final String MSG_SHOW_PLACES = "Questi sono i posti visitabili: ";
    private static final String GET_DATA_EVENT_NAME_EVENT = "Inserisci il nome dell'evento";
    private static final String GET_DATA_EVENT_DESCRIPTION = "Inserisci una descrizione dell'evento";
    private static final String GET_DATA_EVENT_EVENT_TYPE = "Inserisci il tipo di visita";
    private static final String GET_DATA_EVENT_MEETING_POINT = "Inserisci dove è il meeting point";
    private static final String GET_DATA_CITY = "Inserire la città dove si svolge questo evento";
    private static final String GET_DATA_ADDRESS = "Inserisci l'indirizzo";
    private static final String GET_DATA_DAY_WHICH_DAY = "Inserisci il giorno della settimana in cui si svolge questa visita";
    private static final String GET_DATA_DAY_START_HOUR = "Inserisci l'orario di inizio di questa visita (formato HH:MM)";
    private static final String GET_DATA_DAY_HOW_TIME = "Inserisci la durata in minuti di questa visita (minimo 60 minuti)";
    private static final String GET_DATA_PLACE_LOOP_CONF = "Vuoi inserire un'altro luogo";
    private static final String GET_DATA_DAY_LOOP_CONFIRM = "Vuoi inserire un altro giorno in cui si svolge questa visita";
    private static final String GET_DATA_MIN_PARTECIPANTS = "Inserisci il numero minimo di partecipanti a questo evento";
    private static final String GET_DATA_MAX_PARTECIPANTS  = "Inserisci il numero massimo di partecipanti a questo evento";
    private static final String GET_DATA_IS_PAY = "Questo evento è a pagamento?";
    private static final String GET_DAY_IS_PAY_HOW_MUCH = "Inserisci il prezzo in euro di questo evento";
    private static final int MAX_PEOPLE_FOR_VISIT = 2000;
    private static final int MAX_VISIT_PRICE = 10000;
    private static final int MAX_VISIT_DURATION = 1440;
    private static final int MIN_RANGE_VALUE = 0;
    private static final int MIN_VISIT_DURATION = 59;
    private String organization;

    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> view_visitable_places());
        menuSelection.put(2, () -> view_type_of_visit_by_place());

        menuOptionList.add("Visualizza l'elenco dei luoghi visitabili");
        menuOptionList.add("Visualizza l'elenco dei tipi di visita associati a ciascun luogo");
    }

    public ConfMenuPlaceMenu (String organization)
    {
        UserTui.printCenteredTitle(MENU_TITLE);
        this.organization = organization;   
        initialize_menu_selection();
        UserTui.stampSeparator();
        manage_options(MENU_TITLE);
    }
    // COSTRUTTORE PER IL PRIMO ACCESSO DEL CONFIGURATORE
    public ConfMenuPlaceMenu (boolean isFirstAccess, String organization)
    {
        this.organization = organization;
        introduce_new_place();
    }
    // stampa i posti visitabili, return di Set<String> per poter riusare il metodo nella ricerca posti
    public Map<String, String> view_visitable_places ()     //OK
	{
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
            Map<String, String> distinctPlaces = new HashMap<>();
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                StringBuilder place = new StringBuilder ();
                place.append(event.getString("city"));
                place.append (":");
                place.append(event.getString("address"));
                String formattedPlace = place.toString().toUpperCase().trim().replaceAll(" ", "");
                distinctPlaces.putIfAbsent(formattedPlace, place.toString());
            }
            UserTui.stamp_list (MSG_SHOW_PLACES, distinctPlaces.values());
            return distinctPlaces;
        }
        return null;
	}

    public Map<String, Place> view_type_of_visit_by_place() //OK
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        filters.put ("visitType", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
        
            Map<String, Place> placeMap = new HashMap<>();
            
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                StringBuilder place = new StringBuilder ();
                place.append(event.getString("city"));
                place.append (":");
                place.append(event.getString("address"));
                String formattedPlace = place.toString().toUpperCase().trim().replaceAll(" ", "");   
                // normalizzo formato per evitare duplicati
                placeMap.putIfAbsent(formattedPlace, new Place (place.toString())); 
                // creo una nuova istanza di Place se non esiste già con combo città+indirizzo in maisucolo e trim
                placeMap.get(formattedPlace).addVisitType(event.getString("visitType"));
            }
            UserTui.stamp_list(MSG_EVENTS_BY_PLACE, placeMap);
            return placeMap;
        }
        else
            System.out.println (ERROR_CONNECTION_SERVER);
        return null;
    }

    public void introduce_new_place()   //OK
    {
        boolean addAnotherPlaceAnswer;
        do
        {
            String cityName = UserTui.getString(GET_DATA_CITY);
            String cityAddress = UserTui.getString(GET_DATA_ADDRESS);
            add_place_to_server(cityName, cityAddress);
            addAnotherPlaceAnswer = UserTui.getYesNoAnswer(GET_DATA_PLACE_LOOP_CONF);
        } while (addAnotherPlaceAnswer); 
    }


    //METODI GESTIONE INTERNA

    public Map<String, String> get_visitable_places ()     //OK
	{
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
            Map<String, String> distinctPlaces = new HashMap<>();
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                StringBuilder place = new StringBuilder ();
                place.append(event.getString("city"));
                place.append (":");
                place.append(event.getString("address"));
                String formattedPlace = place.toString().toUpperCase().trim().replaceAll(" ", "");
                distinctPlaces.putIfAbsent(formattedPlace, place.toString());
            }
            return distinctPlaces;
        }
        return null;
	}

    public Map<String, Place> get_type_of_visit_by_place() //OK
    {
        HashMap <String, Object> filters = new HashMap<>();
        filters.put ("city", "%");
        filters.put ("address", "%");
        filters.put ("visitType", "%");
        Client.getInstance().get_event(filters);
        String getEventResponse = Client.getInstance().make_server_request();
        if (!getEventResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getEventResponse))
        {
            JSONArray eventsArray = new JSONArray(getEventResponse);
        
            Map<String, Place> placeMap = new HashMap<>();
            
            for (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                StringBuilder place = new StringBuilder ();
                place.append(event.getString("city"));
                place.append (":");
                place.append(event.getString("address"));
                String formattedPlace = place.toString().toUpperCase().trim().replaceAll(" ", "");   // normalizzo formato per evitare duplicati
                
                placeMap.putIfAbsent(formattedPlace, new Place (place.toString())); 
                // creo una nuova istanza di Place se non esiste già con combo città+indirizzo in maisucolo e trim
                placeMap.get(formattedPlace).addVisitType(event.getString("visitType"));
            }
            return placeMap;
        }
        else
            System.out.println (ERROR_CONNECTION_SERVER);
        return null;
    }

    private void add_place_to_server(String cityName, String cityAddress) // DA CONTROLLARE
    {
        boolean addAnotherTypeVisitAnswer; 
        do 
            {
                String eventName = UserTui.getString(GET_DATA_EVENT_NAME_EVENT);
                String eventDescription = UserTui.getString(GET_DATA_EVENT_DESCRIPTION, 500);
                String visitType = UserTui.getString(GET_DATA_EVENT_EVENT_TYPE);
                String meetingPoint = UserTui.getString(GET_DATA_EVENT_MEETING_POINT);
                DataManagerPeriod date = new DataManagerPeriod();
                int startDate = date.getStartDate();
                int endDate = date.getEndDate();
                ArrayList <String> visitDays = new ArrayList<>();
                ArrayList <Integer> startHours = new ArrayList<>();
                ArrayList <Integer> duration = new ArrayList<>();
                do
                {
                    String visitDay = DataManager.getDayOfWeekFromUser(GET_DATA_DAY_WHICH_DAY);
                    if (!visitDay.trim().isEmpty()) // controllo che sia andata bene l'acquisizione
                    {
                        visitDays.add(visitDay);
                        startHours.add (DataManager.getAnHourFromUser(GET_DATA_DAY_START_HOUR));
                        duration.add (UserTui.getInteger(GET_DATA_DAY_HOW_TIME, MIN_VISIT_DURATION, MAX_VISIT_DURATION));
                    }
                }while (UserTui.getYesNoAnswer(GET_DATA_DAY_LOOP_CONFIRM));
                int minPartecipants = UserTui.getInteger(GET_DATA_MIN_PARTECIPANTS, MIN_RANGE_VALUE, MAX_PEOPLE_FOR_VISIT);
                int maxPartecipants = UserTui.getInteger(GET_DATA_MAX_PARTECIPANTS, minPartecipants+1, MAX_PEOPLE_FOR_VISIT);
                int maxPeopleForSubscription = JSONObjectCreator.getMaxPeopleForSubscription();
                float price = 0;
                if (UserTui.getYesNoAnswer(GET_DATA_IS_PAY))
                    price = UserTui.getFloat(GET_DAY_IS_PAY_HOW_MUCH, MIN_RANGE_VALUE, MAX_VISIT_PRICE);
                
                UserTui.stampAllEventInfo(cityName, cityAddress, eventName, eventDescription, visitType, meetingPoint, 
                visitDays, startHours, duration, startDate, endDate, minPartecipants, maxPartecipants, maxPeopleForSubscription, price);
                if (UserTui.getYesNoAnswer("Confermi "))
                {
                    Client.getInstance().set_new_event(eventName, eventDescription, cityName, cityAddress, meetingPoint, startDate, endDate, 
                    organization, minPartecipants, maxPartecipants, maxPeopleForSubscription, visitType, price, visitDays, startHours, duration);
                    String setNewEventReply = Client.getInstance().make_server_request();
                    if (!setNewEventReply.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(setNewEventReply))
                    {
                        JSONObject dictionary = new JSONObject(setNewEventReply);
                        UserTui.operationIsSuccessful(dictionary.getBoolean("registrationSuccesful"));
                    }
                    else
                        System.out.println (ERROR_CONNECTION_SERVER);

                }
                addAnotherTypeVisitAnswer = UserTui.getYesNoAnswer(MSG_ADD_ANOTHERVISIT_SAME_PLACE);
            } while (addAnotherTypeVisitAnswer); // fine ciclo tipo visita
    }
}
