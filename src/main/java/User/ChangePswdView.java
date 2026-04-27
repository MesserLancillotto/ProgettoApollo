package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChangePswdView extends JFrame {

    // Componenti della finestra
    private JPasswordField txtNuovaPassword;
    private JPasswordField txtConfermaPassword;
    private JButton btnConfirm;
    private JButton btnCancel;

    public ChangePswdView() {
        // Impostazioni della finestra
        setTitle("Cambia Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        setVisible(true);

        // 1. Etichetta superiore con i requisiti (Zona NORTH)
        String infoText = "<html><div style='text-align: center;'>"
                + "Inserire la nuova password, deve contenere una lettera maiuscola,<br>"
                + "una minuscola, un numero e deve essere lunga almeno 8 caratteri."
                + "</div></html>";
        JLabel lblInfo = new JLabel(infoText, SwingConstants.CENTER);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        add(lblInfo, BorderLayout.NORTH);

        // 2. Pannello centrale per campi di input (Zona CENTER)
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        txtNuovaPassword = new JPasswordField(15);
        txtConfermaPassword = new JPasswordField(15);

        centerPanel.add(new JLabel("Nuova password:"));
        centerPanel.add(txtNuovaPassword);
        centerPanel.add(new JLabel("Conferma password:"));
        centerPanel.add(txtConfermaPassword);

        add(centerPanel, BorderLayout.CENTER);

        // 3. Pannello inferiore per i bottoni (Zona SOUTH)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        btnConfirm = new JButton("Conferma");
        btnCancel = new JButton("Annulla");

        bottomPanel.add(btnConfirm);
        bottomPanel.add(btnCancel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Finalizzazione
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Svuota entrambi i campi password.
     */
    public void clearAllFields() {
        txtNuovaPassword.setText("");
        txtConfermaPassword.setText("");
    }

    public void addConfirmListener(ActionListener actionListener)
    {
        btnConfirm.addActionListener(actionListener);
    }
    public void addCancelListener(ActionListener actionListener)
    {
        btnCancel.addActionListener(actionListener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Getter per l'interazione con il controller
    public String getTxtNuovaPassword() { return new String (txtNuovaPassword.getPassword()); }
    public String getTxtConfermaPassword() { return new String (txtConfermaPassword.getPassword()); }
    public JButton getBtnConfirm() { return btnConfirm; }
    public JButton getBtnCancel() { return btnCancel; }

}