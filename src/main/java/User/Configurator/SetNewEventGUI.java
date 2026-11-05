package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class SetNewEventGUI extends RequestGUI {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter FORMATTER_DATE_ONLY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        this.fields.put("Data inizio (es. 25/06/2026 15:30, facoltativo)", false);
        this.fields.put("Data fine (es. 25/06/2026 18:00, facoltativo)", false);
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
            String startStr = inputMap.get("Data inizio (es. 25/06/2026 15:30, facoltativo)").getText().trim();
            String endStr = inputMap.get("Data fine (es. 25/06/2026 18:00, facoltativo)").getText().trim();
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

            Integer startDate = parseHumanDate(startStr);
            if (!startStr.isEmpty() && startDate == null) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Formato data inizio non valido. Usa 'dd/MM/yyyy HH:mm' oppure 'dd/MM/yyyy'.",
                    "Input non valido",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Integer endDate = parseHumanDate(endStr);
            if (!endStr.isEmpty() && endDate == null) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Formato data fine non valido. Usa 'dd/MM/yyyy HH:mm' oppure 'dd/MM/yyyy'.",
                    "Input non valido",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
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

    private Integer parseHumanDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            LocalDateTime ldt;
            if (dateStr.length() <= 10) {
                ldt = java.time.LocalDate.parse(dateStr, FORMATTER_DATE_ONLY).atStartOfDay();
            } else {
                ldt = LocalDateTime.parse(dateStr, FORMATTER);
            }
            return (int) ldt.atZone(ZoneId.systemDefault()).toEpochSecond();
        } catch (DateTimeParseException e) {
            return null;
        }
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