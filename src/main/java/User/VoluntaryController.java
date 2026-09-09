package User;

import Client.Client;

import java.util.List;
import java.util.stream.Collectors;

public class VoluntaryController
{
    private VoluntaryModel model;
    private IVoluntaryView view;
    private IChangePswdView changePswView;
    private FunctionPasswordController changePswdController;

    public VoluntaryController(IVoluntaryView view, VoluntaryModel model)
    {
        this.model = model;
        this.view = view;
        if (model.getPasswordNeedsToBeChanged())
        {
            view.setVisible(false);
            changePswdController = new FunctionPasswordController(model, ViewFactory.getInstance().createChangePswdView(), () -> view.setVisible(true));
            changePswdController.execute();
        }
        view.addVisualizeTypesListener(e -> handle_view_visit_type_associated());
        handle_disponibility_dates();
        view.addShowConfirmedVisitsListener(e -> handle_confirmed_events());
    }

    private void handle_confirmed_events()
    {
        view.clearConfirmedVisits();
        FunctionController<Boolean> controller = new FunctionVoluntaryConfEventsController(model, view);
        controller.execute();
    }

    private void handle_view_visit_type_associated()
    {
        view.openVisitTypeCard(model.getAllowedVisits());
        Client.getInstance().get_event("CONFIRMED");
        String getEventRequest = Client.getInstance().make_server_request();
        try
        {
            List<EventDTO> events = DataMapper.parseEvents(getEventRequest);
            for (EventDTO event : events)
            {
                for (EventInstanceDTO instance : event.getInstances())
                {
                    if (instance.hasVoluntary(model.getUsername()))
                    {
                        view.addConfirmedVisitRow(
                                event.getName(),
                                event.getDescription(),
                                event.getFullLocation(),
                                instance.getStartDate(),
                                instance.getEndDate(),
                                instance.getUsers()
                        );
                    }
                }
            }
        }
        catch (Exception e)
        {
            view.showMessage("Errore nella comunicazione col server");
        }
    }

    private void handle_disponibility_dates()
    {
        view.setOnDisponibilitaSavedListener(dateSelezionate ->
        {
            List<Integer> dateInt = dateSelezionate.stream()
                    .map(Long::intValue)
                    .collect(Collectors.toList());
            Client.getInstance().set_disponibility_request(dateInt);
            if (check_server_response())
            {
                view.showMessage("Date segnate con successo");
            }
            else
            {
                view.showMessage("Errore nella comunicazione col server");
            }
        });
    }

    private boolean check_server_response()
    {
        return DataMapper.isOperationSuccessful(Client.getInstance().make_server_request());
    }
}
