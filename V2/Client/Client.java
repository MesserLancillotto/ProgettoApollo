package Client;

import java.io.*;
import java.net.*;
import java.util.*;

import RequestReply.Request.*;
import RequestReply.Reply.*;
import RequestReply.ComunicationType.*;
import RequestReply.UserRoleTitle.*;

public final class Client 
{
    private static final int PORT = 8000;
    private static final String SERVER_ADDR = "127.0.0.1";

    private String userID;
    private String userPassword;
    private RequestType body;
    private ComunicationType comunicationType;

    private static Client instance;

    private Client ()
    {
        
    }

    public static synchronized Client getInstance ()
    {
         if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void setUserID (String tmpuUserID)
    {
        userID = tmpuUserID;
    }

    public void setUserPassword (String tmpUserPassword)
    {
        userPassword = tmpUserPassword;
    }


    public void set_new_organization(String organizationName, ArrayList<String> territoriesOfCompetence) {
        this.body = new SetNewOrganizationRequest(organizationName, territoriesOfCompetence);
        this.comunicationType = ComunicationType.SET_NEW_ORGANIZATION;
    }

    public void set_new_event(
        String eventName,
        String description,
        String city,
        String address,
        String meetingPoint,
        int startDate,
        int endDate,
        String organizationName,
        int minimumUsers,
        int maximumUsers,
        int maximumFriends,
        String visitType,
        float price,
        ArrayList<String> visitDays,
        ArrayList<Integer> startHour,
        ArrayList<Integer> duration
    ) {
        this.body = new SetNewEventRequest(
            eventName,
            description,
            city,
            address,
            meetingPoint,
            startDate,
            endDate,
            organizationName,
            minimumUsers,
            maximumUsers,
            maximumFriends,
            visitType,
            price,
            visitDays,
            startHour,
            duration
        );
            this.comunicationType = ComunicationType.SET_NEW_EVENT;
    }

    public void set_closed_days(int startDate, int endDate, String organization) {
        this.body = new SetClosedDaysRequest(startDate, endDate, organization);
        this.comunicationType = ComunicationType.SET_CLOSED_DAYS;
    }

    public void set_new_user(
        String userName,
        String newPassword,
        String cityOfResidence,
        Integer birthYear
    ) {
        this.body = new SetNewUserRequest(
            userName,
            newPassword,
            cityOfResidence,
            birthYear
        );
        this.comunicationType = ComunicationType.SET_NEW_USER;
    }

    public void edit_event(String eventName, Map<String, Object> fields) {
        this.body = new EditEventRequest(eventName, fields);
        this.comunicationType = ComunicationType.EDIT_EVENT;
    }

    public void edit_password(String newPassword) {
        this.body = new EditPasswordRequest(newPassword);
        this.comunicationType = ComunicationType.EDIT_PASSWORD;
    }

    public void get_voluntaries(Map<String, Object> filters)
    {
        this.body = new GetVoluntariesRequest(filters);
        this.comunicationType = ComunicationType.GET_VOLUNTARIES;
    }

    public void get_event(Map<String, Object> filters) {
        this.body = new GetEventRequest(filters);
        this.comunicationType = ComunicationType.GET_EVENT;
    }
    
    public void get_user_data(String target) {
        this.body = new GetUserDataRequest(target);
        this.comunicationType = ComunicationType.GET_USER_DATA;
    }
    
    public void set_disponibility(        
        String event,
        int time
    ) {
        this.body = new SetDisponibilityRequest(event, time);
        this.comunicationType = ComunicationType.SET_DISPONIBILITY;
    }

// -----------------------------------------------------------------------

    public String make_server_request() {
        String request = new Request(comunicationType, userID, userPassword, body).toJSONString();
        String response = "";
        
        try (
            Socket socket = new Socket(SERVER_ADDR, PORT);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        ) {
            dataOutputStream.writeUTF(request);
            dataOutputStream.flush(); 
            response = dataInputStream.readUTF();
            return response;
        } catch(Exception e) {
            System.out.println("An error occurred: " + e);
        }
        return response;
    }
}
