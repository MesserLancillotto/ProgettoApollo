package User;

import Client.Client;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VoluntaryController
{
    private VoluntaryModel model;
    private VoluntaryView view;
    private ChangePswdView changePswView;
    private FunctionPasswordController changePswdController;
    public  VoluntaryController(VoluntaryView view, VoluntaryModel model)
    {
        this.model = model;
        this.view = view;
        if (model.getPasswordNeedsToBeChanged())
        {
            view.setVisible(false);
            changePswdController = new FunctionPasswordController(model, new ChangePswdView(), () -> view.setVisible(true));
        }
        view.addVisualizeTypesListener ( e -> handle_view_visit_type_associated());
        handle_disponibility_dates();
        view.addShowConfirmedVisitsListener (e -> handle_confirmed_events());
    }

    private void handle_confirmed_events()
    {
        view.clearConfirmedVisits();
        FunctionController controller = new FunctionVoluntaryConfEventsController(model, view);
        controller.execute();
    }

    private void handle_view_visit_type_associated ()
    {
        view.openVisitTypeCard(model.getAllowedVisits());
        Client.getInstance().get_event("CONFIRMED");
        String getEventRequest = Client.getInstance().make_server_request();
        try
        {
            JSONObject getEventResponse = new JSONObject(getEventRequest);
            if (getEventResponse.getBoolean("loginSuccessful"))
            {
                JSONArray events = getEventResponse.getJSONArray("events");
                for (int i = 0; i < events.length(); i++)
                {
                    JSONObject event = events.getJSONObject(i);
                    String eventName = event.getString("name");
                    String eventDescription = event.getString("description");
                    String eventCity = event.getString("city");
                    String eventAddress = event.getString("address");
                    String eventRandezvous = event.getString("randezvous");

                    JSONArray eventInstances = event.getJSONArray("instances");
                    for (int j = 0; j < eventInstances.length(); j++)
                    {
                        JSONObject eventInstance = eventInstances.getJSONObject(j);
                        Integer startDate = eventInstance.getInt("start_date");
                        Integer endDate = eventInstance.getInt("end_date");
                        Boolean isInEvent = false;
                        JSONArray voluntariesArray = eventInstance.getJSONArray("voluntaries");
                        List<String> eventUsers = new ArrayList<>();
                        for (int k = 0; k < voluntariesArray.length(); k++)
                        {
                            if (model.getUsername().equalsIgnoreCase(voluntariesArray.getString(k)))
                                isInEvent = true;
                        }
                        JSONArray eventUserArray = eventInstance.getJSONArray("event_users");
                        for (int l = 0; l < eventUserArray.length(); l++)
                        {
                            eventUsers.add(eventUserArray.getString(l));
                        }
                        if (isInEvent)
                        {
                            String luogoCompleto = eventCity + ", " + eventAddress + " (" + eventRandezvous + ")";

                            view.addConfirmedVisitRow(
                                    eventName,
                                    eventDescription,
                                    luogoCompleto, // Luogo combinato
                                    startDate,
                                    endDate,
                                    eventUsers
                            );
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            view.showMessage("Errore nella comunicazione col server");
        }
    }

    private void handle_disponibility_dates ()
    {
        view.setOnDisponibilitaSavedListener(dateSelezionate ->
        {
            List<Integer> dateInt = dateSelezionate.stream()
                    .map(Long::intValue) // Converte ogni Long nel suo valore int
                    .collect(Collectors.toList()); // Raccoglie tutto in una nuova Lista
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

    private boolean  check_server_response()
    {
        try
        {
            String newUserResponse = Client.getInstance().make_server_request();
            JSONObject response = new JSONObject(newUserResponse);
            if (response.getBoolean("loginSuccessful"))
            {
                if (response.getBoolean("updateSuccessful"))
                    return true;
                else
                    return false;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
