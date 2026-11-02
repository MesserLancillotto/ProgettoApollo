package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class DeleteEventInstanceGUI extends RequestGUI {

    public DeleteEventInstanceGUI() {
        this.title = "Elimina Istanza Evento";
        this.successMessage = "L'istanza dell'evento è stata eliminata con successo.";
        this.errorMessage = "Si è verificato un errore durante l'eliminazione dell'istanza dell'evento. Si prega di riprovare.";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("Nome evento", false);
        this.fields.put("Data inizio istanza (timestamp)", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String name = inputMap.get("Nome evento").getText().trim();
            String startStr = inputMap.get("Data inizio istanza (timestamp)").getText().trim();

            if (name.isEmpty() || startStr.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Tutti i campi sono obbligatori.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int startDate;
            try {
                startDate = Integer.parseInt(startStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    frame,
                    "La data di inizio deve essere un timestamp numerico valido.",
                    "Input non valido",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Client.getInstance().deleteEventInstance(name, startDate);

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
