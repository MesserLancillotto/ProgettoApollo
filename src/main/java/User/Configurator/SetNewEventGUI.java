package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class SetNewEventGUI extends RequestGUI {

    public SetNewEventGUI() {
        this.title = "Crea Nuovo Evento";
        this.successMessage = "L'evento è stato creato con successo.";
        this.errorMessage = "Si è verificato un errore durante la creazione dell'evento. Si prega di riprovare.";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("Nome evento", false);
        this.fields.put("Descrizione", false);
        this.fields.put("Tipo di visita", false);
        this.fields.put("Città", false);
        this.fields.put("Indirizzo", false);
        this.fields.put("Punto di ritrovo", false);
        this.fields.put("Data inizio (timestamp, facoltativo)", false);
        this.fields.put("Data fine (timestamp, facoltativo)", false);
        this.fields.put("Volontari (separati da virgola, facoltativo)", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String name = inputMap.get("Nome evento").getText().trim();
            String description = inputMap.get("Descrizione").getText().trim();
            String visitType = inputMap.get("Tipo di visita").getText().trim();
            String city = inputMap.get("Città").getText().trim();
            String address = inputMap.get("Indirizzo").getText().trim();
            String rendezvous = inputMap.get("Punto di ritrovo").getText().trim();
            String startStr = inputMap.get("Data inizio (timestamp, facoltativo)").getText().trim();
            String endStr = inputMap.get("Data fine (timestamp, facoltativo)").getText().trim();
            String voluntariesRaw = inputMap.get("Volontari (separati da virgola, facoltativo)").getText().trim();

            if (name.isEmpty() || visitType.isEmpty() || city.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame,
                    "I campi Nome evento, Tipo di visita, Città e Indirizzo sono obbligatori.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Integer startDate = null;
            Integer endDate = null;

            if (!startStr.isEmpty()) {
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
            }

            if (!endStr.isEmpty()) {
                try {
                    endDate = Integer.parseInt(endStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "La data di fine deve essere un timestamp numerico valido.",
                        "Input non valido",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
            }

            List<String> voluntaries = parseList(voluntariesRaw);

            String organization = "";
            JSONObject userData = Client.getInstance().getUserData();
            if (userData != null && userData.has("user")) {
                organization = userData.getJSONObject("user").optString("organization", "");
            }

            Client.getInstance().setNewEvent(
                name,
                description,
                visitType,
                organization,
                city,
                address,
                rendezvous,
                startDate,
                endDate,
                "CONFIRMED",
                voluntaries
            );

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
