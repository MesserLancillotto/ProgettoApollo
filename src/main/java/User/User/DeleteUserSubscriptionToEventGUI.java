package User.User;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class DeleteUserSubscriptionToEventGUI extends RequestGUI {

    public DeleteUserSubscriptionToEventGUI() {
        this.title = "Cancella Iscrizione Evento";
        this.successMessage = "Iscrizione all'evento cancellata con successo.";
        this.errorMessage = "Si è verificato un errore durante la cancellazione dell'iscrizione. Si prega di riprovare.";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("Nome Evento", false);
        this.fields.put("Data Inizio (Timestamp)", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String eventName = inputMap.get("Nome Evento").getText().trim();
            String dateStr = inputMap.get("Data Inizio (Timestamp)").getText().trim();

            if (eventName.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Inserire il nome dell'evento.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int date;
            try {
                date = Integer.parseInt(dateStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    frame,
                    "La data inizio (timestamp) deve essere un numero intero.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Client client = Client.getInstance();
            client.delete_user_subscription_to_event(eventName, date);

            String response = client.makeServerRequest();
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
