package Comunication.Request;

import org.json.*;
import java.util.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.AuthenticatedRequest; 
import Comunication.DatabaseObjects.Event;
import Comunication.DatabaseObjects.EventInstance;

public class DeleteEventInstanceRequest extends AuthenticatedRequest
{
    private Event event;
    private List<EventInstance> instances;

    public DeleteEventInstanceRequest
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
        super(ComunicationType.DELETE_EVENT_INSTANCE, userID, password);
        
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

    public void addInstance(
        Integer start_date,
        Integer end_date,
        String state,
        List<String> voluntaries,
        List<String> users
    ) {
        EventInstance instance = new EventInstance(start_date, end_date, state, voluntaries, users);
        this.instances.add(instance);
 
        JSONArray instancesJSON = new JSONArray();
        for(EventInstance inst : this.instances)
        {
            instancesJSON.put(inst.getJSONObject());
        }
        json.put("event", event.getJSONObject().put("instances", instancesJSON));
    }
}