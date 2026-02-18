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

        DeleteVoluntaryRequest request 
            = new DeleteVoluntaryRequest(
                "Lancillotto.Benacense.99",
                "password",
                "Arlecchino.Valcalepio.98"
            );

        System.out.println(request.toJSONString());

        DeleteVoluntaryEngine engine 
            = new DeleteVoluntaryEngine(request.toJSONString());

        ReplyInterface reply = engine.handleRequest();

        System.out.println(reply.toJSONString());

    }
}