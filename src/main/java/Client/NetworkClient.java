package Client;

import java.io.*;
import java.net.*;

import Comunication.Request.GetPersonalDataRequest;
import Comunication.Request.Interfaces.RequestInterface;

public class NetworkClient 
{
    private static Socket socket = null;
    private static ServerSocket serverSocket = null;
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;

    private static final int PORT = 8000;
    private static final String SERVER_ADDR = "127.0.0.1";

    public static final String makeServerRequest(String server_addr, int port, String request) 
    {
        String response;
        try
        {
            socket = new Socket(server_addr, port);
            System.out.println("Connected");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(request);
            dataOutputStream.flush(); 
            dataInputStream = new DataInputStream(socket.getInputStream());
            response = dataInputStream.readUTF();
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            return response;
        }
        catch(Exception e) 
        {
            System.out.println("An error occurred: " + e);
        }
        return "";
    }

    public static void main(String [] args)
    {
        RequestInterface req = new GetPersonalDataRequest("Lancillotto.Benacense.99", "Altachiara");
        String resp = makeServerRequest("127.0.0.1", 8000, req.toJSONString());
        System.out.println(resp);
    }
}
