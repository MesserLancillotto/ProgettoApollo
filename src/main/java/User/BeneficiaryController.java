package User;

import Client.Client;

import java.util.List;

public class BeneficiaryController
{
    private IBeneficiaryView view;
    private BeneficiaryModel model;
    private IFirstAccessView firstAccessView;

    public BeneficiaryController(IBeneficiaryView view, BeneficiaryModel model)
    {
        this.view = view;
        this.model = model;

        if (model.getIsFirstAccess())
        {
            view.setVisible(false);
            firstAccessView = ViewFactory.getInstance().createFirstAccessView("Completa");
            firstAccessView.addConfirmListener(e -> handle_complete_registration());
        }

        view.addEffettuaPrenotazioneListener(e -> handle_make_booking());
        view.addPrenotaActionListener(e -> handle_book_clicked());

        // Listener per la gestione delle prenotazioni e disdetta
        view.addGestisciPrenotazioneListener(e -> handle_manage_booking());
        view.addDisdiciActionListener(e -> handle_disdici_click());
    }

    private void handle_manage_booking()
    {
        view.clearPrenotazioniAttive();

        Client.getInstance().get_event("CONFIRMED");
        String response = Client.getInstance().make_server_request();

        try {
            List<EventDTO> events = DataMapper.parseEvents(response);
            for (EventDTO event : events)
            {
                for (EventInstanceDTO instance : event.getInstances())
                {
                    view.addEventoDaDisdireRow(
                            event.getName(),
                            event.getDescription(),
                            event.getRandezvous(),
                            instance.getStartDate(),
                            instance.getEndDate()
                    );
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
        // Recupero i dati dell'evento selezionato nella schermata di gestione
        IBeneficiaryView.EventSelectionData data = view.getSelectedManageData();

        if (data == null)
        {
            view.showMessage("Seleziona prima un evento dalla lista per poter disdire!");
            return;
        }

        // Apro il popup di conferma passando la funzione da eseguire in caso l'utente accetti
        view.openCancelConfirmDialog(data, () -> {
            System.out.println("--- DISDETTA CONFERMATA ---");
            System.out.println("Evento da disdire: " + data.name);
            System.out.println("Data Inizio: " + data.startDate);

            Client.getInstance().delete_user_subscription_to_event(data.name, data.startDate);
            if (check_server_response())
            {
                view.showMessage("Prenotazione disdetta con successo!");
            }
            else
                view.showMessage("Errore, prenotazione non trovata!");

            // Aggiorna la lista dopo la disdetta per rimuovere l'evento cancellato
            handle_manage_booking();
        });
    }

    private void handle_book_clicked()
    {
        IBeneficiaryView.EventSelectionData data = view.getSelectedBookingData();

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
            if (DataMapper.isOperationSuccessful(makeBookingResponse))
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

        Client.getInstance().get_event("CONFIRMED");
        String response = Client.getInstance().make_server_request();
        try {
            List<EventDTO> events = DataMapper.parseEvents(response);
            for (EventDTO event : events)
            {
                for (EventInstanceDTO instance : event.getInstances())
                {
                    view.addEventoPrenotabileRow(
                            event.getName(),
                            event.getDescription(),
                            event.getRandezvous(),
                            instance.getStartDate(),
                            instance.getEndDate()
                    );
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
                    Client.getInstance().make_server_request();
                    view.setVisible(true);
                }
            }
        }

    }

    private boolean check_server_response()
    {
        return DataMapper.isOperationSuccessful(Client.getInstance().make_server_request());
    }
}