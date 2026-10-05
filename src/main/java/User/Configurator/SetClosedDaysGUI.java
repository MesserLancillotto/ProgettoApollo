package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SetClosedDaysGUI extends RequestGUI {

    private static final DateTimeFormatter[] DATE_FORMATTERS = new DateTimeFormatter[] {
        DateTimeFormatter.ISO_LOCAL_DATE,                 // yyyy-MM-dd
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),        // dd/MM/yyyy
        DateTimeFormatter.ofPattern("dd-MM-yyyy")         // dd-MM-yyyy
    };

    public SetClosedDaysGUI() 
    {
        this.successMessage = "I giorni di chiusura sono stati impostati con successo.";
        this.errorMessage = "Si è verificato un errore durante l'impostazione dei giorni di chiusura. Si prega di riprovare.";
        this.title = "Imposta Giorni di Chiusura";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Data inizio (YYYY-MM-DD o timestamp)", false);
        this.fields.put("Data fine (YYYY-MM-DD o timestamp)", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String startInput = inputMap.get("Data inizio (YYYY-MM-DD o timestamp)").getText().trim();
            String endInput = inputMap.get("Data fine (YYYY-MM-DD o timestamp)").getText().trim();

            if (startInput.isEmpty() || endInput.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Entrambe le date sono obbligatorie.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            long startTimestamp;
            long endTimestamp;

            try {
                startTimestamp = parseDateToEpochSeconds(startInput, false);
                endTimestamp = parseDateToEpochSeconds(endInput, true);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Formato data non valido. Inserire una data nel formato YYYY-MM-DD, DD/MM/YYYY o un timestamp Unix.",
                    "Formato non valido",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (startTimestamp > endTimestamp) {
                JOptionPane.showMessageDialog(
                    frame,
                    "La data di inizio non può essere successiva alla data di fine.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Client.getInstance().set_closed_days(startTimestamp, endTimestamp);

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

    private long parseDateToEpochSeconds(String input, boolean endOfDay) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException ignored) {
        }

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(input, formatter);
                if (endOfDay) {
                    return date.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toEpochSecond();
                } else {
                    return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
                }
            } catch (Exception ignored) {
            }
        }

        throw new IllegalArgumentException("Invalid date format: " + input);
    }
}
