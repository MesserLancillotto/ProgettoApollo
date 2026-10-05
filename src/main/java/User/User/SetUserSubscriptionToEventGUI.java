package User.User;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SetUserSubscriptionToEventGUI extends RequestGUI {

    public SetUserSubscriptionToEventGUI() {
        this.title = "Iscrizione ad Evento";
        this.successMessage = "Iscrizione all'evento completata con successo.";
        this.errorMessage = "Si è verificato un errore durante l'iscrizione all'evento. Si prega di riprovare.";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("Nome Evento", false);
        this.fields.put("Data Inizio (Timestamp)", false);
        this.fields.put("Amici (separati da virgola, opzionale)", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String eventName = inputMap.get("Nome Evento").getText().trim();
            String dateStr = inputMap.get("Data Inizio (Timestamp)").getText().trim();
            String friendsRaw = inputMap.get("Amici (separati da virgola, opzionale)").getText().trim();

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

            List<String> friends = parseList(friendsRaw);

            Client client = Client.getInstance();
            client.set_user_subscription_to_event(friends, eventName, date);

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

    private List<String> parseList(String rawInput) {
        if (rawInput == null || rawInput.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(rawInput.split(","))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.toList());
    }
}
