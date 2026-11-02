package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import User.Interfaces.UserViewInterface;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SetNewOrganizationGUI extends RequestGUI {

    public SetNewOrganizationGUI() {
        this.title = "Configura Nuova Organizzazione";
        this.successMessage = "L'organizzazione è stata configurata con successo.";
        this.errorMessage = "Si è verificato un errore durante la configurazione dell'organizzazione. Si prega di riprovare.";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("Nome Organizzazione", false);
        this.fields.put("Territori di Competenza (separati da virgola)", false);
        this.fields.put("Numero massimo di amici", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String organization = inputMap.get("Nome Organizzazione").getText().trim();
            String territoriesRaw = inputMap.get("Territori di Competenza (separati da virgola)").getText().trim();
            String maxFriendsStr = inputMap.get("Numero massimo di amici").getText().trim();

            if (organization.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Inserire il nome dell'organizzazione.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int maxFriends = 0;
            if (!maxFriendsStr.isEmpty()) {
                try {
                    maxFriends = Integer.parseInt(maxFriendsStr);
                    if (maxFriends < 0) {
                        throw new NumberFormatException("Il numero non può essere negativo");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Inserire un numero intero valido maggiore o uguale a 0 per il numero massimo di amici.",
                        "Input non valido",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
            }

            List<String> territories = parseList(territoriesRaw);

            Client client = Client.getInstance();
            client.set_new_organization(organization, territories, maxFriends);

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

                    JSONObject userData = client.getUserData();
                    if (userData != null && userData.has("user")) {
                        userData.getJSONObject("user").put("organization", organization);
                    }

                    if (this.frame != null) {
                        this.frame.dispose();
                    }

                    UserViewInterface configuratorGUI = new ConfiguratorGUI();
                    configuratorGUI.paint(userData);
                    return;
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
