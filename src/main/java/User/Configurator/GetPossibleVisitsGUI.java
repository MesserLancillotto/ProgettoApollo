package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class GetPossibleVisitsGUI extends RequestGUI {

    public GetPossibleVisitsGUI() 
    {
        this.successMessage = "Tipi di visita recuperati con successo.";
        this.errorMessage = "Si è verificato un errore durante il recupero dei tipi di visita. Si prega di riprovare.";
        this.title = "Visite Possibili Organizzazione";
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
            String organization = inputMap.get("Nome organizzazione").getText().trim();

            if (organization.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Inserire il nome dell'organizzazione.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Client.getInstance().getPossibleVisits(organization);

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
                boolean success = jsonResponse.optBoolean("querySuccesful", false) 
                    || jsonResponse.optBoolean("querySuccessful", false);

                if (success && jsonResponse.has("visitTypes")) {
                    JSONArray visitTypes = jsonResponse.getJSONArray("visitTypes");
                    if (visitTypes.length() == 0) {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Nessun tipo di visita disponibile per l'organizzazione: " + organization,
                            "Risultati Visite",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        StringBuilder sb = new StringBuilder("Tipi di visita possibili per " + organization + ":\n\n");
                        for (int i = 0; i < visitTypes.length(); i++) {
                            sb.append("• ").append(visitTypes.getString(i)).append("\n");
                        }
                        JOptionPane.showMessageDialog(
                            frame,
                            sb.toString(),
                            "Tipi di Visita",
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
