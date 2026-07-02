package User;

import Client.Client;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.time.Instant;
import java.time.*;
import java.util.*;

public class ConfiguratorController
{
    private ConfiguratorView view;
    private FirstAccessView firstAccessView;
    private ConfiguratorModel model;

    public ConfiguratorController(ConfiguratorView view, ConfiguratorModel model)
    {
        this.model = model;
        this.view = view;
        if (model.getPasswordNeedsToBeChanged())
        {
            view.setVisible(false);
            firstAccessView = new FirstAccessView("Completa");
            firstAccessView.addConfirmListener(e -> handle_complete_registration());
        }

        // --- PRIMA SEZIONE: GESTIONE VOLONTARI ---
        view.addGestisciVolontariAzioneListener(e -> handle_manage_voluntaries());
        view.addConfirmCloseVoluntariesDisponibilties (e -> handle_close_voluntaries_disponibility ());
        view.addOpenVoluntaryDisponibility(e -> handle_open_voluntaries_disponibility());
        view.addAggiungiTipoVisitaVolontarioListener(e -> handle_add_typeOfVisit_to_voluntary());

        // --- SECONDA SEZIONE: GESTIONE LUOGHI ---
        view.addGestisciLuoghiAzioneListener(e -> handle_gestione_luoghi_apri());
        view.addAggiungiLuogoListener(e -> handle_apri_aggiungi_luogo());

        // --- TERZA SEZIONE: ALTRO ---
        view.addChangeMaxPeopleListener (e -> handle_change_max_people());
        view.addClosedDaysListener(e -> view.open_closed_days_view(f -> handle_closed_days_selected()));
    }

    // PER PRIMO ACCESSO
    private void handle_complete_registration()
    {
        FunctionController completeRegistration = new FunctionConfCompleteRegistrationController(firstAccessView, model, () -> view.setVisible(true));
        completeRegistration.execute();
    }

    // --- PRIMA SEZIONE: GESTIONE VOLONTARI ---

    private void handle_manage_voluntaries()
    {
        view.open_gestione_volontari_view(() -> {
            String toRemoveID = view.getSelectedVoluntaryID();

            System.out.println("--- AZIONE: RIMUOVI VOLONTARIO ---");
            System.out.println("Comando inviato al server per rimuovere: " + toRemoveID);

            Client.getInstance().delete_voluntary(toRemoveID);
            if (check_server_response())
            {
                view.showMessage("Volontario " + toRemoveID + " rimosso con successo!");
            }
            else
                view.showMessage("Errore di comunicazione col server, volontario non rimosso");

            handle_manage_voluntaries();
        });

        view.clearVolontariList();

        // CARICAMENTO VOLONTARI
        try
        {
            Client.getInstance().get_voluntaries();
            String getVoluntariesRequest = Client.getInstance().make_server_request();
            try
            {
                JSONObject getVoluntariesResponse = new JSONObject(getVoluntariesRequest);
                if (getVoluntariesResponse.getBoolean("loginSuccessful"))
                {
                    JSONArray voluntaries = getVoluntariesResponse.getJSONArray("voluntaries");
                    for (int m = 0; m < voluntaries.length(); m++)
                    {
                        JSONObject voluntary = voluntaries.getJSONObject(m);
                        JSONArray allowedVisitsArray = voluntary.getJSONArray("allowedVisits");
                        Set <String> allowedVisits = new HashSet<String>();

                        for (int i = 0; i < allowedVisitsArray.length(); i++)
                        {
                            allowedVisits.add(allowedVisitsArray.getString(i));
                        }
                        List<String> allowedVisitsList = new ArrayList<>(allowedVisits);
                        view.addVolontarioRow(voluntary.getString("userID"), allowedVisitsList);
                    }
                }
                else
                {
                    view.showMessage("Errore nel recupero dei volontari!");
                }
            }
            catch (Exception e)
            {
                view.showMessage("Errore di comunicazione col server!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            view.showMessage("Errore durante il caricamento dei volontari.");
        }
    }

    private void handle_add_typeOfVisit_to_voluntary()
    {
        view.open_add_visit_type_voluntary_view(() -> {
            String selectedID = view.getSelectedAddVisitVoluntaryID();
            Set<String> newVisitType = new HashSet<>();

            Client.getInstance().get_event(null);
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
                        newVisitType.add(event.getString("visitType").toUpperCase());
                    }
                }
            }
            catch (Exception e)
            {
                view.showMessage("Errore nella comunicazione col server");
            }

            view.open_select_new_visit_type_dialog(selectedID, newVisitType.stream().toList(), selectedType -> {
                System.out.println("--- AZIONE: AGGIUNGI TIPO DI VISITA A VOLONTARIO ---");
                System.out.println("Utente: " + selectedID + " | Nuovo tipo: " + selectedType);

                // Client.getInstance().add_visit_type_to_voluntary(selectedID, selectedType);

                view.showMessage("Tipo di visita '" + selectedType + "' aggiunto con successo a " + selectedID + "!");
                handle_add_typeOfVisit_to_voluntary();
            });
        });

        view.clearAddVisitVolontariList();

        // CARICAMENTO VOLONTARI PER AGGIUNGERE TIPO DI VISITA
        try
        {
            Client.getInstance().get_voluntaries();
            String getVoluntariesRequest = Client.getInstance().make_server_request();
            try
            {
                JSONObject getVoluntariesResponse = new JSONObject(getVoluntariesRequest);
                if (getVoluntariesResponse.getBoolean("loginSuccessful"))
                {

                    JSONArray voluntaries = getVoluntariesResponse.getJSONArray("voluntaries");
                    for (int m = 0; m < voluntaries.length(); m++)
                    {
                        JSONObject voluntary = voluntaries.getJSONObject(m);
                        JSONArray allowedVisitsArray = voluntary.getJSONArray("allowedVisits");

                        Set <String> allowedVisits = new HashSet<String>();

                        for (int i = 0; i < allowedVisitsArray.length(); i++)
                        {
                            allowedVisits.add(allowedVisitsArray.getString(i));
                        }
                        List<String> allowedVisitsList = new ArrayList<>(allowedVisits);
                        view.addAddVisitVolontarioRow(voluntary.getString("userID"), allowedVisitsList);
                    }
                }
                else
                {
                    view.showMessage("Errore nel recupero dei volontari!");
                }
            }
            catch (Exception e)
            {
                view.showMessage("Errore di comunicazione col serverAO!");
            }
        }
        catch (Exception e)
        {
            view.showMessage("Errore durante il caricamento dei volontari.");
        }
    }

    private void handle_close_voluntaries_disponibility()
    {
        Client.getInstance().close_voluntary_disponibility ();
        if (check_server_response())
        {
            view.showMessage("Raccolta disponibilità volontari chiusa!");
        }
        else
        {
            view.showMessage("Errore nella comunicazione col server");
        }
    }

    private void handle_open_voluntaries_disponibility()
    {
        int giornoAttuale = LocalDate.now().getDayOfMonth();

        if (giornoAttuale >= 16)
        {
            Client.getInstance().open_voluntary_disponibility ();
            if (check_server_response())
            {
                view.showMessage("Raccolta disponibilità volontari aperta!");
            }
            else
            {
                view.showMessage("Errore nella comunicazione col server");
            }
        }
        else
        {
            view.showMessage("Funzione ancora non disponibile, attendi il 16 del mese");
        }
    }

    // --- SECONDA SEZIONE: GESTIONE LUOGHI ---

    private void handle_gestione_luoghi_apri()
    {
        // Callback per i tre pulsanti a destra dell'elenco
        view.open_gestione_luoghi_view(
                // 1. Azione: Aggiungi tipo di visita a luogo
                () -> {
                    String key = view.getSelectedLuogoKey();
                    if (key == null) return; // Sicurezza

                    // a. Estrazione di city e address
                    String[] parts = key.split("\\|");
                    String city = parts[0];
                    String address = parts[1];

                    // b. Otteniamo dal server la lista di tutti i tipi di visita possibili
                    Set<String> newVisitType = new HashSet<>();
                    Client.getInstance().get_event(null);
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
                                newVisitType.add(event.getString("visitType").toUpperCase());
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        view.showMessage("Errore nella comunicazione col server durante il recupero dei tipi!");
                        return; // Interrompiamo se fallisce il caricamento
                    }

                    // c. Logica extra: rimuoviamo dal Set i tipi di visita che il luogo ha GIA' associati
                    List<String> tipiGiaPresenti = view.getSelectedLuogoVisitTypes();
                    newVisitType.removeAll(tipiGiaPresenti);

                    // Se dopo aver rimosso i duplicati non c'è più nulla da aggiungere
                    if (newVisitType.isEmpty()) {
                        view.showMessage("Questo luogo possiede già tutti i tipi di visita disponibili!");
                        return;
                    }

                    // d. Apriamo la finestra creata nella View passando i dati
                    view.open_add_visit_type_to_place_dialog(city, address, new ArrayList<>(newVisitType), selectedType -> {

                        System.out.println("Azione: Aggiungi tipo visita -> " + selectedType + " al luogo: " + key);


                        Client.getInstance().set_new_place(city, address, "Description", model.getOrganization(), selectedType, "VOLUNTARY.Mario.Rossi.1991");

                        if (check_server_response())
                        {
                            view.showMessage("Tipo di visita '" + selectedType + "' aggiunto con successo!");
                        }
                        else
                        {
                            view.showMessage("Errore di comunicazione col server!");
                        }

                        // Ricarica la lista per mostrare l'aggiornamento
                        handle_gestione_luoghi_apri();
                    });
                },
                // 2. Azione: Rimuovi tipo di visita da luogo
                () -> {
                    String key = view.getSelectedLuogoKey();

                    // a. Estrazione di city e address dalla chiave unita
                    String[] parts = key.split("\\|");
                    String city = parts[0];
                    String address = parts[1];

                    // b. Otteniamo i tipi di visita correntemente associati al luogo
                    List<String> visitTypes = view.getSelectedLuogoVisitTypes();

                    // Controllo di sicurezza: se non ci sono tipi di visita, avvisiamo l'utente
                    if (visitTypes.isEmpty()) {
                        view.showMessage("Non ci sono tipi di visita associati a questo luogo.");
                        return;
                    }

                    // c. Apriamo la nuova finestra passando i dati e gestiamo la risposta (Callback)
                    view.open_remove_visit_type_from_place_dialog(city, address, visitTypes, selectedType -> {

                        System.out.println("Azione: Rimuovi tipo visita -> " + selectedType + " dal luogo: " + key);

                        Client.getInstance().delete_place(city, address, selectedType);
                        if (check_server_response())
                        {
                            view.showMessage("Tipo di visita '" + selectedType + "' rimosso con successo!");
                        }
                        else
                        {
                            view.showMessage("Errore di comunicazione col server!");
                        }

                        // Ricarichiamo la pagina per mostrare i dati aggiornati
                        handle_gestione_luoghi_apri();
                    });
                },
                // 3. Azione: Rimuovi luogo
                () -> {
                    String key = view.getSelectedLuogoKey();

                    // a. Estrazione di city e address
                    String[] parts = key.split("\\|");
                    String city = parts[0];
                    String address = parts[1];

                    // b. Estrazione dei tipi di visita associati a quel luogo
                    List<String> visitTypes = view.getSelectedLuogoVisitTypes();

                    int response = JOptionPane.showConfirmDialog(null,
                            "Vuoi davvero eliminare il luogo:\nCittà: " + city + "\nIndirizzo: " + address + "?",
                            "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);

                    if (response == JOptionPane.YES_OPTION)
                    {
                        System.out.println("Azione: Rimosso luogo -> " + key);
                        System.out.println("Città: " + city);
                        System.out.println("Indirizzo: " + address);
                        System.out.println("Tipi di visita associati: " + visitTypes);

                        for (String type : visitTypes)
                        {
                            Client.getInstance().delete_place(city, address, type);
                            if (check_server_response())
                                view.showMessage("Luogo eliminato con successo!");
                            else
                                view.showMessage("Errore di comunicazione col server!");
                            handle_gestione_luoghi_apri(); // Ricarica la lista
                        }

                    }
                }
        );

        view.clearLuoghiList();

        try
        {
            Client.getInstance().get_places();
            String getPlacesRequest = Client.getInstance().make_server_request();
            try
            {
                JSONObject getPlacesResponse = new JSONObject(getPlacesRequest);
                if (getPlacesResponse.getBoolean("loginSuccessful"))
                {
                    JSONArray places = getPlacesResponse.getJSONArray("places");
                    for (int m = 0; m < places.length(); m++)
                    {
                        JSONObject place = places.getJSONObject(m);
                        view.addLuogoRow(place.getString("city"), place.getString("address"), place.getString("visitType"));
                    }
                }
                else
                {
                    view.showMessage("Errore nel recupero dei volontari!");
                }
            }
            catch (Exception e)
            {
                view.showMessage("Errore nella comunicazione col server");
            }
        }
        catch (Exception e)
        {
            view.showMessage("Errore durante il caricamento dei luoghi.");
        }
    }

    private void handle_apri_aggiungi_luogo() {
        // Apriamo la view passandogli il listener per il bottone di salvataggio
        view.open_aggiungi_luogo_view(e -> handle_salva_nuovo_luogo());
    }

    private void handle_salva_nuovo_luogo() {
        // 1. Recupero i dati dalla view
        String city = view.getNewPlaceCity();
        String address = view.getNewPlaceAddress();
        String description = view.getNewPlaceDescription();
        String organization = view.getNewPlaceOrganization();
        String visitType = view.getNewPlaceVisitType();
        String defaultVoluntary = view.getNewPlaceDefaultVoluntary();

        // 2. Validazione di base per evitare campi obbligatori vuoti
        if (city.isEmpty() || address.isEmpty() || visitType == null) {
            view.showMessage("Attenzione: Città, Indirizzo e Tipo Visita sono obbligatori!");
            return;
        }

        System.out.println("--- AZIONE: CREA NUOVO LUOGO ---");
        System.out.println("Città: " + city + " | Indirizzo: " + address + " | Tipo: " + visitType);

        // 3. Invio la richiesta al server
        Client.getInstance().set_new_place(city, address, description, organization, visitType, defaultVoluntary);

        // 4. Controllo la risposta
        if (check_server_response()) {
            view.showMessage("Nuovo luogo creato con successo!");
            view.close_aggiungi_luogo_view(); // Chiudiamo il popup

            // Ricarichiamo la lista dei luoghi in background per mostrare quello nuovo
            handle_gestione_luoghi_apri();
        } else {
            view.showMessage("Errore di comunicazione col server durante la creazione del luogo.");
        }
    }


    // --- TERZA SEZIONE : ALTRE FUNZIONI ---

    private void handle_change_max_people ()
    {
        int maxPeople = Client.getInstance().get_max_people_subscription();
        view.open_change_max_number_subrsctipion_view(maxPeople, e -> handle_max_people_sub_changed());
    }

    private void handle_max_people_sub_changed ()
    {
        int maxSubValue = Integer.parseInt(view.getNewMaxSub());
        Client.getInstance().set_max_people_subscription(maxSubValue);
        if (check_server_response())
        {
            this.view.showMessage("Numero massimo prenotazioni cambiato");
        }
        else
            this.view.showMessage("Errore! Modifica non eseguita");
        view.setVisible(false);
    }

    private void handle_closed_days_selected()
    {
        List <Long> unixClosedDays = view.getDatePrecluse();
        Boolean changeSuccessfull = true;
        for  (Long unix : unixClosedDays)
        {
            Client.getInstance().set_closed_days(getDayBoundaries(unix, true), getDayBoundaries(unix,false));
            if (changeSuccessfull && check_server_response())
                changeSuccessfull = true;
            else
                changeSuccessfull = false;
        }
        if (changeSuccessfull)
            this.view.showMessage("Giorni di chiusura aggiornati!");
        else
            this.view.showMessage("Errore! Modifica non eseguita");
    }

    // PER GESTIONE INTERNA
    public static long getDayBoundaries(long unixTimeMillis, Boolean isStartDay) {

        ZoneId zone = ZoneId.systemDefault();

        LocalDate date = Instant.ofEpochMilli(unixTimeMillis)
                .atZone(zone)
                .toLocalDate();

        ZonedDateTime startingDate = date.atTime(0, 1, 0).atZone(zone);
        ZonedDateTime endingDate = date.atTime(23, 59, 59).atZone(zone);

        if (isStartDay)
            return startingDate.toInstant().toEpochMilli();
        else
            return endingDate.toInstant().toEpochMilli();

    }

    private boolean  check_server_response()
    {
        try
        {
            String requestResponse = Client.getInstance().make_server_request();
            JSONObject response = new JSONObject(requestResponse);
            if (response.getBoolean("loginSuccessful"))
            {
                if (response.getBoolean("updateSuccessful"))
                {
                    return true;
                }
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