package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class SetUserPermissionsGUI extends RequestGUI 
{
    public SetUserPermissionsGUI() 
    {
        this.successMessage = "Il permesso è stato aggiunto con successo.";
        this.errorMessage = "Si è verificato un errore durante l'inserimento del permesso. Si prega di riprovare.";
        this.title = "Aggiungi Permesso";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("UserID", false);
        this.fields.put("Permessi (separati da una virgola)", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String targetID = inputMap.get("UserID").getText().trim();
            String permitsString = inputMap.get("Permessi (separati da una virgola)").getText().trim();
            List<String> permits = parseList(permitsString);

            if (targetID.isEmpty() || permits.isEmpty()) 
            {
                JOptionPane.showMessageDialog(
                    frame,
                    "Tutti i campi sono obbligatori.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Client.getInstance().setUserPermissions(targetID, permits);

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
