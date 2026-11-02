package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 
import Comunication.DatabaseObjects.Event;
import Comunication.DatabaseObjects.EventInstance;

public class DeleteEventRequest extends AuthenticatedRequest
{
    private Event event;
    private List<EventInstance> instances;

    public DeleteEventRequest
    (
        String userID,
        String password,
        String name
    ) {
        this(userID, password, name, "", "", "", "", "", "");
    }

    public DeleteEventRequest
    (
        String userID,
        String password,
        String name,
        String description,
        String visitType,
        String organization,
        String city,
        String address,
        String rendezvous
    ) {
        super(ComunicationType.DELETE_EVENT, userID, password);
        
        this.instances = new ArrayList<>();
        
        this.event = new Event(
            name,
            description,
            visitType,
            organization,
            city,
            address,
            rendezvous,
            this.instances
        );
        
        json.put("event", event.getJSONObject());
    }
}