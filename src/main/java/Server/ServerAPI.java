package Server;

import java.io.*;
import java.net.*;
import java.util.function.Function;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Server.Engine.Interfaces.EngineInterface;
import Server.Engine.*;

class ServerAPI extends Thread
{
    private static final int PORT = 8000;

    public void handleUserRequest() 
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept();
             DataInputStream dataInputStream = new DataInputStream(
                 new BufferedInputStream(socket.getInputStream()));
             DataOutputStream dataOutputStream = new DataOutputStream(
                 socket.getOutputStream()))
        {
            System.out.println("Server started on port " + PORT);
            serverSocket.setReuseAddress(true);
            System.out.println("Connection from device " 
                + socket.getInetAddress().getHostAddress());
            String request = dataInputStream.readUTF();
            System.out.println("Incoming payload: " + request);
            String response = userResponse(request);
            dataOutputStream.writeUTF(response);
            dataOutputStream.flush();
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
            if (engineCreator == null) 
            {
                return errorResponse("Unknown communication type: " + type);
            }
            
            EngineInterface engine = engineCreator.apply(_request);
            
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
    
    @Override
    public void run()
    {
        handleUserRequest();
    }
}