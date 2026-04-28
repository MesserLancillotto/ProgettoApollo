package Server;

import java.util.*;

import Server.Engine.Interfaces.*;
import Server.Engine.*;
import Comunication.Request.*;
import Comunication.Request.Interfaces.*;
import Comunication.Reply.*;
import Comunication.Reply.Interfaces.*;


import org.json.*;

public class Server
{

    private static Request request;
    private static AuthenticatedEngine engine;

    public static void main(String [] args)
    {
        while(true)
        {
            ServerAPI api = new ServerAPI();
            api.start(); 
            try {
                api.join();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e);
            }
        }        
    }
}
