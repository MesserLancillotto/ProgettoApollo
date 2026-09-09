package User;

import Client.Client;

import java.util.List;

public class FunctionVoluntaryConfEventsController extends FunctionController<Boolean>
{
    private VoluntaryModel model;
    private IVoluntaryView view;

    public FunctionVoluntaryConfEventsController (VoluntaryModel model, IVoluntaryView view)
    {
        this.model = model;
        this.view = view;
    }

    @Override
    public Boolean execute ()
    {
        try {
            Client.getInstance().get_event(null);
            String response = Client.getInstance().make_server_request();
            List<EventDTO> events = DataMapper.parseEvents(response);

            for (EventDTO event : events)
            {
                for (EventInstanceDTO instance : event.getInstances())
                {
                    if (instance.hasVoluntary(model.getUsername()))
                    {
                        view.addConfirmedVisitRow(
                                event.getName(),
                                event.getDescription(),
                                event.getRandezvous(),
                                instance.getStartDate(),
                                instance.getEndDate(),
                                instance.getUsers()
                        );
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
