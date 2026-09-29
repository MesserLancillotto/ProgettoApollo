package User.Configurator;

import Client.Client;
import Comunication.DatabaseObjects.Place;
import User.Interfaces.RequestGUI;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetPlacesGUI extends RequestGUI {

    public GetPlacesGUI() 
    {
        this.successMessage = "Ricerca luoghi completata con successo.";
        this.errorMessage = "Si è verificato un errore durante il recupero dei luoghi. Si prega di riprovare.";
        this.title = "Ricerca Luoghi";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Città", false);
        this.fields.put("Indirizzo", false);
        this.fields.put("Tipi Visita (separati da virgola)", false);
        this.fields.put("Stati (separati da virgola)", false);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String city = inputMap.get("Città").getText().trim();
            String address = inputMap.get("Indirizzo").getText().trim();
            String visitTypesRaw = inputMap.get("Tipi Visita (separati da virgola)").getText().trim();
            String statesRaw = inputMap.get("Stati (separati da virgola)").getText().trim();

            List<String> visitTypes = parseList(visitTypesRaw);
            List<String> states = parseList(statesRaw);

            Client client = Client.getInstance();
            client.getPlaces(city, address, visitTypes, states);

            String response = client.makeServerRequest();
            System.out.println("Risposta dal server: " + response);

            if(response == null || response.isEmpty())
            {
                JOptionPane.showMessageDialog(
                    frame,
                    this.errorMessage,
                    "Errore Rete",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            try 
            {
                JSONObject jsonResponse = new JSONObject(response);
                if(!jsonResponse.optBoolean("loginSuccessful", false)
                    || !jsonResponse.has("places"))
                {
                    JOptionPane.showMessageDialog(
                        frame,
                        this.errorMessage,
                        "Errore Autenticazione / Server",
                        JOptionPane.ERROR_MESSAGE);
                }
                
                List<Place> placesList = new ArrayList<>();
                JSONArray placesArray = jsonResponse.getJSONArray("places");
                for (int i = 0; i < placesArray.length(); i++) 
                {
                    JSONObject placeJson = placesArray.getJSONObject(i);
                    Place place = new Place(placeJson);
                    boolean matchesCity = city.isEmpty() || place.getCity().equalsIgnoreCase(city);
                    boolean matchesAddress = address.isEmpty() || place.getAddress().equalsIgnoreCase(address);
                    boolean matchesVisitType = visitTypes.isEmpty() || (place.getVisitType() != null && visitTypes.stream().anyMatch(vt -> vt.equalsIgnoreCase(place.getVisitType())));
                    if (matchesCity && matchesAddress && matchesVisitType) 
                    {
                        placesList.add(place);
                    }
                }
                if (placesList.isEmpty()) 
                {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Nessun luogo trovato per i filtri specificati.",
                        "Risultati Ricerca",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    StringBuilder sb = new StringBuilder("Luoghi trovati (" + placesList.size() + "):\n\n");
                    for (Place p : placesList) {
                        sb.append("• ").append(p.getCity())
                          .append(" - ").append(p.getAddress())
                          .append(" | Org: ").append(p.getOrganization())
                          .append(" | Visita: ").append(p.getVisitType())
                          .append("\n");
                    }
                    JOptionPane.showMessageDialog(
                        frame,
                        sb.toString(),
                        "Risultati Ricerca",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Errore nel parsing dei dati ricevuti dal server.",
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