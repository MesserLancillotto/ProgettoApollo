package Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.*;

import Comunication.Request.*;
import Comunication.Request.Interfaces.*;
import Comunication.DatabaseObjects.*;

public class Client 
{
    private String userID = "";
    private String userPassword = "";
    private static Client instance;

    private RequestInterface request = null;

    private static final String SERVER_ADDR = "127.0.0.1";
    private static final int PORT = 8000;
    private String filePath = "configurazione.json";
    private JSONObject userData;
    private Boolean triggerPersonalData = false;
    private String whichFile = "";
    private boolean isObject = true;

    public static JSONObject readJSONObjectFromFile(String filename) {
        try {
            if (!filename.endsWith(".json")) {
                filename += ".json";
            }
            String jsonContent = Files.readString(Paths.get(filename));
            return new JSONObject(jsonContent);
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONArray readJSONArrayFromFile(String filename) {
        try {
            if (!filename.endsWith(".json")) {
                filename += ".json";
            }
            String jsonContent = Files.readString(Paths.get(filename));
            return new JSONArray(jsonContent);
        } catch (Exception e) {
            return null;
        }
    }

    private Client(String userID, String userPassword) 
    {
        this.userID = userID;
        this.userPassword = userPassword;
    }

    public static synchronized Client getInstance() 
    {
        if (instance == null) 
        {
            instance = new Client("", "");
        }
        return instance;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    public String getUserID()
    {
        return this.userID;
    }

    public String getUserPassword()
    {
        return this.userPassword;
    }
    public JSONObject getUserData()
    {
        return this.userData;
    }

    public RequestInterface getCurrentRequest()
    {
        return this.request;
    }

    public String makeServerRequest()
    {
        if(!this.triggerPersonalData)
        {
            return makeServerRequest(this.request);
        }
        else
        {
            this.triggerPersonalData = false;
            String reply = makeServerRequest(this.request);
            userData = new JSONObject(reply);
            return reply;
        }
    }

    public String makeServerRequest(JSONObject jsonRequest)
    {
        return NetworkClient.makeServerRequest(SERVER_ADDR, PORT, jsonRequest.toString());
    }

    public String makeServerRequest(RequestInterface request) 
    {
        return NetworkClient.makeServerRequest(SERVER_ADDR, PORT, request.toJSONString());
    }

    public void deletePlace(String city, String address) 
    {
        this.request = new DeletePlaceRequest(this.userID, this.userPassword, city, address);
    }

    public void delete_place(String city, String address) 
    {
        deletePlace(city, address);
    }

    public void delete_place(String city, String address, String visitType) 
    {
        this.request = new DeletePlaceRequest(this.userID, this.userPassword, city, address, visitType);
    }

    public void setNewPlace(String city, String address, String description, String visitType, String defaultVoluntary) 
    {
        SetVisitablePlacesRequest req = new SetVisitablePlacesRequest(this.userID, this.userPassword);
        req.addPlace(city, address, description, visitType, defaultVoluntary);
        this.request = req;
    }

    public void set_new_place(String city, String address, String description, String organization, String visitType, String defaultVoluntary) 
    {
        setNewPlace(city, address, description, visitType, defaultVoluntary);
    }

    public void setUserPermissions(String targetID, List<String> permissions) 
    {
        this.request = new SetVoluntaryPermissionsRequest(this.userID, this.userPassword, targetID, permissions);
    }

    public void editVisitablePlaces(String city, String address, String visitType, String newDefauldVoluntary) 
    {
        this.request = new EditVisitablePlacesRequest(this.userID, this.userPassword, city, address, visitType, newDefauldVoluntary);
    }

    public void set_new_organization(String organizationName, List<String> territoriesOfCompetence) 
    {
        this.request = new SetNewOrganizationRequest(this.userID, this.userPassword, organizationName, territoriesOfCompetence);
    }

    public void set_new_organization(String organizationName, List<String> territoriesOfCompetence, Integer maximumFriends) 
    {
        this.request = new SetNewOrganizationRequest(this.userID, this.userPassword, organizationName, territoriesOfCompetence, maximumFriends);
    }

    public void setNewUser(String userName, String newPassword, String cityOfResidence, Integer birthYear) 
    {
        this.request = new SetNewUserRequest(userName, newPassword, cityOfResidence, birthYear);
    }

    public void setNewVoluntary(String name, String surname, String city, String birthDate, String password) {
        int day = 1;
        int month = 1;
        int year = 2000;

        if (birthDate != null && birthDate.contains("/")) {
            String[] parts = birthDate.split("/");
            if (parts.length != 3) 
            {
                System.out.println("Errore nel parsing della data, usati valori di default.");
                return; 
            }
            try {
                day = Integer.parseInt(parts[0].trim());
                month = Integer.parseInt(parts[1].trim());
                year = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                System.err.println("Errore nel parsing della data, usati valori di default.");
            }
        }

        // Recupera l'organizzazione dell'utente corrente (Configurator) se presente nel userData
        String organizationName = "";
        if (this.userData != null && this.userData.has("user")) {
            organizationName = this.userData.getJSONObject("user").optString("organization", "");
        }

        set_new_user(
            name,
            surname,
            password,
            city,
            day,
            month,
            year,
            2026,
            UserRole.VOLUNTARY,
            organizationName
        );
    }

    public void set_new_user(
            String name,
            String surname,
            String password,
            String city,
            Integer birth_dd,
            Integer birth_mm,
            Integer birth_yy,
            Integer user_since,
            UserRole role,
            String organization
    ) {
        this.request = new SetNewUserRequest(this.userID, this.userPassword, name, surname, password, city, birth_dd, birth_mm, birth_yy, user_since, role, organization);
    }

    public void change_password(String newPassword) 
    {
        this.request = new SetNewPasswordRequest(
            this.userID, 
            this.userPassword, 
            newPassword
        );
        this.userPassword = newPassword; 
    }

    public void set_voluntary_to_event(
        String event, String targetID, int time) 
    {
    }

    public void set_disponibility(String eventName, int unixDate) 
    {
        List<List<Integer>> dispList = new ArrayList<>();
        List<Integer> interval = new ArrayList<>();
        interval.add(unixDate);
        interval.add(unixDate);
        dispList.add(interval);
        this.request = new SetDisponibilityRequest(this.userID, this.userPassword, dispList);
    }

    public void set_disponibility(List<List<Integer>> disponibilities) 
    {
        this.request = new SetDisponibilityRequest(this.userID, this.userPassword, disponibilities);
    }

    public void getVoluntaries(String name, String surname, String city, Integer birthYear, Boolean olderThanYear, String visitType) 
    {
        GetVoluntariesRequest req = new GetVoluntariesRequest(this.userID, this.userPassword);
        if (name != null && !name.trim().isEmpty()) {
            req.withName(name.trim());
        }
        if (surname != null && !surname.trim().isEmpty()) {
            req.withSurname(surname.trim());
        }
        if (city != null && !city.trim().isEmpty()) {
            req.withCity(city.trim());
        }
        if (birthYear != null) {
            req.withYear(birthYear, olderThanYear != null ? olderThanYear : false);
        }
        if (visitType != null && !visitType.trim().isEmpty()) {
            req.withAllowedVisits(visitType.trim());
        }
        this.request = req;
    }

    public void getVoluntaries() 
    {
        getVoluntaries(null, null, null, null, null, null);
    }

    public void get_voluntaries() 
    {
        getVoluntaries();
    }

    public void getPlaces(String city, String address, List<String> visitTypes, List<String> states) 
    {
        GetPlacesRequest req = new GetPlacesRequest(this.userID, this.userPassword);
        if (city != null && !city.trim().isEmpty()) {
            req.withCity(city.trim());
        }
        if (address != null && !address.trim().isEmpty()) {
            req.withAddress(address.trim());
        }
        if (visitTypes != null && !visitTypes.isEmpty()) {
            req.withVisitType(visitTypes);
        }
        if (states != null && !states.isEmpty()) {
            req.withState(states);
        }
        this.request = req;
    }

    public void getPlaces() 
    {
        getPlaces(null, null, null, null);
    }

    public void getPersonalData(String target) 
    {
        this.request = new GetPersonalDataRequest(this.userID, this.userPassword);
        this.triggerPersonalData = true;
    }

    public void get_personal_data(String target) 
    {
        getPersonalData(target);
    }

    public void get_subscribed_events(String targetID)
    {
        this.request = new GetSubscribedEventsRequest(this.userID, this.userPassword, targetID);
    }

    public void getPossibleVisits(String organization)
    {
        this.request = new GetPossibleVisitsRequest(organization);
    }

    public void getMaximumFriends(String organization)
    {
        this.request = new GetMaximumFriendsRequest(this.userID, this.userPassword, organization);
    }

    public void setMaximumFriends(int value)
    {
        this.request = new SetMaximumFriendsRequest(this.userID, this.userPassword, value);
    }

    public void getAllowedVisitTypes()
    {
        this.request = new GetAllowedVisitTypesRequest(this.userID, this.userPassword);
    }

    public void deleteVoluntary(String voluntaryID) 
    {
        System.out.println("deleteVoluntary() called with userID: " + this.userID);
        System.out.println("deleteVoluntary() called with userPassword: " + this.userPassword);
        System.out.println("deleteVoluntary() called with voluntaryID: " + voluntaryID);
        
        this.request = new DeleteVoluntaryRequest(this.userID, this.userPassword, voluntaryID);
    }

    public void open_voluntary_disponibility() 
    {
    }

    public void close_voluntary_disponibility() 
    {
    }

    public void set_disponibility_request(List<Integer> disponibility) 
    {
        List<List<Integer>> dispList = new ArrayList<>();
        if (disponibility != null) {
            for (Integer d : disponibility) {
                List<Integer> interval = new ArrayList<>();
                interval.add(d);
                interval.add(d);
                dispList.add(interval);
            }
        }
        this.request = new SetDisponibilityRequest(this.userID, this.userPassword, dispList);
    }

    public void set_max_people_subscription(int value) 
    {
        JSONObject maxPeopleSubscription = new JSONObject();
        maxPeopleSubscription.put("MaxPeople", value);

        if (filePath != null && !filePath.isEmpty()) {
            try (java.io.FileWriter file = new java.io.FileWriter(filePath)) {
                file.write(maxPeopleSubscription.toString(4));
            } catch (IOException e) {
                System.err.println("Errore durante il salvataggio del file locale: " + e.getMessage());
            }
        }

        this.request = new SetMaximumFriendsRequest(this.userID, this.userPassword, value);
        makeServerRequest();
    }

    public int get_max_people_subscription() 
    {
        if (filePath != null && Files.exists(Paths.get(filePath))) {
            try {
                String contenutoFile = Files.readString(Paths.get(filePath));
                JSONObject jsonLetto = new JSONObject(contenutoFile);
                if (jsonLetto.has("MaxPeople")) {
                    return jsonLetto.getInt("MaxPeople");
                }
            } catch (Exception e) {
                // fall back to server request
            }
        }

        JSONObject req = new JSONObject();
        req.put("action", "get_max_people_subscription");
        req.put("userID", this.userID);
        req.put("userPassword", this.userPassword);

        String jsonResponse = makeServerRequest(req);

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            System.err.println("❌ Nessuna risposta dal server.");
            return -1;
        }

        try {
            JSONObject responseObj = new JSONObject(jsonResponse);
            if (responseObj.has("MaxPeople")) {
                return responseObj.getInt("MaxPeople");
            }
        } catch (JSONException e) {
            System.err.println("❌ Errore nel parsing del JSON di risposta dal server: " + e.getMessage());
        }

        return -1;
    }

    public void setNewEvent(
        String name,
        String description,
        String visitType,
        String organization,
        String city,
        String address,
        String rendezvous
    ) {
        this.request = new SetNewEventRequest(
            this.userID,
            this.userPassword,
            name,
            description,
            visitType,
            organization,
            city,
            address,
            rendezvous
        );
    }

    public void setNewEvent(
        String name,
        String description,
        String visitType,
        String organization,
        String city,
        String address,
        String rendezvous,
        Integer startDate,
        Integer endDate,
        String state,
        List<String> voluntaries
    ) {
        SetNewEventRequest req = new SetNewEventRequest(
            this.userID,
            this.userPassword,
            name,
            description,
            visitType,
            organization,
            city,
            address,
            rendezvous
        );
        if (startDate != null && startDate > 0) {
            req.addInstance(
                startDate,
                endDate != null && endDate > 0 ? endDate : startDate,
                state != null && !state.isEmpty() ? state : "CONFIRMED",
                voluntaries != null ? voluntaries : new ArrayList<>(),
                new ArrayList<>()
            );
        }
        this.request = req;
    }

    public void deleteEvent(String name) 
    {
        this.request = new DeleteEventRequest(this.userID, this.userPassword, name);
    }

    public void delete_event(String name) 
    {
        deleteEvent(name);
    }

    public void deleteEventInstance(String name, Integer startDate) 
    {
        this.request = new DeleteEventInstanceRequest(this.userID, this.userPassword, name, startDate);
    }

    public void delete_event_instance(String name, Integer startDate) 
    {
        deleteEventInstance(name, startDate);
    }

    public void set_new_event(
            String eventName, String description, String city, String address,
            String meetingPoint, int startDate, int endDate, String organizationName,
            int minimumUsers, int maximumUsers, int maximumFriends, String visitType,
            float price, ArrayList<String> visitDays, ArrayList<Integer> startHour,
            ArrayList<Integer> duration
    ) {
        setNewEvent(
            eventName,
            description,
            visitType,
            organizationName,
            city,
            address,
            meetingPoint,
            startDate,
            endDate,
            "CONFIRMED",
            new ArrayList<>()
        );
    }

    public void getEvent(String state) 
    {
        this.request = new GetEventRequest(this.userID, this.userPassword, state);
    }

    public void get_event(String state) 
    {
        this.whichFile = "get_event.json";
        this.isObject = true;
        getEvent(state);
    }

    public void set_closed_days(long startDate, long endDate) 
    {
        this.request = new SetClosedDaysRequest(this.userID, this.userPassword, (int) startDate, (int) endDate);
    }

    public void deleteVisitTypeFromPlace(String city, String address, String visitType) 
    {
        this.request = new DeleteVisitTypeFromPlaceRequest(this.userID, this.userPassword, city, address, visitType);
    }

    public void set_user_subscription_to_event(List<String> users, String eventName, Integer startDate) 
    {
        this.request = new SetUserSubscriptionToEventRequest(this.userID, this.userPassword, eventName, startDate, users);
    }

    public void delete_user_subscription_to_event(String eventName, Integer startDate) 
    {
        this.request = new DeleteUserSubscriptionToEventRequest(this.userID, this.userPassword, eventName, startDate);
    }
}