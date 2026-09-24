package User.Configurator;

import Client.Client;
import org.json.JSONObject;

import User.Interfaces.UserViewInterface;
import User.Interfaces.RequestGUI;

import javax.swing.*;
import java.util.Map;

public class DeleteVoluntaryGUI extends RequestGUI {

    public DeleteVoluntaryGUI() 
    {
        this.successMessage = "La richiesta di eliminazione dell'utente %s è stata inviata con successo.";
        this.errorMessage = "Si è verificato un errore durante l'invio della richiesta di eliminazione dell'utente. Si prega di riprovare."; 
        this.title = "Elimina Utente";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("UserID", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String userID = inputMap.get("UserID").getText();

            System.out.println("UserID: " + userID);

            Client.getInstance().deleteVoluntary(userID);
            String response = Client.getInstance().makeServerRequest();
            System.out.println("Risposta dal server: " + response);

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.has("updateSuccessful") && jsonResponse.getBoolean("updateSuccessful")) {
                JOptionPane.showMessageDialog(
                    frame, 
                    String.format(this.successMessage,this.fields.put("UserID", false)), 
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