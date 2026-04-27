package User;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class FirstAccessView extends JFrame {
    private JTextField txtName;
    private JTextField txtSurname;
    private JTextField txtBirthYear;
    private JTextField txtCity;
    private JPasswordField pswdNewPassword;
    private JPasswordField pswdConfirmPassword;
    private JButton btnConfirm;
    private String typeOfView;

    public FirstAccessView(String type) {
        typeOfView = "Primo Accesso - "+type+" Profilo";
        initialize();
    }

    private void initialize() {
        this.setTitle(typeOfView);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 550); // Aumentata altezza per nuovo campo
        this.setMinimumSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);

        // Panel principale che centra tutto
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titolo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel titleLabel = new JLabel("Completa il tuo profilo", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, gbc);

        // Reset insets
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridwidth = 1;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Label occupa 30%
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7; // Campo occupa 70%
        gbc.anchor = GridBagConstraints.WEST;
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(200, 25)); // Dimensione fissa
        txtName.setMaximumSize(new Dimension(250, 25));   // Massima dimensione
        mainPanel.add(txtName, gbc);

        // Cognome
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Cognome:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        txtSurname = new JTextField();
        txtSurname.setPreferredSize(new Dimension(200, 25));
        txtSurname.setMaximumSize(new Dimension(250, 25));
        mainPanel.add(txtSurname, gbc);

        // Anno di Nascita
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Anno di nascita:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        txtBirthYear = new JTextField();
        txtBirthYear.setPreferredSize(new Dimension(100, 25)); // Più stretto per i numeri
        txtBirthYear.setMaximumSize(new Dimension(150, 25));
        mainPanel.add(txtBirthYear, gbc);

        // Città di Residenza
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Città di residenza:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        txtCity = new JTextField();
        txtCity.setPreferredSize(new Dimension(200, 25));
        txtCity.setMaximumSize(new Dimension(250, 25));
        mainPanel.add(txtCity, gbc);

        // Spaziatura prima dei campi password
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 0, 5, 0); // Spazio sopra
        mainPanel.add(Box.createVerticalStrut(10), gbc);

        // Reset per i campi password
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nuova Password
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Nuova Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        pswdNewPassword = new JPasswordField();
        pswdNewPassword.setPreferredSize(new Dimension(200, 25));
        pswdNewPassword.setMaximumSize(new Dimension(250, 25));
        mainPanel.add(pswdNewPassword, gbc);

        // Conferma Password
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Conferma Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        pswdConfirmPassword = new JPasswordField();
        pswdConfirmPassword.setPreferredSize(new Dimension(200, 25));
        pswdConfirmPassword.setMaximumSize(new Dimension(250, 25));
        mainPanel.add(pswdConfirmPassword, gbc);

        // Bottone Conferma
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0.5; // Spinge il bottone verso l'alto
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(25, 0, 0, 0);

        btnConfirm = new JButton("Conferma");
        btnConfirm.setPreferredSize(new Dimension(120, 35));
        mainPanel.add(btnConfirm, gbc);

        // Aggiungi il panel principale al frame
        this.add(mainPanel);
        this.setVisible(true);
    }

    // Getter methods
    public String getName() { return txtName.getText(); }
    public String getSurname() { return txtSurname.getText(); }
    public String getBirthYear() { return txtBirthYear.getText(); }
    public String getCity() { return txtCity.getText(); }
    public String getNewPassword() { return new String(pswdNewPassword.getPassword()); }
    public String getConfirmPassword() { return new String(pswdConfirmPassword.getPassword()); }

    public void addConfirmListener(ActionListener listener) {
        btnConfirm.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void clearAllFields()
    {

        txtName.setText("");
        txtSurname.setText("");
        txtBirthYear.setText("");
        txtCity.setText("");

        clearPasswordFields();
    }

    public void clearPasswordFields() {
        pswdNewPassword.setText("");
        pswdConfirmPassword.setText("");
    }
}