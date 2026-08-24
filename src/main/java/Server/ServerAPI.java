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
            
            Function<String, EngineInterface> engineCreator 
                = EnginePureFabricatiorContext.createEngine(type);
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
    
    private static String errorResponse(String message) 
    {
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