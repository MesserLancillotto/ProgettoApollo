package User.Configurator;

import Client.Client;
import User.Interfaces.*;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class GetMaximumFriendsGUI extends RequestGUI {

    public GetMaximumFriendsGUI() 
    {
        this.successMessage = "Numero massimo di amici: ";
        this.errorMessage = "Si è verificato un errore durante l'invio della richiesta sul numero di amici. Si prega di riprovare."; 
        this.title = "Richiesta numero massimo di amici";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Nome organizzazione", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String organization_name = inputMap.get("Nome organizzazione").getText();

            Client.getInstance().getMaximumFriends(organization_name);
            
            String response = Client.getInstance().makeServerRequest();
            System.out.println("Risposta dal server: " + response);

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.has("querySuccessful") 
                && jsonResponse.getBoolean("querySuccessful")) 
            {
                JOptionPane.showMessageDialog(
                    frame, 
                    new StringBuilder("Numero massimo di amici per ")
                        .append(inputMap.get("Nome organizzazione").getText())
                        .append(": ")
                        .append(new JSONObject(response)
                        .getInt("friendsNumber"))
                        .toString(),
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