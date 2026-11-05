package User.Voluntary;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class GetSubscribedEventsGUI extends RequestGUI {

    public GetSubscribedEventsGUI() {
        this.title = "Eventi Iscritti";
        this.successMessage = "Eventi recuperati con successo.";
        this.errorMessage = "Si è verificato un errore durante il recupero degli eventi iscritti. Si prega di riprovare.";
    }

    @Override
    protected void buildFieldsMap() {
        this.fields.put("ID Utente (vuoto per utente corrente)", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String targetID = inputMap.get("ID Utente (vuoto per utente corrente)").getText().trim();
            if (targetID.isEmpty()) {
                targetID = Client.getInstance().getUserID();
            }

            Client client = Client.getInstance();
            client.get_subscribed_events(targetID);

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

                if (success && jsonResponse.has("events") && !jsonResponse.isNull("events")) {
                    JSONArray eventsArray = jsonResponse.getJSONArray("events");
                    if (eventsArray.length() == 0) {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Nessun evento iscritto trovato per l'utente: " + targetID,
                            "Eventi Iscritti",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        StringBuilder sb = new StringBuilder("Eventi a cui è iscritto " + targetID + " (" + eventsArray.length() + "):\n\n");
                        for (int i = 0; i < eventsArray.length(); i++) {
                            JSONObject ev = eventsArray.getJSONObject(i);
                            sb.append("• ").append(ev.optString("name", "N/D"))
                              .append(" | ").append(ev.optString("description", ""))
                              .append("\n  Org: ").append(ev.optString("organization", "-"))
                              .append(" | Visita: ").append(ev.optString("visitType", ev.optString("type", "-")))
                              .append(" | Luogo: ").append(ev.optString("city", "-")).append(", ").append(ev.optString("address", "-"))
                              .append(" | Ritrovo: ").append(ev.optString("rendezvous", "-"));

                            if (ev.has("instances")) {
                                JSONArray instances = ev.getJSONArray("instances");
                                if (instances.length() > 0) {
                                    sb.append("\n  Date:");
                                    for (int j = 0; j < instances.length(); j++) {
                                        JSONObject inst = instances.getJSONObject(j);
                                        sb.append(" [Inizio: ").append(inst.optInt("start_date"))
                                          .append(", Fine: ").append(inst.optInt("end_date"))
                                          .append(", Stato: ").append(inst.optString("state", "-"))
                                          .append("]");
                                    }
                                }
                            }
                            sb.append("\n\n");
                        }

                        JTextArea textArea = new JTextArea(sb.toString());
                        textArea.setEditable(false);
                        textArea.setLineWrap(true);
                        textArea.setWrapStyleWord(true);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

                        JOptionPane.showMessageDialog(
                            frame,
                            scrollPane,
                            "Eventi Iscritti",
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
