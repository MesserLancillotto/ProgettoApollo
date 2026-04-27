package User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLoginView extends JFrame
{
    JPanel panel;
    private JTextField txtUsername;
    private JPasswordField pswdField;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public UserLoginView()
    {
        initialize();
    }

    private void initialize()
    {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setMinimumSize(new Dimension(400, 300));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        JPanel pnlPrincipal = new JPanel(new BorderLayout());
        JPanel pnlCentral = new JPanel(new GridBagLayout());
        pnlCentral.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        lblUsername = new JLabel("Username");
        pnlCentral.add(lblUsername, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        txtUsername = new JTextField();
        pnlCentral.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        lblPassword = new JLabel("Password");
        pnlCentral.add(lblPassword, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        pswdField = new JPasswordField();
        pnlCentral.add(pswdField, gbc);

        // Pulsanti
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(15,10,5,10);
        JPanel pnlButtons = new JPanel(new FlowLayout());
        btnLogin = new JButton("Login");
        pnlButtons.add(btnLogin);
        btnRegister =new JButton("Crea nuovo account");
        pnlButtons.add(btnRegister);
        pnlCentral.add(pnlButtons, gbc);

        pnlPrincipal.add(pnlCentral, BorderLayout.CENTER);
        this.add(pnlPrincipal, BorderLayout.CENTER);
    }

    public String getUsername()
    {
        return txtUsername.getText();
    }

    public String getPassword()
    {
        return new String(pswdField.getPassword());
    }

    public void clearFields()
    {
        txtUsername.setText("");
        pswdField.setText("");
    }

    public void showMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }

    // Metodi per aggiungere listener (DELEGAZIONE)
    public void addLoginListener(ActionListener listener)
    {
        btnLogin.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener)
    {
        btnRegister.addActionListener(listener);
    }
}