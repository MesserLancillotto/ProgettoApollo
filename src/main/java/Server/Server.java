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

        AuthenticatedRequest request 
            = new DeleteVisitTypeFromPlaceRequest(
                "Lancillotto.Benacense.99",
                "password",
                "Brescia",
                "Via Castello 9",
                "Teatro"
            );

        System.out.println(request.toJSONString());

        AuthenticatedEngine engine 
            = new DeleteVisitTypeFromPlaceEngine(request.toJSONString());

        ReplyInterface reply = engine.handleRequest();

        System.out.println(reply.toJSONString());

    }
}