package User.Configurator;

import Client.Client;
import User.Interfaces.*;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Map;

public class GetPersonalDataGUI extends RequestGUI {

    public GetPersonalDataGUI() 
    {
        this.successMessage = "Dati personali: ";
        this.errorMessage = "Si è verificato un errore durante l'invio della richiesta sul numero di amici. Si prega di riprovare."; 
        this.title = "Visualizza dati personali";
    }

    @Override
    protected void buildFieldsMap() 
    {
    }

    @Override
    protected void requestLogic(JButton submitButton, Map<String, JTextField> inputMap) 
    {
        JOptionPane.showMessageDialog(
            frame, 
            new StringBuilder("Numero massimo di amici per ")
                .append(inputMap.get("Nome organizzazione").getText())
                .append(": ")
                .append(new JSONObject(response)
                .getInt("friendsNumber"))
                .toString(),
            "Risposta dal Server", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}