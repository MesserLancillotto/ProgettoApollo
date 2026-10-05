package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class SetMaximumFriendsGUI extends RequestGUI {

    public SetMaximumFriendsGUI() 
    {
        this.successMessage = "Il numero massimo di amici è stato aggiornato con successo.";
        this.errorMessage = "Si è verificato un errore durante l'aggiornamento del numero massimo di amici. Si prega di riprovare.";
        this.title = "Imposta Massimo Amici";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Numero massimo di amici", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String maxFriendsStr = inputMap.get("Numero massimo di amici").getText().trim();

            int maxFriends;
            try {
                maxFriends = Integer.parseInt(maxFriendsStr);
                if (maxFriends < 0) {
                    throw new NumberFormatException("Il numero non può essere negativo");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Inserire un numero intero valido maggiore o uguale a 0.",
                    "Input non valido",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Client.getInstance().setMaximumFriends(maxFriends);

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
