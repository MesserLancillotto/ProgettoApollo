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
        List<String> visitTypes = new ArrayList<String>();
        List<String> userIDs = new ArrayList<String>();

        userIDs.add("Stefania.Arilicense.71");

        AuthenticatedRequest request 
            = new SetUserSubscriptionToEventRequest(
                "Michele.Monteclarense.89",
                "campagna",
                "Cinema in castello",
                10,
                userIDs
            );

        System.out.println(request.toJSONString());

        AuthenticatedEngine engine 
            = new SetUserSubscriptionToEventEngine(request.toJSONString());

        ReplyInterface reply = engine.handleRequest();

        System.out.println(reply.toJSONString());

    }
}