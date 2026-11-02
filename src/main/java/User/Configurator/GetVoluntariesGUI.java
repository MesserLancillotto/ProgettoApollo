package User.Configurator;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.Dimension;
import java.util.Map;

public class GetVoluntariesGUI extends RequestGUI {

    public GetVoluntariesGUI() 
    {
        this.title = "Visualizza Volontari";
        this.successMessage = "Ricerca volontari completata con successo.";
        this.errorMessage = "Si è verificato un errore durante il recupero dei volontari. Si prega di riprovare.";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Nome", false);
        this.fields.put("Cognome", false);
        this.fields.put("Città", false);
        this.fields.put("Anno di nascita", false);
        this.fields.put("Più anziano dell'anno specificato? (true/false)", false);
        this.fields.put("Tipo di visita", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String name = inputMap.get("Nome").getText().trim();
            String surname = inputMap.get("Cognome").getText().trim();
            String city = inputMap.get("Città").getText().trim();
            String yearStr = inputMap.get("Anno di nascita").getText().trim();
            String olderThanStr = inputMap.get("Più anziano dell'anno specificato? (true/false)").getText().trim();
            String visitType = inputMap.get("Tipo di visita").getText().trim();

            Integer birthYear = null;
            if (!yearStr.isEmpty()) {
                try {
                    birthYear = Integer.parseInt(yearStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "L'anno di nascita deve essere un numero intero valido.",
                        "Input non valido",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
            }

            Boolean olderThanYear = null;
            if (!olderThanStr.isEmpty()) {
                olderThanYear = olderThanStr.equalsIgnoreCase("true")
                    || olderThanStr.equalsIgnoreCase("si")
                    || olderThanStr.equalsIgnoreCase("sì")
                    || olderThanStr.equalsIgnoreCase("1");
            }

            Client.getInstance().getVoluntaries(name, surname, city, birthYear, olderThanYear, visitType);

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
                if (!jsonResponse.optBoolean("loginSuccessful", false) || !jsonResponse.has("voluntaries")) {
                    JOptionPane.showMessageDialog(
                        frame,
                        this.errorMessage,
                        "Errore Server / Autenticazione",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                JSONArray voluntaries = jsonResponse.getJSONArray("voluntaries");
                if (voluntaries.length() == 0) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Nessun volontario trovato per i filtri specificati.",
                        "Risultati Ricerca",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    StringBuilder sb = new StringBuilder("Volontari trovati (" + voluntaries.length() + "):\n\n");
                    for (int i = 0; i < voluntaries.length(); i++) {
                        JSONObject v = voluntaries.getJSONObject(i);
                        sb.append("• UserID: ").append(v.optString("userID", "-"))
                          .append(" | Nome: ").append(v.optString("name", "")).append(" ").append(v.optString("surname", ""))
                          .append("\n  Città: ").append(v.optString("city", "-"))
                          .append(" | Nascita: ").append(v.optInt("birth_dd", 0)).append("/")
                          .append(v.optInt("birth_mm", 0)).append("/").append(v.optInt("birth_yy", 0))
                          .append(" | Org: ").append(v.optString("organization", "-"));

                        if (v.has("allowedVisits")) {
                            JSONArray visits = v.optJSONArray("allowedVisits");
                            if (visits != null && visits.length() > 0) {
                                sb.append("\n  Permessi/Visite: ");
                                for (int j = 0; j < visits.length(); j++) {
                                    Object item = visits.get(j);
                                    if (item instanceof JSONObject) {
                                        sb.append(((JSONObject) item).optString("name", item.toString()));
                                    } else {
                                        sb.append(item.toString());
                                    }
                                    if (j < visits.length() - 1) sb.append(", ");
                                }
                            }
                        }
                        sb.append("\n\n");
                    }

                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setEditable(false);
                    textArea.setCaretPosition(0);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 300));

                    JOptionPane.showMessageDialog(
                        frame,
                        scrollPane,
                        "Risultati Ricerca Volontari",
                        JOptionPane.INFORMATION_MESSAGE
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
