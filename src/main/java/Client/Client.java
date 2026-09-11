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

    public RequestInterface getCurrentRequest()
    {
        return this.request;
    }

    public String makeServerRequest()
    {
        return makeServerRequest(this.request);
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

    public void set_new_place(String city, String address, String description, String organization, String visitType, String defaultVoluntary) 
    {
        SetVisitablePlacesRequest req = new SetVisitablePlacesRequest(this.userID, this.userPassword);
        req.addPlace(city, address, description, organization, visitType, defaultVoluntary);
        this.request = req;
    }

    public void edit_visitable_places(String city, String address, String visitType, String newDefauldVoluntary) 
    {
        this.request = new EditVisitablePlacesRequest(this.userID, this.userPassword, city, address, visitType, newDefauldVoluntary);
    }

    public void set_new_organization(String organizationName, List<String> territoriesOfCompetence) 
    {
        this.request = new SetNewOrganizationRequest(this.userID, this.userPassword, organizationName, territoriesOfCompetence);
    }

    public void set_new_user(String userName, String newPassword, String cityOfResidence, Integer birthYear) 
    {
        this.request = new SetNewUserRequest(userName, newPassword, cityOfResidence, birthYear);
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
        this.request = new SetNewUserRequest(name, surname, password, city, birth_dd, birth_mm, birth_yy, user_since, role, organization);
    }

    public void change_password(String newPassword) 
    {
        this.request = new SetNewPasswordRequest(
            this.userID, 
            this.userPassword, 
            newPassword
        );
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

    public void get_voluntaries() 
    {
        this.request = new GetVoluntariesRequest(this.userID, this.userPassword);
    }

    public void get_places() 
    {
        this.request = new GetPlacesRequest(this.userID, this.userPassword);
    }

    public void get_personal_data(String target) 
    {
        this.request = new GetPersonalDataRequest(this.userID, this.userPassword);
    }

    public void get_subscribed_events(String targetID)
    {
        this.request = new GetSubscribedEventsRequest(this.userID, this.userPassword, targetID);
    }

    public void get_possible_visits(String organization)
    {
        this.request = new GetPossibleVisitsRequest(organization);
    }

    public void get_maximum_friends(String organization)
    {
        this.request = new GetMaximumFriendsRequest(organization);
    }

    public void get_allowed_visit_types()
    {
        this.request = new GetAllowedVisitTypesRequest(this.userID, this.userPassword);
    }

    public void remove_voluntary(String voluntaryID) 
    {
        delete_voluntary(voluntaryID);
    }

    public void delete_voluntary(String voluntaryID) 
    {
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
        this.request = new SetMaximumFriendsRequest(this.userID, this.userPassword, value);
        makeServerRequest();
    }

    public int get_max_people_subscription() 
    {
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

    public void set_new_event(
            String eventName, String description, String city, String address,
            String meetingPoint, int startDate, int endDate, String organizationName,
            int minimumUsers, int maximumUsers, int maximumFriends, String visitType,
            float price, ArrayList<String> visitDays, ArrayList<Integer> startHour,
            ArrayList<Integer> duration
    ) {
    }

    public void get_event(String state) 
    {
        this.request = new GetEventRequest(this.userID, this.userPassword, state);
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