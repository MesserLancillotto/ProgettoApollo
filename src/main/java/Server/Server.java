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

        AuthenticatedRequest request 
            = new DeleteUserSubscriptionToEventRequest(
                "Michele.Monteclarense.89",
                "campagna",
                "Cinema in castello",
                10
            );

        System.out.println(request.toJSONString());

        AuthenticatedEngine engine 
            = new DeleteUserSubscriptionToEventEngine(request.toJSONString());

        ReplyInterface reply = engine.handleRequest();

        System.out.println(reply.toJSONString());

    }
}