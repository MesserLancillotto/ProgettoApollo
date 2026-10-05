package User;

import Client.Client;
import User.Interfaces.RequestGUI;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class SetNewPasswordGUI extends RequestGUI {

    public SetNewPasswordGUI() 
    {
        this.successMessage = "La password è stata modificata con successo.";
        this.errorMessage = "Si è verificato un errore durante la modifica della password. Si prega di riprovare.";
        this.title = "Modifica Password";
    }

    @Override
    protected void buildFieldsMap() 
    {
        this.fields.put("Nuova Password", true);
        this.fields.put("Conferma Password", true);
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        submitButton.addActionListener(e -> {
            String newPassword = inputMap.get("Nuova Password").getText();
            String confirmPassword = inputMap.get("Conferma Password").getText();

            if (newPassword == null || newPassword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame,
                    "La nuova password non può essere vuota.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Le due password inserite non coincidono.",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Client.getInstance().change_password(newPassword);

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
                    Client.getInstance().setUserPassword(newPassword);
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
}
