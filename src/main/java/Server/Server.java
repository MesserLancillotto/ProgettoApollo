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
        testGetVoluntariesEngine();
    }

    private static void test()
    {
        System.out.println(request.toJSONString());
        ReplyInterface reply = engine.handleRequest();
        System.out.println(reply.toJSONString());
    }

    private static void testDeletePlaceEngie()
    {
        request = new DeletePlaceRequest(
            "Renzo.Tramaglino.94",
            "Milano",
            "Desenzano",
            "Via Castello 63", 
            "Cinema"
        );
        engine = new DeletePlaceEngine(request.toJSONString());
        test();

        request = new DeletePlaceRequest(
            "Arlecchino.Valcalepio.89",
            "vino&carte",
            "Desenzano",
            "Via Castello 63", 
            "Cinema"
        );
        engine = new DeletePlaceEngine(request.toJSONString());
        test();
        
        request = new DeletePlaceRequest(
            "Lancillotto.Benacense.99",
            "Altachiara",
            "Desenzano",
            "Via Castello 63", 
            "Cinema"
        );
        engine = new DeletePlaceEngine(request.toJSONString());
        test();

        request = new DeletePlaceRequest(
            "Lancillotto.Benacense.99",
            "Altachiara",
            "Desenzano",
            "Via Santa Maria del Mare 3", 
            "Cinema"
        );
        engine = new DeletePlaceEngine(request.toJSONString());
        test();
    }

    private static void testDeleteUserSubscriptionToEventEngie()
    {
        request = new DeleteUserSubscriptionToEventRequest(
            "Renzo.Tramaglino.94",
            "Milano",
            "Cinema in castello",
            1772830800
        );
        engine = new DeleteUserSubscriptionToEventEngine(
            request.toJSONString());
        test();

        request = new DeleteUserSubscriptionToEventRequest(
            "Renzo.Tramaglino.94",
            "Milano",
            "Cinema in castello",
            999999999
        );
        engine = new DeleteUserSubscriptionToEventEngine(
            request.toJSONString());
        test();

        request = new DeleteUserSubscriptionToEventRequest(
            "Renzo.Tramaglino.94",
            "Milano",
            "Cinema sulla luna",
            1772830800
        );
        engine = new DeleteUserSubscriptionToEventEngine(
            request.toJSONString());
        test();

        request = new DeleteUserSubscriptionToEventRequest(
            "Lancillotto.Benacense.99",
            "Altachiara",
            "Cinema in castello",
            1772830800
        );
        test();
    }

    private static void testGetVoluntariesEngine()
    {
        GetVoluntariesRequest _request = new GetVoluntariesRequest(
            "Lancillotto.Benacense.99",
            "Altachiara"
        );
        _request.withCity("Desenzano");
        request = _request;
        engine = new GetVoluntariesEngine(request.toJSONString());
        test();

        _request = new GetVoluntariesRequest(
            "Lancillotto.Benacense.99",
            "Altachiara"
        );
        _request.withYear(1990, true);
        request = _request;
        engine = new GetVoluntariesEngine(request.toJSONString());
        test();

        _request = new GetVoluntariesRequest(
            "Lancillotto.Benacense.99",
            "Altachiara"
        );
        _request.withYear(1990, false);
        request = _request;
        engine = new GetVoluntariesEngine(request.toJSONString());
        test();

        _request = new GetVoluntariesRequest(
            "Arlecchino.Valcalepio.89",
            "vino&carte"
        );
        request = _request;
        engine = new GetVoluntariesEngine(request.toJSONString());
        test();
    }
}