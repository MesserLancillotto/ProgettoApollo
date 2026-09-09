package Client;

import java.nio.file.*;
import java.util.*;
import java.io.*;
import org.json.*;

import Comunication.Request.*;
import Comunication.Request.Interfaces.*;
import Comunication.DatabaseObjects.*;

public class Client 
{
    private String userID = "";
    private String userPassword = "";
    private String whichFile = "";
    private boolean isObject = true;
    private RequestInterface currentRequest;
    private static Client instance;

    private static final String SERVER_ADDR = "127.0.0.1";
    private static final int PORT = 8000;
    private String filePath = "configurazione.json";

    private Client() {}

    public static synchronized Client getInstance() 
    {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void setUserID(String tmpUserID) 
    {
        this.userID = tmpUserID;
    }

    public String getUserID()
    {
        return this.userID;
    }

    public void setUserPassword(String tmpUserPassword) 
    {
        this.userPassword = tmpUserPassword;
    }

    public String getUserPassword()
    {
        return this.userPassword;
    }

    public void setCurrentRequest(RequestInterface request)
    {
        this.currentRequest = request;
    }

    public RequestInterface getCurrentRequest()
    {
        return this.currentRequest;
    }

    public static JSONObject readJSONObjectFromFile(String filename) {
        try {
            if (!filename.endsWith(".json")) {
                filename += ".json";
            }

            String jsonContent = Files.readString(Paths.get(filename));
            return new JSONObject(jsonContent);

        } catch (Exception e) {
            System.err.println("❌ Errore lettura JSONObject da: " + filename);
            System.err.println("   Dettaglio: " + e.getMessage());
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
            System.err.println("❌ Errore lettura JSONArray da: " + filename);
            System.err.println("   Dettaglio: " + e.getMessage());
            return null;
        }
    }

    // Invio richiesta preparata
    public String make_server_request() 
    {
        if (this.currentRequest != null) {
            return NetworkClient.makeServerRequest(SERVER_ADDR, PORT, this.currentRequest.toJSONString());
        }

        if (this.whichFile == null || this.whichFile.isEmpty()) {
            System.err.println("❌ Nessuna richiesta impostata.");
            return "";
        }

        JSONObject req = new JSONObject();
        String actionName = this.whichFile.replace(".json", "");
        req.put("action", actionName);
        req.put("userID", this.userID);
        req.put("userPassword", this.userPassword);

        return NetworkClient.makeServerRequest(SERVER_ADDR, PORT, req.toString());
    }

    // Invio generico tramite JSONObject
    public String make_server_request(JSONObject jsonRequest) 
    {
        if (jsonRequest == null) {
            System.err.println("❌ Richiesta JSON nulla.");
            return "";
        }
        if (!jsonRequest.has("userID")) jsonRequest.put("userID", this.userID);
        if (!jsonRequest.has("userPassword")) jsonRequest.put("userPassword", this.userPassword);

        return NetworkClient.makeServerRequest(SERVER_ADDR, PORT, jsonRequest.toString());
    }

    // Invio generico tramite RequestInterface
    public String make_server_request(RequestInterface request) 
    {
        if (request == null) {
            System.err.println("❌ Richiesta RequestInterface nulla.");
            return "";
        }
        this.currentRequest = request;
        return NetworkClient.makeServerRequest(SERVER_ADDR, PORT, request.toJSONString());
    }

    public void delete_place(String city, String address, String visitType) 
    {
        this.isObject = true;
        this.whichFile = "delete_place.json";
        this.currentRequest = new DeletePlaceRequest(this.userID, this.userPassword, city, address, visitType);
    }

    public void delete_place(String city, String address) 
    {
        delete_place(city, address, "");
    }

    public void set_new_place(String city, String address, String description, String organization, String visitType, String defaultVoluntary) 
    {
        this.isObject = true;
        this.whichFile = "set_new_place.json";
        SetVisitablePlacesRequest req = new SetVisitablePlacesRequest(this.userID, this.userPassword);
        req.addPlace(city, address, description, organization, visitType, defaultVoluntary);
        this.currentRequest = req;
    }

    public void edit_visitable_places(String city, String address, String visitType, String newDefauldVoluntary) 
    {
        this.isObject = true;
        this.whichFile = "edit_visitable_places.json";
        this.currentRequest = new EditVisitablePlacesRequest(this.userID, this.userPassword, city, address, visitType, newDefauldVoluntary);
    }

    public void set_new_organization(String organizationName, List<String> territoriesOfCompetence) 
    {
        this.isObject = true;
        this.whichFile = "set_new_organization.json";
        this.currentRequest = new SetNewOrganizationRequest(this.userID, this.userPassword, organizationName, territoriesOfCompetence);
    }

    public void set_new_user(String userName, String newPassword, String cityOfResidence, Integer birthYear) 
    {
        this.isObject = true;
        this.whichFile = "set_new_user.json";
        this.currentRequest = new SetNewUserRequest(userName, newPassword, cityOfResidence, birthYear);
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
        this.isObject = true;
        this.whichFile = "set_new_user.json";
        this.currentRequest = new SetNewUserRequest(name, surname, password, city, birth_dd, birth_mm, birth_yy, user_since, role, organization);
    }

    public void edit_password(String newPassword) 
    {
        change_password(newPassword);
    }

    public void change_password(String newPassword) 
    {
        this.isObject = true;
        this.whichFile = "change_password.json";
        this.currentRequest = new SetNewPasswordRequest(this.userID, this.userPassword, newPassword);
    }

    public void set_voluntary_to_event(String event, String targetID, int time) 
    {
        this.isObject = true;
        this.whichFile = "set_voluntary_to_event.json";
    }

    public void set_disponibility(String eventName, int unixDate) 
    {
        this.isObject = true;
        this.whichFile = "set_disponibility.json";
        List<List<Integer>> dispList = new ArrayList<>();
        List<Integer> interval = new ArrayList<>();
        interval.add(unixDate);
        interval.add(unixDate);
        dispList.add(interval);
        this.currentRequest = new SetDisponibilityRequest(this.userID, this.userPassword, dispList);
    }

    public void set_disponibility(List<List<Integer>> disponibilities) 
    {
        this.isObject = true;
        this.whichFile = "set_disponibility.json";
        this.currentRequest = new SetDisponibilityRequest(this.userID, this.userPassword, disponibilities);
    }

    public void get_voluntaries() 
    {
        this.isObject = true;
        this.whichFile = "get_voluntaries.json";
        this.currentRequest = new GetVoluntariesRequest(this.userID, this.userPassword);
    }

    public void get_places() 
    {
        this.isObject = true;
        this.whichFile = "get_places.json";
        this.currentRequest = new GetPlacesRequest(this.userID, this.userPassword);
    }

    public void get_personal_data(String target) 
    {
        this.isObject = true;
        this.whichFile = "get_personal_data.json";
        this.currentRequest = new GetPersonalDataRequest(this.userID, this.userPassword);
    }

    public void remove_voluntary(String voluntaryID) 
    {
        delete_voluntary(voluntaryID);
    }

    public void delete_voluntary(String voluntaryID) 
    {
        this.isObject = true;
        this.whichFile = "delete_voluntary.json";
        this.currentRequest = new DeleteVoluntaryRequest(this.userID, this.userPassword, voluntaryID);
    }

    public void open_voluntary_disponibility() 
    {
        this.isObject = true;
        this.whichFile = "open_voluntary_disponibility.json";
    }

    public void close_voluntary_disponibility() 
    {
        this.isObject = true;
        this.whichFile = "close_voluntary_disponibility.json";
    }

    public void set_disponibility_request(List<Integer> disponibility) 
    {
        this.isObject = true;
        this.whichFile = "set_disponibility_request.json";
        List<List<Integer>> dispList = new ArrayList<>();
        if (disponibility != null) {
            for (Integer d : disponibility) {
                List<Integer> interval = new ArrayList<>();
                interval.add(d);
                interval.add(d);
                dispList.add(interval);
            }
        }
        this.currentRequest = new SetDisponibilityRequest(this.userID, this.userPassword, dispList);
    }

    public void set_max_people_subscription(int value) 
    {
        JSONObject maxPeopleSubscription = new JSONObject();
        maxPeopleSubscription.put("MaxPeople", value);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(maxPeopleSubscription.toString(4));
            System.out.println("File JSON locale salvato con successo!");
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file locale: " + e.getMessage());
        }
        this.currentRequest = new SetMaximumFriendsRequest(this.userID, this.userPassword, value);
    }

    public int get_max_people_subscription() 
    {
        int maxPeople = -1;
        try {
            String contenutoFile = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonLetto = new JSONObject(contenutoFile);
            maxPeople = jsonLetto.getInt("MaxPeople");
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file locale: " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Errore nel parsing del JSON locale: " + e.getMessage());
        }
        return maxPeople;
    }

    public void set_new_event(
            String eventName, String description, String city, String address,
            String meetingPoint, int startDate, int endDate, String organizationName,
            int minimumUsers, int maximumUsers, int maximumFriends, String visitType,
            float price, ArrayList<String> visitDays, ArrayList<Integer> startHour,
            ArrayList<Integer> duration
    ) {
        this.isObject = true;
        this.whichFile = "set_new_event.json";
    }

    public void get_event(String state) 
    {
        this.isObject = true;
        this.whichFile = "get_event.json";
        this.currentRequest = new GetEventRequest(this.userID, this.userPassword, state);
    }

    public void set_closed_days(long startDate, long endDate) 
    {
        this.isObject = true;
        this.whichFile = "set_closed_days.json";
        this.currentRequest = new SetClosedDaysRequest(this.userID, this.userPassword, (int) startDate, (int) endDate);
    }

    public void delete_visit_type_from_place(String city, String address, String visitType) 
    {
        this.isObject = true;
        this.whichFile = "delete_visit_type_from_place.json";
        this.currentRequest = new DeleteVisitTypeFromPlaceRequest(this.userID, this.userPassword, city, address, visitType);
    }

    public void set_user_subscription_to_event(List<String> users, String eventName, Integer startDate) 
    {
        this.isObject = true;
        this.whichFile = "set_user_subscription_to_event.json";
        this.currentRequest = new SetUserSubscriptionToEventRequest(this.userID, this.userPassword, eventName, startDate, users);
    }

    public void delete_user_subscription_to_event(String eventName, Integer startDate) 
    {
        this.isObject = true;
        this.whichFile = "delete_user_subscription_to_event.json";
        this.currentRequest = new DeleteUserSubscriptionToEventRequest(this.userID, this.userPassword, eventName, startDate);
    }
}