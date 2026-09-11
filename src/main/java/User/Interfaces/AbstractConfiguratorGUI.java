package User.Configurator;

import User.Interfaces.UserViewInterface;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConfiguratorGUI implements UserViewInterface {

    protected Map<String, ActionConfig> configMap = new HashMap<>();

    @FunctionalInterface
    protected interface ButtonAction 
    {
        void execute();
    }

    protected static class ActionConfig 
    {
        String buttonText;
        String descriptionText;
        ButtonAction action;

        public ActionConfig(String buttonText, String descriptionText, ButtonAction action) 
        {
            this.buttonText = buttonText;
            this.descriptionText = descriptionText;
            this.action = action;
        }
    }

    private Map<String, ActionConfig> getConfigurations() 
    {
        return configMap;
    }

    @Override
    public void paint(JSONObject jsonObject) 
    {
        String userName = jsonObject.getJSONObject("user").getString("name");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Configurator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel welcomeLabel = new JLabel("Benvenuto " + userName, SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            frame.add(welcomeLabel, gbc);

            gbc.gridwidth = 1;
            int row = 1;

            Map<String, ActionConfig> configs = getConfigurations();

            for (Map.Entry<String, ActionConfig> entry : configs.entrySet()) 
            {
                ActionConfig config = entry.getValue();

                JButton button = new JButton(config.buttonText);
                button.addActionListener(e -> config.action.execute());

                JLabel descriptionLabel = new JLabel(config.descriptionText);

                gbc.gridx = 0;
                gbc.gridy = row;
                gbc.anchor = GridBagConstraints.WEST;
                frame.add(button, gbc);

                gbc.gridx = 1;
                gbc.gridy = row;
                gbc.anchor = GridBagConstraints.EAST;
                frame.add(descriptionLabel, gbc);

                row++;
            }

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}