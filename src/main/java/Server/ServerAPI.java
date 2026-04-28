package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.Function;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Server.Engine.Interfaces.EngineInterface;
import Server.Engine.*;

class ServerAPI extends Thread
{
    private static Socket socket = null;
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    private static final int PORT = 8000;
    
    private static final Map<ComunicationType, Function<String, EngineInterface>> STRATEGY_MAP;
    
    static {
        STRATEGY_MAP = new EnumMap<>(ComunicationType.class);
        STRATEGY_MAP.put(ComunicationType.DELETE_PLACE, DeletePlaceEngine::new);
        STRATEGY_MAP.put(ComunicationType.DELETE_USER_SUBSCRIPTION_TO_EVENT, DeleteUserSubscriptionToEventEngine::new);
        // STRATEGY_MAP.put(ComunicationType.DELETE_VISIT, DeleteVisitEngine::new);
        STRATEGY_MAP.put(ComunicationType.DELETE_VOLUNTARY, DeleteVoluntaryEngine::new);
        STRATEGY_MAP.put(ComunicationType.EDIT_VISITABLE_PLACES, EditVisitablePlacesEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_ALLOWED_VISIT_TYPES, GetAllowedVisitTypesEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_EVENT, GetEventEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_PERSONAL_DATA, GetPersonalDataEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_PLACES, GetPlacesEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_SUBSCRIBED_EVENTS, GetSubscribedEventsEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_VOLUNTARIES, GetVoluntariesEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_CLOSED_DAYS, SetClosedDaysEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_DISPONIBILITY, SetDisponibilityEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_MAXIMUM_FRIENDS, SetMaximumFriendsEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_NEW_ORGANIZATION, SetNewOrganizationEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_NEW_PASSWORD, SetNewPasswordEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_USER_SUBSCRIPTION_TO_EVENT, SetUserSubscriptionToEventEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_VISITABLE_PLACES, SetVisitablePlacesEngine::new);
    }

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

    public static final String userResponse(String _request) 
    {
        try {
            JSONObject requestJson = new JSONObject(_request);
            
            ComunicationType type = ComunicationType.valueOf(
                requestJson.getString("comunicationType")
            );
            
            Function<String, EngineInterface> engineCreator = STRATEGY_MAP.get(type);
            if (engineCreator == null) {
                return errorResponse("Unknown communication type: " + type);
            }
            
            // Passa la stringa JSON originale al costruttore
            EngineInterface engine = engineCreator.apply(_request);
            
            // Esegui e restituisci il risultato
            return engine.handleRequest().toJSONString();
            
        } catch (Exception e) {
            return errorResponse("Error processing request: " + e.getMessage());
        }
    }
    
    private static String errorResponse(String message) {
        JSONObject error = new JSONObject();
        error.put("status", "error");
        error.put("message", message);
        return error.toString();
    }
    
    public synchronized void run()
    {
        handleUserRequest();
    }
}