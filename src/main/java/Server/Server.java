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
    public static void main(String [] args)
    {

        GetEventRequest request = new GetEventRequest(
            "Lancillotto.Benacense.99",
            "password",
            "CONFIRMED"
        );

        System.out.println(request.toJSONString());

        AuthenticatedEngine engine 
            = new GetEventEngine(request.toJSONString());

        ReplyInterface reply = engine.handleRequest();

        System.out.println(reply.toJSONString());

    }
}