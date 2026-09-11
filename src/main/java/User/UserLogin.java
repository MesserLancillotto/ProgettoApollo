package User;

import javax.swing.*;
import java.awt.*;

import Client.Client;

public class UserLogin {

    public static void foo(String username, char[] password) {


        JOptionPane.showMessageDialog(null, username + new String(password), "", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 1. Pannello interno (gestisce la disposizione verticale dei componenti)
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
            loginButton.addActionListener(e -> foo(userField.getText(), passField.getPassword()));

            boxPanel.add(userLabel);
            boxPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            boxPanel.add(userField);
            boxPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            boxPanel.add(passLabel);
            boxPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            boxPanel.add(passField);
            boxPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            boxPanel.add(loginButton);

            // 2. Pannello esterno con GridBagLayout (centra automaticamente il boxPanel al suo interno)
            JPanel outerPanel = new JPanel(new GridBagLayout());
            outerPanel.add(boxPanel);

            frame.add(outerPanel);

            // 3. Dimensioni della finestra (calcolate in base allo schermo)
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize((int)(screenSize.width * 0.25), (int)(screenSize.height * 0.30));
            frame.setMinimumSize(new Dimension(300, 250));
            
            frame.setLocationRelativeTo(null); // Centra la finestra sullo schermo
            frame.setVisible(true);
        });
    }
}