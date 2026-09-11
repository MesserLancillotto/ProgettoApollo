package User.Configurator;

import Client.Client;
import User.Interfaces.*;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class GetAllowedVisitTypesGUI extends RequestGUI {

    public GetAllowedVisitTypesGUI() 
    {
        this.successMessage = "La richiesta dell'elenco dei tipi di visita consentiti è stata inviata con successo.";
        this.errorMessage = "Si è verificato un errore durante l'invio della richiesta. Si prega di riprovare."; 
        this.title = "Ottieni Tipi di Visita Consentiti";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("ID del Volontario", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String userID = inputMap.get("ID del Volontario").getText();

            Client.getInstance().getAllowedVisitTypes();
            
            String response = Client.getInstance().makeServerRequest();
            System.out.println("Risposta dal server: " + response);

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.has("updateSuccessful") && jsonResponse.getBoolean("updateSuccessful")) {
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
            if (this.frame != null) {
                this.frame.dispose();
            }
        });
    }
}