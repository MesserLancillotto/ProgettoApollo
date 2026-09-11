package User.Configurator;

import Client.Client;
import org.json.JSONObject;

import User.Interfaces.UserViewInterface;
import User.Interfaces.RequestGUI;

import javax.swing.*;
import java.util.Map;

public class DeleteVisitTypeFromPlaceGUI extends RequestGUI {

    public DeleteVisitTypeFromPlaceGUI() {
        this.successMessage = "La richiesta di eliminazione del tipo di visita associato al luogo è stata inviata con successo.";
        this.errorMessage = "Si è verificato un errore durante l'invio della richiesta di eliminazione del tipo di visita. Si prega di riprovare."; 
        this.title = "Elimina Tipo Visita";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("Città", false);
        this.fields.put("Indirizzo", false);
        this.fields.put("Tipo Visita", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String city = inputMap.get("Città").getText();
            String address = inputMap.get("Indirizzo").getText();
            String visitType = inputMap.get("Tipo Visita").getText();
            
            System.out.println("Città: " + city);
            System.out.println("Indirizzo: " + address);
            System.out.println("Tipo Visita: " + visitType);

            Client.getInstance().deleteVisitTypeFromPlace(city, address, visitType);
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