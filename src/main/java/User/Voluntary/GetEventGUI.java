package User.Voluntary;

import Client.Client;
import User.Interfaces.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class GetEventGUI extends RequestGUI {

    public GetEventGUI() 
    {
        this.successMessage = "Eventi recuperati con successo.";
        this.errorMessage = "Si è verificato un errore durante il recupero degli eventi. Si prega di riprovare."; 
        this.title = "Visualizza Eventi";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Stato", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            JTextField field = inputMap.get("Stato");
            String state = (field != null) ? field.getText().trim() : "";

            Client.getInstance().getEvent(state);
            
            String response = Client.getInstance().makeServerRequest();
            System.out.println("Risposta dal server: " + response);

            if (response == null || response.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame, 
                    this.errorMessage, 
                    "Errore Rete", 
                    JOptionPane.ERROR_MESSAGE
                );
                if (this.frame != null) this.frame.dispose();
                return;
            }

            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.has("events")) {
                    JSONArray eventsArray = jsonResponse.getJSONArray("events");
                    StringBuilder sb = new StringBuilder("Eventi trovati (" + eventsArray.length() + "):\n\n");
                    
                    for (int i = 0; i < eventsArray.length(); i++) {
                        JSONObject ev = eventsArray.getJSONObject(i);
                        sb.append("=== Evento ").append(i + 1).append(" ===\n")
                          .append("• Nome: ").append(ev.optString("name", "N/D")).append("\n")
                          .append("• Descrizione: ").append(ev.optString("description", "N/D")).append("\n")
                          .append("• Tipo Visita: ").append(ev.optString("visitType", "N/D")).append("\n")
                          .append("• Organizzazione: ").append(ev.optString("organization", "N/D")).append("\n")
                          .append("• Città: ").append(ev.optString("city", "N/D")).append("\n")
                          .append("• Indirizzo: ").append(ev.optString("address", "N/D")).append("\n")
                          .append("• Ritrovo: ").append(ev.optString("rendezvous", "N/D")).append("\n");

                        if (ev.has("instances")) {
                            JSONArray instances = ev.getJSONArray("instances");
                            sb.append("• Istanze:\n");
                            for (int j = 0; j < instances.length(); j++) {
                                JSONObject inst = instances.getJSONObject(j);
                                sb.append("   - Inizio: ").append(inst.optInt("start_date", 0))
                                  .append(" | Fine: ").append(inst.optInt("end_date", 0))
                                  .append(" | Stato: ").append(inst.optString("state", "N/D")).append("\n");
                            }
                        }
                        sb.append("\n--------------------------------------------------\n\n");
                    }

                    JTextArea textArea = new JTextArea(sb.toString(), 20, 50);
                    textArea.setEditable(false);
                    JOptionPane.showMessageDialog(
                        frame, 
                        new JScrollPane(textArea), 
                        "Dettaglio Eventi", 
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
}