package User.Configurator;

import Client.Client;
import User.Interfaces.*;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class GetEventGUI extends RequestGUI {

    public GetEventGUI() 
    {
        this.successMessage = "La richiesta dell'evento è stata inviata con successo.";
        this.errorMessage = "Si è verificato un errore durante l'invio della richiesta sui dati dell'evento. Si prega di riprovare."; 
        this.title = "Richiesta Evento";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Stato", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String state = inputMap.get("Stato\n").getText();

            Client.getInstance().getEvent(state);
            
            String response = Client.getInstance().makeServerRequest();
            System.out.println("Risposta dal server: " + response);

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.has("updateSuccessful") 
                && jsonResponse.getBoolean("updateSuccessful")) 
            {
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