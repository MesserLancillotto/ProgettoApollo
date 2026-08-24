package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.Function;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Server.Engine.Interfaces.EngineInterface;
import Server.Engine.*;

class EnginePureFabricatiorContext
{   
    private static final Map<ComunicationType, Function<String, EngineInterface>> STRATEGY_MAP;
    
    static {
        STRATEGY_MAP = new EnumMap<>(ComunicationType.class);
        STRATEGY_MAP.put(ComunicationType.DELETE_PLACE, DeletePlaceEngine::new);
        STRATEGY_MAP.put(ComunicationType.DELETE_USER_SUBSCRIPTION_TO_EVENT, DeleteUserSubscriptionToEventEngine::new);
        // STRATEGY_MAP.put(ComunicationType.DELETE_VISIT, DeleteVisitEngine::new);
        STRATEGY_MAP.put(ComunicationType.DELETE_VOLUNTARY, DeleteVoluntaryEngine::new);
        STRATEGY_MAP.put(ComunicationType.EDIT_VISITABLE_PLACES, EditVisitablePlacesEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_ALLOWED_VISIT_TYPES, GetAllowedVisitTypesEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_EVENT, GetEventEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_MAXIMUM_FRIENDS, GetMaximumFriendsEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_PERSONAL_DATA, GetPersonalDataEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_PLACES, GetPlacesEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_POSSIBLE_VISITS, GetPossibleVisitsEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_SUBSCRIBED_EVENTS, GetSubscribedEventsEngine::new);
        STRATEGY_MAP.put(ComunicationType.GET_VOLUNTARIES, GetVoluntariesEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_CLOSED_DAYS, SetClosedDaysEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_DISPONIBILITY, SetDisponibilityEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_MAXIMUM_FRIENDS, SetMaximumFriendsEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_NEW_ORGANIZATION, SetNewOrganizationEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_NEW_PASSWORD, SetNewPasswordEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_NEW_USER, SetNewUserEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_USER_SUBSCRIPTION_TO_EVENT, SetUserSubscriptionToEventEngine::new);
        STRATEGY_MAP.put(ComunicationType.SET_VISITABLE_PLACES, SetVisitablePlacesEngine::new);
    }

    public static Function<String, EngineInterface> createEngine(ComunicationType comunicationType)
    {
        return STRATEGY_MAP.get(comunicationType);
    }
}