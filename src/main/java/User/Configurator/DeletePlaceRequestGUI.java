package User.Configurator;

import Client.Client;

import User.Interfaces.RequestGUI;

import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class DeletePlaceRequestGUI extends RequestGUI {

    public DeletePlaceRequestGUI() {
        this.successMessage = "La richiesta di eliminazione del luogo è stata inviata con successo.";
        this.errorMessage = "Si è verificato un errore durante l'invio della richiesta di eliminazione del luogo. Si prega di riprovare."; 
        this.title = "Elimina Luogo";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("Città", false);
        this.fields.put("Indirizzo", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String city = inputMap.get("Città").getText();
            String address = inputMap.get("Indirizzo").getText();
            
            System.out.println("Città: " + city);
            System.out.println("Indirizzo: " + address);

            Client.getInstance().deletePlace(city, address);
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