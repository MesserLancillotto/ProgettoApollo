package User.Voluntary;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class GetAllowedVisitTypesGUI extends RequestGUI {

    public GetAllowedVisitTypesGUI() {
        this.title = "Visite Autorizzate";
        this.successMessage = "Tipi di visita autorizzati recuperati con successo.";
        this.errorMessage = "Si è verificato un errore durante il recupero dei tipi di visita autorizzati. Si prega di riprovare.";
    }

    @Override
    protected void buildFieldsMap() {
        // Nessun campo di input necessario per recuperare le visite autorizzate del volontario.
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            Client client = Client.getInstance();
            client.getAllowedVisitTypes();

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
                boolean success = jsonResponse.optBoolean("loginSuccessful", false);

                if (success && jsonResponse.has("visitTypes")) {
                    JSONArray visitTypes = jsonResponse.getJSONArray("visitTypes");
                    if (visitTypes.length() == 0) {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Nessun tipo di visita autorizzato per l'utente corrente.",
                            "Visite Autorizzate",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        StringBuilder sb = new StringBuilder("Tipi di visita autorizzati:\n\n");
                        for (int i = 0; i < visitTypes.length(); i++) {
                            sb.append("• ").append(visitTypes.getString(i)).append("\n");
                        }
                        JOptionPane.showMessageDialog(
                            frame,
                            sb.toString(),
                            "Visite Autorizzate",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
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
