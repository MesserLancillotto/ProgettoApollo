package User;

import Client.Client;
import org.json.JSONObject;

import java.util.List;

public class FunctionConfCompleteRegistrationController extends FunctionController
{
    private FirstAccessView view;
    private SetBasicAppInfoView setBasicAppInfoView;
    private ConfiguratorModel model;
    private Runnable onCompleteAction;

    public FunctionConfCompleteRegistrationController (FirstAccessView view, ConfiguratorModel model, Runnable onCompleteAction)
    {
        this.view = view;
        this.model = model;
        this.onCompleteAction = onCompleteAction;
    }

    public Boolean execute ()
    {
        // Recupero i valori grezzi prima di formattarli
        String rawName = view.getName();
        String rawSurname = view.getSurname();

        // Controlli di sicurezza
        if (rawName == null || rawName.trim().isEmpty())
        {
            view.showMessage("Attenzione: Il campo 'Nome' non può essere vuoto.");
            return false; // Blocca l'esecuzione e aspetta che l'utente corregga
        }

        if (rawSurname == null || rawSurname.trim().isEmpty())
        {
            view.showMessage("Attenzione: Il campo 'Cognome' non può essere vuoto.");
            return false; // Blocca l'esecuzione
        }

        model.setName(UserInputValidator.format_String(rawName));
        model.setSurname(UserInputValidator.format_String(rawSurname));
        model.setCityOfResidence(UserInputValidator.format_String(view.getCity()));

        String year = view.getBirthYear().trim();
        if  (UserInputValidator.checkYearOfBirth(year))
        {
            model.setYearOfBirth (Integer.parseInt(year));
            if (UserInputValidator.passwordIsSafe(view.getNewPassword()))
            {
                if (view.getNewPassword().equals(view.getConfirmPassword()))
                {
                    Client.getInstance().setUserPassword(view.getNewPassword());
                    Client.getInstance().set_new_user(model.getUserID(), view.getNewPassword(),
                            model.getCityOfResidence(), model.getYearOfBirth());

                    if (check_server_response() || true)
                    // SISTEMAAAAA = togli il true
                    {
                        view.setVisible(false);
                        setBasicAppInfoView = new SetBasicAppInfoView();
                        setBasicAppInfoView.addConfirmListener(e -> handle_basic_app_info_registration());
                    }
                    else
                    {
                        view.clearAllFields();
                        view.showMessage("Errore! Comunicazione non riuscita col server, riprova");
                    }
                }
                else
                {
                    view.clearPasswordFields();
                    view.showMessage("Le password non corrispondono");
                }
            }
            else
            {
                view.showMessage("La password inserita non rispetta i criteri\n" +
                        "Deve avere essere lunga almeno 8 caratteri, con una maiuscola, una minuscola e un numero");
                view.clearPasswordFields();
            }
        }
        else
        {
            view.showMessage("L'anno di nascita inserito non è valido");
        }

        return false;
    }

    private void handle_basic_app_info_registration()
    {
        // Recupero i dati dalla View
        String rawOrgName = setBasicAppInfoView.getOrganizationName();
        Integer maxPeople = setBasicAppInfoView.getMaxPeoplePerBooking();
        List<String> locations = setBasicAppInfoView.getLocations();

        // Controlli
        if (rawOrgName == null || rawOrgName.trim().isEmpty())
        {
            setBasicAppInfoView.showMessage("Errore: Il nome dell'organizzazione non può essere vuoto.");
        }
        else if (maxPeople == null || maxPeople < 1 || maxPeople > 30)
        {
            setBasicAppInfoView.showMessage("Errore: Il numero massimo di persone deve essere un numero compreso tra 1 e 30.");
        }
        else if (locations == null || locations.isEmpty())
        {
            setBasicAppInfoView.showMessage("Errore: Devi aggiungere almeno un luogo alla lista.");
        }
        else
        {
            // 3. TUTTI I CONTROLLI SUPERATI!
            // Formatta il nome: Prima lettera maiuscola, resto minuscola (es. "CEnTRo" -> "Centro")
            String finalOrganizationName = rawOrgName.substring(0, 1).toUpperCase() + rawOrgName.substring(1).toLowerCase();

            // Debug in console
            System.out.println("--- DATI CONFIGURAZIONE SALVATI ---");
            System.out.println("Organizzazione: " + finalOrganizationName);
            System.out.println("Max Persone: " + maxPeople);
            System.out.println("Luoghi: " + locations.toString());

            // CHIAMATE AL SERVER
            Client.getInstance().set_max_people_subscription(maxPeople);
            Client.getInstance().set_new_organization(finalOrganizationName, locations);
            if (check_server_response())
            {
                view.showMessage("Registrazione avvenuta con successo");
                onCompleteAction.run();
            }
            else
            {
                view.showMessage("Errore nella comunicazione col server");
            }
            setBasicAppInfoView.dispose();
        }
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
