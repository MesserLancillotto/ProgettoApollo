package User.User;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class GetSubscribedEventsGUI extends RequestGUI {

    public GetSubscribedEventsGUI() {
        this.title = "Eventi Iscritti";
        this.errorMessage = "Errore durante il recupero degli eventi iscritti.";
    }

    @Override
    protected void buildFieldsMap() {
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) {
        submitButton.addActionListener(e -> {
            String targetID = Client.getInstance().getUserID();

            Client client = Client.getInstance();
            client.get_subscribed_events(targetID);

            String response = client.makeServerRequest();
            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.has("events")) {
                    JSONArray eventsArray = jsonResponse.getJSONArray("events");
                    StringBuilder sb = new StringBuilder("Eventi iscritti (" + eventsArray.length() + "):\n\n");
                    
                    for (int i = 0; i < eventsArray.length(); i++) {
                        JSONObject ev = eventsArray.getJSONObject(i);
                        sb.append("• Nome: ").append(ev.optString("name", "N/D")).append("\n")
                          .append("  Descrizione: ").append(ev.optString("description", "N/D")).append("\n")
                          .append("  Tipo Visita: ").append(ev.optString("visitType", "N/D")).append("\n")
                          .append("  Organizzazione: ").append(ev.optString("organization", "N/D")).append("\n")
                          .append("  Città: ").append(ev.optString("city", "N/D")).append("\n")
                          .append("  Indirizzo: ").append(ev.optString("address", "N/D")).append("\n")
                          .append("  Rendezvous: ").append(ev.optString("rendezvous", "N/D")).append("\n");

                        JSONArray instances = ev.optJSONArray("instances");
                        if (instances != null && instances.length() > 0) {
                            sb.append("  Istanze:\n");
                            for (int j = 0; j < instances.length(); j++) {
                                JSONObject inst = instances.getJSONObject(j);
                                sb.append("    - Inizio: ").append(inst.optInt("start_date", 0))
                                  .append(" | Fine: ").append(inst.optInt("end_date", 0))
                                  .append(" | Stato: ").append(inst.optString("state", "N/D"))
                                  .append("\n");
                            }
                        }
                        sb.append("\n-----------------------------------\n\n");
                    }

                    JTextArea textArea = new JTextArea(sb.toString(), 25, 50);
                    textArea.setEditable(false);
                    JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Eventi Iscritti", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, this.errorMessage, "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Errore nel parsing della risposta.", "Errore", JOptionPane.ERROR_MESSAGE);
            }

            if (this.frame != null) this.frame.dispose();
        });
    }
}