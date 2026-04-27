package User;

import Client.Client;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FunctionVoluntaryConfEventsController extends FunctionController
{
    private VoluntaryModel model;
    private VoluntaryView view;

    public FunctionVoluntaryConfEventsController (VoluntaryModel model, VoluntaryView view)
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
            JSONArray eventsArray = new JSONArray(response);

            for  (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                JSONArray eventInstancesArray = event.getJSONArray("instances");

                for (int k = 0; k < eventInstancesArray.length(); k++)
                {
                    JSONObject eventInstance = eventInstancesArray.getJSONObject(k);
                    JSONArray voluntariesArray = eventInstance.getJSONArray("voluntaries");

                    for (int l = 0; l < voluntariesArray.length(); l++)
                    {
                        if (model.getUsername().equalsIgnoreCase(voluntariesArray.getString(l)))
                        {
                            String eventName = event.getString("name");
                            String eventDescription = event.getString("description");
                            String eventRandezvous = event.getString("randezvous");
                            Integer eventStartDate = eventInstance.getInt("start_date");
                            Integer eventEndDate = eventInstance.getInt("end_date");

                            List<String> eventUsers = new ArrayList<>();
                            JSONArray eventUserArray = eventInstance.getJSONArray("users");

                            for (int m = 0; m < eventUserArray.length(); m++)
                            {
                                eventUsers.add(eventUserArray.getString(m));
                            }

                            view.addConfirmedVisitRow(eventName, eventDescription, eventRandezvous, eventStartDate, eventEndDate, eventUsers);
                        }
                    }
                }
            }
            return true; // Ha finito con successo
        } catch (Exception e) {
            e.printStackTrace();
            return false; // C'è stato un errore
        }
    }
}
