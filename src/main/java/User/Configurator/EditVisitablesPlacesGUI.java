package User.Configurator;

import Client.Client;
import User.Interfaces.*;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class EditVisitablesPlacesGUI extends RequestGUI {

    public EditVisitablesPlacesGUI() 
    {
        this.successMessage = "La richiesta di modifica del posto è stata inviata con successo.";
        this.errorMessage = "Si è verificato un errore durante l'invio della richiesta di modifica del posto. Si prega di riprovare."; 
        this.title = "Modifica Posto Visitabile";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Città", false);
        this.fields.put("Indirizzo", false);
        this.fields.put("Tipo Visita", false);
        this.fields.put("Nuovo volontario assegnato", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String city = inputMap.get("Città").getText();
            String address = inputMap.get("Indirizzo").getText();
            String visitType = inputMap.get("Tipo Visita").getText();
            String newDefaultVoluntary = inputMap.get("Nuovo volontario assegnato").getText();

            Client.getInstance().editVisitablePlaces(city, address, visitType, newDefaultVoluntary);
            
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