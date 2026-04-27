package User;

import Client.Client;
import org.json.JSONObject;

import javax.swing.*;
import java.time.LocalDate;
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
        view.addAggiungiLuogoListener(e -> view.open_aggiungi_luogo_view(null));

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

            // Client.getInstance().remove_voluntary(toRemoveID);
            // check_server_response()...

            view.showMessage("Volontario " + toRemoveID + " rimosso con successo!");
            handle_manage_voluntaries();
        });

        view.clearVolontariList();

        try
        {
            view.addVolontarioRow("VOLUNTARY.Marco.Rossi.1985", List.of("PUBLIC", "EDUCATIONAL"));
            view.addVolontarioRow("VOLUNTARY.Anna.Bianchi.1991", List.of("PRIVATE", "GUIDED"));
            view.addVolontarioRow("VOLUNTARY.Luca.Verdi.1990", List.of("VIRTUAL"));
            view.addVolontarioRow("VOLUNTARY.Giulia.Neri.1988", new ArrayList<>());
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

            List<String> newVisitTypes = List.of("PUBLIC", "EDUCATIONAL", "PRIVATE", "VIRTUAL", "GUIDED");

            view.open_select_new_visit_type_dialog(selectedID, newVisitTypes, selectedType -> {
                System.out.println("--- AZIONE: AGGIUNGI TIPO DI VISITA A VOLONTARIO ---");
                System.out.println("Utente: " + selectedID + " | Nuovo tipo: " + selectedType);

                // Client.getInstance().add_visit_type_to_voluntary(selectedID, selectedType);

                view.showMessage("Tipo di visita '" + selectedType + "' aggiunto con successo a " + selectedID + "!");
                handle_add_typeOfVisit_to_voluntary();
            });
        });

        view.clearAddVisitVolontariList();

        try
        {
            view.addAddVisitVolontarioRow("VOLUNTARY.Marco.Rossi.1985", List.of("PUBLIC", "EDUCATIONAL"));
            view.addAddVisitVolontarioRow("VOLUNTARY.Anna.Bianchi.1991", List.of("PRIVATE", "GUIDED"));
            view.addAddVisitVolontarioRow("VOLUNTARY.Luca.Verdi.1990", List.of("VIRTUAL"));
            view.addAddVisitVolontarioRow("VOLUNTARY.Giulia.Neri.1988", new ArrayList<>());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            view.showMessage("Errore durante il caricamento dei volontari.");
        }
    }

    private void handle_close_voluntaries_disponibility()
    {
        Client.getInstance().close_voluntary_disponibility ();
        if (check_server_response())
        {
            view.showMessage("Raccolta disponibilità volontari aperta!");
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
                    System.out.println("Azione: Aggiungi tipo visita al luogo -> " + key);
                    view.showMessage("Funzione Aggiungi Tipo Visita in costruzione per: " + key);
                },
                // 2. Azione: Rimuovi tipo di visita da luogo
                () -> {
                    String key = view.getSelectedLuogoKey();
                    System.out.println("Azione: Rimuovi tipo visita dal luogo -> " + key);
                    view.showMessage("Funzione Rimuovi Tipo Visita in costruzione per: " + key);
                },
                // 3. Azione: Rimuovi luogo
                () -> {
                    String key = view.getSelectedLuogoKey();
                    int response = JOptionPane.showConfirmDialog(null,
                            "Vuoi davvero eliminare il luogo: " + key + "?",
                            "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        System.out.println("Azione: Rimosso luogo -> " + key);
                        view.showMessage("Luogo eliminato con successo!");
                        handle_gestione_luoghi_apri(); // Ricarica la lista
                    }
                }
        );

        view.clearLuoghiList();

        try {
            // Esempio dati per testare l'aggregazione (La View li unirà se la chiave Città+Indirizzo coincide)
            view.addLuogoRow("Roma", "Piazza del Colosseo, 1", "PUBLIC");
            view.addLuogoRow("Roma", "Piazza del Colosseo, 1", "GUIDED"); // Si aggregherà al primo
            view.addLuogoRow("Milano", "Piazza del Duomo", "EDUCATIONAL");
            view.addLuogoRow("Milano", "Piazza del Duomo", "EDUCATIONAL"); // Duplicato ignorato
            view.addLuogoRow("Firenze", "Piazzale degli Uffizi, 6", "PRIVATE");
            view.addLuogoRow("Venezia", "Piazza San Marco", null); // Luogo senza tipi di visita
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Errore durante il caricamento dei luoghi.");
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
        view.setVisible(false);
    }

    private void handle_closed_days_selected()
    {
        List <Long> unixClosedDays = view.getDatePrecluse();
        for  (Long unix : unixClosedDays)
        {
            System.out.println (unix);
        }
    }

    // PER GESTIONE INTERNA
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