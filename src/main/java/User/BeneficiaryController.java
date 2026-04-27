package User;

import Client.Client;
import org.json.JSONArray;
import org.json.JSONObject;

public class BeneficiaryController
{
    private BeneficiaryView view;
    private BeneficiaryModel model;
    private FirstAccessView firstAccessView;

    public BeneficiaryController(BeneficiaryView view, BeneficiaryModel model)
    {
        this.view = view;
        this.model = model;

        if (model.getIsFirstAccess())
        {
            view.setVisible(false);
            firstAccessView = new FirstAccessView("Completa");
            firstAccessView.addConfirmListener(e -> handle_complete_registration());
        }

        view.addEffettuaPrenotazioneListener (e -> handle_make_booking ());
        view.addPrenotaActionListener(e -> handle_book_clicked());

        // Listener per la gestione delle prenotazioni e disdetta
        view.addGestisciPrenotazioneListener (e -> handle_manage_booking());
        view.addDisdiciActionListener(e -> handle_disdici_click());
    }

    private void handle_manage_booking()
    {
        view.clearPrenotazioniAttive();

        Client.getInstance().get_event(null);
        String response = Client.getInstance().make_server_request();

        try {
            JSONArray eventsArray = new JSONArray(response);
            for  (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                JSONArray eventInstancesArray = event.getJSONArray("instances");
                for (int k = 0; k < eventInstancesArray.length(); k++)
                {
                    JSONObject eventInstance = eventInstancesArray.getJSONObject(k);
                    String eventName = event.getString("name");
                    String eventDescription = event.getString("description");
                    String eventRandezvous = event.getString("randezvous");
                    Integer eventStartDate = eventInstance.getInt("start_date");
                    Integer eventEndDate = eventInstance.getInt("end_date");

                    // Aggiunge la riga nella schermata "Gestisci"
                    view.addEventoDaDisdireRow(eventName, eventDescription, eventRandezvous, eventStartDate, eventEndDate);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            view.showMessage("Errore nel caricamento degli eventi prenotati.");
        }
    }

    private void handle_disdici_click()
    {
        // 1. Recupera i dati dell'evento selezionato nella schermata di gestione
        BeneficiaryView.EventSelectionData data = view.getSelectedManageData();

        if (data == null)
        {
            view.showMessage("Seleziona prima un evento dalla lista per poter disdire!");
            return;
        }

        // 2. Apre il popup di conferma passando la funzione da eseguire in caso l'utente accetti
        view.openCancelConfirmDialog(data, () -> {
            System.out.println("--- DISDETTA CONFERMATA ---");
            System.out.println("Evento da disdire: " + data.name);
            System.out.println("Data Inizio: " + data.startDate);

            // chiamata al server per rimuovere la prenotazione
            // Client.getInstance().delete_booking(data.name, data.startDate);
            // Client.getInstance().make_server_request();

            view.showMessage("Prenotazione disdetta con successo!");

            // Aggiorna la lista dopo la disdetta per rimuovere l'evento cancellato
            handle_manage_booking();
        });
    }

    private void handle_book_clicked()
    {
        BeneficiaryView.EventSelectionData data = view.getSelectedBookingData();

        if (data == null)
        {
            view.showMessage("Seleziona prima un evento dalla lista per poter prenotare!");
            return;
        }

        int maxParticipants = Client.getInstance().get_max_people_subscription();

        view.openBookingDialog(data, maxParticipants, (eventName, startDate, friendsName) -> {

            System.out.println("--- DATI RICEVUTI DAL DIALOG ---");
            System.out.println("Evento: " + eventName);
            System.out.println("Data Inizio: " + startDate);
            System.out.println("Nominativi: " + friendsName.toString());

            Client.getInstance().set_user_subscription_to_event(friendsName, eventName, startDate);
            String makeBookingResponse = Client.getInstance().make_server_request();
            JSONObject response = new JSONObject(makeBookingResponse);
            if (response.getBoolean("updateSuccessful") && response.getBoolean("loginSuccessful"))
            {
                view.showMessage("Prenotazione per " + friendsName.size() + " partecipanti confermata con successo!");
            }
            else
            {
                view.showMessage("Errore! Prenotazione non riuscita!");
            }
        });
    }

    private void handle_make_booking ()
    {
        view.clearPrenotazioniDisponibili();

        Client.getInstance().get_event(null);
        String response = Client.getInstance().make_server_request();

        try {
            JSONArray eventsArray = new JSONArray(response);
            for  (int i = 0; i < eventsArray.length(); i++)
            {
                JSONObject event = eventsArray.getJSONObject(i);
                JSONArray eventInstancesArray = event.getJSONArray("instances");
                for (int k = 0; k < eventInstancesArray.length(); k++)
                {
                    JSONObject eventInstance = eventInstancesArray.getJSONObject(k);
                    String eventName = event.getString("name");
                    String eventDescription = event.getString("description");
                    String eventRandezvous = event.getString("randezvous");
                    Integer eventStartDate = eventInstance.getInt("start_date");
                    Integer eventEndDate = eventInstance.getInt("end_date");

                    view.addEventoPrenotabileRow(eventName, eventDescription, eventRandezvous, eventStartDate, eventEndDate);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            view.showMessage("Errore nel caricamento degli eventi.");
        }
    }

    private void handle_complete_registration ()
    {
        model.setName(UserInputValidator.format_String(firstAccessView.getName()));
        model.setSurname(UserInputValidator.format_String(firstAccessView.getSurname()));
        model.setCityOfResidence(UserInputValidator.format_String(firstAccessView.getCity()));
        String year = firstAccessView.getBirthYear().trim();
        if  (UserInputValidator.checkYearOfBirth(year))
        {
            model.setYearOfBirth (Integer.parseInt(year));
            if (UserInputValidator.passwordIsSafe(firstAccessView.getNewPassword()))
            {
                if (firstAccessView.getNewPassword().equals(firstAccessView.getConfirmPassword()))
                {
                    //Client.getInstance().manda_i_dati_al_server();
                    Client.getInstance().make_server_request();
                    view.setVisible(true);
                }
            }
        }

    }
}