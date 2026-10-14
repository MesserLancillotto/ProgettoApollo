package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class SetVisitablePlacesGUI extends RequestGUI {

    public SetVisitablePlacesGUI() 
    {
        this.successMessage = "Il posto visitabile è stato aggiunto con successo.";
        this.errorMessage = "Si è verificato un errore durante l'inserimento del posto visitabile. Si prega di riprovare.";
        this.title = "Aggiungi Posto Visitabile";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Città", false);
        this.fields.put("Indirizzo", false);
        this.fields.put("Descrizione", false);
        this.fields.put("Tipo Visita", false);
        this.fields.put("Volontario predefinito", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String city = inputMap.get("Città").getText().trim();
            String address = inputMap.get("Indirizzo").getText().trim();
            String description = inputMap.get("Descrizione").getText().trim();
            String visitType = inputMap.get("Tipo Visita").getText().trim();
            String defaultVoluntary = inputMap.get("Volontario predefinito").getText().trim();

            if (city.isEmpty() || address.isEmpty() || description.isEmpty() 
                || visitType.isEmpty() || defaultVoluntary.isEmpty()) 
            {
                JOptionPane.showMessageDialog(
                    frame,
                    "Tutti i campi sono obbligatori.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Client.getInstance().setNewPlace(city, address, description, visitType, defaultVoluntary);

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
