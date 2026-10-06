package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class SetNewVoluntaryGUI extends RequestGUI {

    public SetNewVoluntaryGUI() 
    {
        this.successMessage = "Il nuovo volontario è stato creato con successo.";
        this.errorMessage = "Si è verificato un errore durante l'inserimento del nuovo volontario. Si prega di riprovare.";
        this.title = "Aggiungi nuovo volontario";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Nome", false);
        this.fields.put("Cognome", false);
        this.fields.put("Città", false);
        this.fields.put("Data di nascita (dd/mm/YYYY)", false);
        this.fields.put("Password temporanea", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String name = inputMap.get("Nome").getText().trim();
            String surname = inputMap.get("Cognome").getText().trim();
            String city = inputMap.get("Città").getText().trim();
            String birthDate = inputMap.get("Data di nascita (dd/mm/YYYY)").getText().trim();
            String password = inputMap.get("Password temporanea").getText().trim();

            if (name.isEmpty() || surname.isEmpty() || city.isEmpty() 
                || birthDate.isEmpty() || password.isEmpty()) 
            {
                JOptionPane.showMessageDialog(
                    frame,
                    "Tutti i campi sono obbligatori.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Client.getInstance().setNewVoluntary(name, surname, city, birthDate, password);

            String response = Client.getInstance().makeServerRequest();
            System.out.println("Risposta dal server: " + response);

            if (response == null || response.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame,
                    this.errorMessage,
                    "Errore Rete",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.optBoolean("updateSuccessful", false)) {
                    JOptionPane.showMessageDialog(
                        frame,
                        this.successMessage,
                        "Risposta dal Server",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        frame,
                        this.errorMessage,
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Errore nel parsing della risposta del server.",
                    "Errore Formato JSON",
                    JOptionPane.ERROR_MESSAGE
                );
            }

            if (this.frame != null) {
                this.frame.dispose();
            }
        });
    }
}