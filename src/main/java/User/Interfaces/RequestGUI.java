package User.Interfaces;

import User.Interfaces.UserViewInterface;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class RequestGUI implements UserViewInterface {
    
    protected Map<String, Boolean> fields = new LinkedHashMap<>();
    protected String successMessage;
    protected String errorMessage;
    protected String title;
    protected JFrame frame;

    @Override
    public void paint(JSONObject jsonObject) {
        buildFieldsMap();
        
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame(title != null ? title : "Richiesta");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            int row = 0;

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.gridwidth = 2;
            frame.add(titleLabel, gbc);

            row++;
            gbc.gridwidth = 1;

            Map<String, JTextField> inputMap = new LinkedHashMap<>();

            for (Map.Entry<String, Boolean> entry : fields.entrySet()) 
            {
                String labelText = entry.getKey();
                boolean isPassword = entry.getValue();

                JLabel label = new JLabel(labelText + ":");
                JTextField textField = isPassword ? new JPasswordField(15) : new JTextField(15);
                
                inputMap.put(labelText, textField);

                gbc.gridx = 0;
                gbc.gridy = row;
                gbc.anchor = GridBagConstraints.EAST;
                frame.add(label, gbc);

                gbc.gridx = 1;
                gbc.gridy = row;
                gbc.anchor = GridBagConstraints.WEST;
                frame.add(textField, gbc);

                row++;
            }

            JButton submitButton = new JButton("Invia Richiesta");
            requestLogic(submitButton, inputMap);

            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            frame.add(submitButton, gbc);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    protected abstract void requestLogic(JButton submitButton, Map<String, JTextField> inputMap);
    protected abstract void buildFieldsMap();
}