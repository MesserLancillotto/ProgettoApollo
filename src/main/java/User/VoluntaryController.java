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
    }

    private void handle_disponibility_dates ()
    {
        view.setOnDisponibilitaSavedListener(dateSelezionate ->
        {
            List<Integer> dateInt = dateSelezionate.stream()
                    .map(Long::intValue) // Converte ogni Long nel suo valore int
                    .collect(Collectors.toList()); // Raccoglie tutto in una nuova Lista
            Client.getInstance().set_disponibility_request(dateInt);
            if (!check_server_response())
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
