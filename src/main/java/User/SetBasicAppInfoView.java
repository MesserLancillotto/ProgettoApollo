package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SetBasicAppInfoView extends JFrame {
    private JTextField txtOrganizationName;
    private JTextField txtMaxPeoplePerBooking;

    // Nuovi componenti per la gestione dinamica dei luoghi
    private JTextField txtNewLocation;
    private JButton btnAddLocation;
    private DefaultListModel<String> locationListModel;
    private JList<String> listLocations;
    private JButton btnRemoveLocation;

    private JButton btnConfirm;

    public SetBasicAppInfoView() {
        initialize();
        setupInternalListeners(); // Inizializza la logica dei bottoni Aggiungi/Rimuovi
    }

    private void initialize() {
        this.setTitle("Configurazione Applicazione");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(550, 480);
        this.setMinimumSize(new Dimension(500, 450));
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titolo
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        JLabel titleLabel = new JLabel("Configurazione Iniziale", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridwidth = 1;

        // Nome Organizzazione
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Nome organizzazione:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        txtOrganizationName = new JTextField();
        mainPanel.add(txtOrganizationName, gbc);

        // Numero massimo persone
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Max persone per prenotazione:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        txtMaxPeoplePerBooking = new JTextField();
        mainPanel.add(txtMaxPeoplePerBooking, gbc);

        // --- SEZIONE DINAMICA LUOGHI ---

        // 1. Campo di input e bottone Aggiungi
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Aggiungi luogo:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;

        // Sotto-pannello per unire TextField e Bottone "Aggiungi" sulla stessa riga
        JPanel addLocationPanel = new JPanel(new BorderLayout(5, 0));
        txtNewLocation = new JTextField();
        btnAddLocation = new JButton("Aggiungi");
        addLocationPanel.add(txtNewLocation, BorderLayout.CENTER);
        addLocationPanel.add(btnAddLocation, BorderLayout.EAST);
        mainPanel.add(addLocationPanel, gbc);

        // 2. Lista dei luoghi e bottone Rimuovi
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHEAST; // Allinea in alto
        mainPanel.add(new JLabel("Luoghi inseriti:"), gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH; // Espande la lista

        // Sotto-pannello per unire la Lista e il Bottone "Rimuovi"
        JPanel listPanel = new JPanel(new BorderLayout(5, 0));
        locationListModel = new DefaultListModel<>();
        listLocations = new JList<>(locationListModel);
        listLocations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollList = new JScrollPane(listLocations);
        scrollList.setPreferredSize(new Dimension(200, 100)); // Altezza fissa per la lista

        btnRemoveLocation = new JButton("Rimuovi");
        JPanel removeBtnContainer = new JPanel(new BorderLayout());
        removeBtnContainer.add(btnRemoveLocation, BorderLayout.NORTH); // Posiziona il bottone in alto a destra

        listPanel.add(scrollList, BorderLayout.CENTER);
        listPanel.add(removeBtnContainer, BorderLayout.EAST);
        mainPanel.add(listPanel, gbc);

        // --- FINE SEZIONE LUOGHI ---

        gbc.fill = GridBagConstraints.HORIZONTAL; // Reset

        // Spaziatura prima del bottone finale
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(Box.createVerticalStrut(15), gbc);

        // Bottone Conferma finale
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 0, 0);

        btnConfirm = new JButton("Conferma Configurazione");
        btnConfirm.setPreferredSize(new Dimension(200, 35));
        btnConfirm.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(btnConfirm, gbc);

        this.add(mainPanel);
        this.setVisible(true);
    }

    /**
     * Gestisce la logica interna dell'interfaccia per aggiungere e rimuovere
     * elementi dalla lista senza coinvolgere il controller principale.
     */
    private void setupInternalListeners() {
        // Logica per aggiungere un luogo
        btnAddLocation.addActionListener(e -> {
            String newLocation = txtNewLocation.getText().trim();
            if (!newLocation.isEmpty()) {

                if (!locationListModel.contains(newLocation)) {
                    locationListModel.addElement(newLocation);
                    txtNewLocation.setText(""); // Pulisce il campo dopo l'aggiunta
                    txtNewLocation.requestFocus(); // Rimette il cursore nel campo di testo
                } else {
                    showMessage("Questo luogo è già stato inserito.");
                }
            }
        });

        // Permette di aggiungere premendo "Invio" sulla tastiera
        txtNewLocation.addActionListener(e -> btnAddLocation.doClick());

        // Logica per rimuovere un luogo
        btnRemoveLocation.addActionListener(e -> {
            int selectedIndex = listLocations.getSelectedIndex();
            if (selectedIndex != -1) {
                locationListModel.remove(selectedIndex);
            } else {
                showMessage("Seleziona un luogo dalla lista per rimuoverlo.");
            }
        });
    }

    // --- Getter Methods ---

    public String getOrganizationName() {
        return txtOrganizationName.getText().trim();
    }

    public Integer getMaxPeoplePerBooking() {
        try {
            return Integer.parseInt(txtMaxPeoplePerBooking.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Restituisce gli elementi attuali nel modello della lista
    public List<String> getLocations() {
        List<String> locations = new ArrayList<>();
        for (int i = 0; i < locationListModel.getSize(); i++) {
            locations.add(locationListModel.getElementAt(i));
        }
        return locations;
    }

    // --- Altri metodi utili ---

    public void addConfirmListener(ActionListener listener) {
        btnConfirm.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void clearFields() {
        txtOrganizationName.setText("");
        txtMaxPeoplePerBooking.setText("");
        txtNewLocation.setText("");
        locationListModel.clear(); // Svuota la lista
    }
}