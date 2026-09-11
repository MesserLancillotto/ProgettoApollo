package User;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.json.*;

import Client.Client;
import User.Interfaces.UserViewInterface;
import User.Configurator.ConfiguratorGUI;
import User.User.UserGUI;
import User.Voluntary.VoluntaryGUI;

public class Main {

    public static void foo(JFrame loginFrame, String username, char[] password) 
    {
        Client client = Client.getInstance();
        client.setUserID(username);
        client.setUserPassword(new String(password));

        client.get_personal_data(username);

        String responseJson = client.make_server_request();

        System.out.println("Response JSON: " + responseJson);

        JSONObject jsonObject = new JSONObject(responseJson);

        if (jsonObject.has("loginSuccessful") && jsonObject.getBoolean("loginSuccessful"))  
        {
            JOptionPane.showMessageDialog(null, "Login avvenuto con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
            
            try {
                Map<String, Supplier<UserViewInterface>> userInterfaces = new HashMap<>();
                userInterfaces.put("CONFIGURATOR", ConfiguratorGUI::new);
                userInterfaces.put("USER", UserGUI::new);
                userInterfaces.put("VOLUNTARY", VoluntaryGUI::new);

                String role = jsonObject.getJSONObject("user").getString("role");
                Supplier<UserViewInterface> supplier = userInterfaces.get(role);

                if (supplier != null) {
                    UserViewInterface ui = supplier.get();
                    ui.paint(jsonObject);
                    loginFrame.dispose();
                } else {
                    System.err.println("❌ Ruolo utente non riconosciuto: " + role);
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        } 
        else {
            JOptionPane.showMessageDialog(null, "Login Fallito", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel boxPanel = new JPanel();
            boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));

            Dimension fieldSize = new Dimension(200, 25);

            JLabel userLabel = new JLabel("Username", SwingConstants.CENTER);
            userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JTextField userField = new JTextField();
            userField.setHorizontalAlignment(JTextField.CENTER);
            userField.setAlignmentX(Component.CENTER_ALIGNMENT);
            userField.setMaximumSize(fieldSize);
            userField.setPreferredSize(fieldSize);

            JLabel passLabel = new JLabel("Password", SwingConstants.CENTER);
            passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPasswordField passField = new JPasswordField();
            passField.setHorizontalAlignment(JTextField.CENTER);
            passField.setAlignmentX(Component.CENTER_ALIGNMENT);
            passField.setMaximumSize(fieldSize);
            passField.setPreferredSize(fieldSize);

            JButton loginButton = new JButton("Invia");
            loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginButton.addActionListener(e -> foo(frame, userField.getText(), passField.getPassword()));

            boxPanel.add(userLabel);
            boxPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            boxPanel.add(userField);
            boxPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            boxPanel.add(passLabel);
            boxPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            boxPanel.add(passField);
            boxPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            boxPanel.add(loginButton);

            JPanel outerPanel = new JPanel(new GridBagLayout());
            outerPanel.add(boxPanel);

            frame.add(outerPanel);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize((int)(screenSize.width * 0.25), (int)(screenSize.height * 0.30));
            frame.setMinimumSize(new Dimension(300, 250));
            
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}