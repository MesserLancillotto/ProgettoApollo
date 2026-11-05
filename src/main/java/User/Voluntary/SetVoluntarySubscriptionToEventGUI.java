package User.Voluntary;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetVoluntarySubscriptionToEventGUI extends RequestGUI {

    public SetVoluntarySubscriptionToEventGUI() {
        this.title = "Iscrizione Evento Volontario";
        this.successMessage = "Iscrizione all'evento completata con successo.";
        this.errorMessage = "Errore durante l'iscrizione all'evento.";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("Nome Evento", false);
        this.fields.put("Data (Timestamp)", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String eventName = inputMap.get("Nome Evento").getText().trim();
            String dateStr = inputMap.get("Data (Timestamp)").getText().trim();

            if (eventName.isEmpty() || dateStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nome evento e data sono obbligatori.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int date;
            try {
                date = Integer.parseInt(dateStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "La data deve essere un numero intero (timestamp).", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Client client = Client.getInstance();
            client.set_voluntary_subscription_to_event(new ArrayList<String>(), eventName, date);

            String response = client.makeServerRequest();
            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.optBoolean("updateSuccessful", false)) {
                    JOptionPane.showMessageDialog(frame, this.successMessage, "Successo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, this.errorMessage, "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Errore nel parsing della risposta.", "Errore", JOptionPane.ERROR_MESSAGE);
            }

            if (this.frame != null) this.frame.dispose();
        });
    }
}