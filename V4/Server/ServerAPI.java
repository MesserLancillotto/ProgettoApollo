package Server;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import org.json.*;
import java.util.concurrent.*;

import RequestReply.Reply.*;
import RequestReply.Request.*;
import RequestReply.ComunicationType.*;
import RequestReply.UserRoleTitle.*;
import Server.Engine.*;

class ServerAPI extends Thread
{
    private static Socket socket = null;
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    private static final int PORT = 8000;

    public static final void handleUserRequest() 
    {
        try
        {
            System.out.println("Server started on port " + PORT);
            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();
            System.out.println("Connection from device " 
                + socket.getInetAddress().getHostAddress());
            dataInputStream = new DataInputStream(
                new BufferedInputStream(
                    socket.getInputStream()
                )
            );
            
            String request = dataInputStream.readUTF();
            System.out.println("Request:\n-----------");
            System.out.println(request);
            System.out.println("-----------");

            String response = userResponse(request);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(response);
            dataOutputStream.flush();
            dataInputStream.close();
            dataOutputStream.close();
            serverSocket.close();
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println("An error occurred: " + e);
        } 
    }

   private static String userResponse(String request)
{
    JSONObject dictionary = new JSONObject(request);
    ComunicationType comunicationType;
    try
    {
        comunicationType = ComunicationType.valueOf((String)dictionary.get("requestType"));
        String userID = dictionary.getString("userID"); 
        String userPassword = dictionary.getString("userPassword");
        Map<String, Object> filters = new HashMap<String, Object>();
        
        dictionary.remove("userID");
        dictionary.remove("userPassword");
        dictionary.remove("requestType");
        
        switch(comunicationType)
        {
            case SET_NEW_ORGANIZATION:
                JSONArray territoriesArray = dictionary.getJSONArray("territoriesOfCompetence");
                ArrayList<String> territories = new ArrayList<>();
                for (int i = 0; i < territoriesArray.length(); i++) {
                    territories.add(territoriesArray.getString(i));
                }
                return new SetNewOrganizationEngine(
                    userID,
                    userPassword,
                    dictionary.getString("organizationName"),
                    territories
                ).handleRequest();
                
            case SET_NEW_EVENT:
                return new SetNewEventEngine(
                    userID,
                    userPassword,
                    dictionary.getString("eventName"),
                    dictionary.getString("description"),
                    dictionary.getString("city"),
                    dictionary.getString("address"),
                    dictionary.getString("meetingPoint"),
                    dictionary.getInt("startDate"),
                    dictionary.getInt("endDate"),
                    dictionary.getString("organization"),
                    dictionary.getInt("minimumUsers"),
                    dictionary.getInt("maximumUsers"),
                    dictionary.getInt("maximumFriends"),
                    dictionary.getString("visitType"),
                    dictionary.getString("state"),
                    getStringArrayListFromJSON(dictionary.getJSONArray("visitDays")),
                    getIntegerArrayListFromJSON(dictionary.getJSONArray("startHour")),
                    getIntegerArrayListFromJSON(dictionary.getJSONArray("duration"))
                ).handleRequest();
                
            case SET_CLOSED_DAYS:
                return new SetClosedDaysEngine(
                    userID, 
                    userPassword,
                    dictionary.getInt("startDate"),
                    dictionary.getInt("endDate"),
                    dictionary.getString("organization")
                ).handleRequest();
                
            case SET_NEW_USER:
                return new SetNewUserEngine(
                    userID,
                    userPassword,
                    dictionary.getString("userName"),
                    dictionary.getString("newPassword"),
                    dictionary.getString("cityOfResidence"),
                    dictionary.getInt("birthYear"),
                    UserRoleTitle.USER
                ).handleRequest();
                
            case EDIT_EVENT:
                Map<String, Object> map = new HashMap<String, Object>();
                for(String key : dictionary.keySet())
                {
                    map.put(key, dictionary.get(key));
                }
                return new EditEventEngine(
                    userID,
                    userPassword,
                    map
                ).handleRequest();
                
            case EDIT_PASSWORD:
                return new EditPasswordEngine(
                    userID, 
                    userPassword,
                    dictionary.getString("newPassword")
                ).handleRequest();
                
            case GET_VOLUNTARIES:
                for (String key : dictionary.keySet()) 
                {
                   filters.put(key, dictionary.get(key));
                }
                return new GetVoluntariesEngine(
                    userID, 
                    userPassword,
                    filters
                ).handleRequest();
                
            case GET_EVENT:
                filters = new HashMap<String, Object>();
                for(String key : dictionary.keySet())
                {
                    filters.put(key, dictionary.get(key));
                }
                return new GetEventEngine(
                    filters
                ).handleRequest();
                
            case GET_USER_DATA:
                return new GetUserDataEngine(
                    userID, 
                    userPassword, 
                    dictionary.getString("target")
                ).handleRequest();
                
            case SET_DISPONIBILITY:
                return new SetDisponibilityEngine(
                    userID, 
                    userPassword,
                    dictionary.getString("event"),
                    dictionary.getInt("time")
                ).handleRequest();
                
            case DELETE_PLACE:
                return new DeletePlaceEngine(
                    userID,
                    userPassword,
                    dictionary.getString("city"),
                    dictionary.getString("address")
                ).handleRequest();
                
            case SET_VOLUNTARY_TO_EVENT:
                return new SetVoluntaryToEventEngine(
                    userID,
                    userPassword,
                    dictionary.getString("event"),
                    dictionary.getString("targetID"),
                    dictionary.getInt("time")
                ).handleRequest();
                
            case DELETE_VISIT_TYPE_FROM_PLACE:
                return new DeleteVisitTypeFromPlaceEngine(
                    userID,
                    userPassword,
                    dictionary.getString("city"),
                    dictionary.getString("address"),
                    dictionary.getString("visitType")
                ).handleRequest();
                
            case DELETE_VOLUNTARY:
                return new DeleteVoluntaryEngine(
                    userID,
                    userPassword,
                    dictionary.getString("targetID")
                ).handleRequest();
                
            case DELETE_SUBSCRIBED_VISIT:
                return new DeleteSubscribedVisitEngine(
                    userID, 
                    userPassword,  
                    dictionary.getString("event"),  
                    dictionary.getInt("time")     
                ).handleRequest();
            case SET_USER_TO_EVENT:
                JSONArray friendsArray = dictionary.getJSONArray("friends");
                ArrayList<String> friends = new ArrayList<>();
                for (int i = 0; i < friendsArray.length(); i++) {
                    friends.add(friendsArray.getString(i));
                }
                return new SetUserToEventEngine(
                    userID, 
                    userPassword,
                    friends,
                    dictionary.getString("event"),
                    dictionary.getInt("time")
                ).handleRequest();
            default:
                return "ERROR: Unknown request type";
        }
    } 
    catch(Exception e)
    {
        e.printStackTrace();
        return "ERROR";
    }
}

    public synchronized void run()
    {
        handleUserRequest();
    }

    private static ArrayList<String> getStringArrayListFromJSON(JSONArray jsonArray) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    private static ArrayList<Integer> getIntegerArrayListFromJSON(JSONArray jsonArray) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getInt(i));
        }
        return list;
    }

}

