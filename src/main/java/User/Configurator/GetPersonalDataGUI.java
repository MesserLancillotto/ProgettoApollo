package User.Configurator;

import Client.Client;
import User.Interfaces.*;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class GetPersonalDataGUI extends RequestGUI 
{
    public GetPersonalDataGUI() 
    {
        this.title = "Visualizza dati personali";
    }

    @Override
    protected void buildFieldsMap() 
    {
        // Non serve riempire 'fields' perché non usiamo JTextField di input.
    }

    @Override
    public void paint(JSONObject jsonObject) 
    {
        JSONObject rawData = Client.getInstance().getUserData();
        if (rawData == null || !rawData.has("user")) 
        {
            JOptionPane.showMessageDialog(
                null, 
                "Errore nel caricamento dati personali, contattare i sistemisti",
                "Risposta dal Server", 
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JSONObject user = rawData.getJSONObject("user");

        // Utilizziamo LinkedHashMap per preservare l'ordine di visualizzazione
        Map<String, String> displayData = new LinkedHashMap<>();
        displayData.put("ID Utente", user.optString("userID", "-"));
        displayData.put("Nome", user.optString("name", "-"));
        displayData.put("Cognome", user.optString("surname", "-"));
        displayData.put("Ruolo", user.optString("role", "-"));
        displayData.put("Organizzazione", user.optString("organization", "-"));
        displayData.put("Città", user.optString("city", "-"));

        if (user.has("birth_dd") && user.has("birth_mm") && user.has("birth_yy")) 
        {
            String birthDate = String.format("%02d/%02d/%d", 
                user.getInt("birth_dd"), 
                user.getInt("birth_mm"), 
                user.getInt("birth_yy"));
            displayData.put("Data di Nascita", birthDate);
        } else {
            displayData.put("Data di Nascita", "-");
        }

        if (user.has("user_since")) 
        {
            long epoch = user.getLong("user_since");
            String dateStr = Instant.ofEpochSecond(epoch)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            displayData.put("Utente Dal", dateStr);
        } else {
            displayData.put("Utente Dal", "-");
        }

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            int row = 0;

            // Titolo
            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.gridwidth = 2;
            frame.add(titleLabel, gbc);

            row++;
            gbc.gridwidth = 1;

            // Generazione dinamica di Label Sinistra (Descrizione) e Label Destra (Valore)
            for (Map.Entry<String, String> entry : displayData.entrySet()) 
            {
                JLabel labelDesc = new JLabel(entry.getKey() + ":");
                labelDesc.setFont(new Font("Arial", Font.BOLD, 12));
                
                JLabel labelValue = new JLabel(entry.getValue());
                labelValue.setFont(new Font("Arial", Font.PLAIN, 12));

                gbc.gridx = 0;
                gbc.gridy = row;
                gbc.anchor = GridBagConstraints.EAST;
                frame.add(labelDesc, gbc);

                gbc.gridx = 1;
                gbc.gridy = row;
                gbc.anchor = GridBagConstraints.WEST;
                frame.add(labelValue, gbc);

                row++;
            }

            // Pulsante Chiudi al posto di "Invia Richiesta"
            JButton closeButton = new JButton("Chiudi");
            closeButton.addActionListener(e -> frame.dispose());

            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            frame.add(closeButton, gbc);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        // Metodo non utilizzato in questa vista di sola lettura
    }
}