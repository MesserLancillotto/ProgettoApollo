package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import java.util.function.Consumer;

public class ConfiguratorView extends JFrame {

    // Pannello centrale
    private JPanel centerCardsPanel;
    private CardLayout cardLayout;

    // Bottoni Impostazioni
    private JButton btnCambiaMax;
    private JTextField txtNewMaxSub;
    private JButton btnDatePrecluse;

    // Bottoni Luoghi (Nuova struttura)
    private JButton btnGestisciLuoghiAzione;
    private JButton btnAggiungiLuogo;

    // Bottoni Azioni Volontari
    private JButton btnChiudiRaccolta;
    private JButton btnOpenVoluntaryDisponibility;
    private JButton btnAggiungiTipoVisitaVolontario;
    private JButton btnConferma;
    private JButton btnGestisciVolontariAzione;

    // Variabili per la finestra Gestione Volontari Dinamica
    private JFrame frameGestioneVolontari;
    private JPanel containerVolontari;
    private JPanel selectedVoluntaryPanel = null;
    private String selectedVoluntaryID = null;

    // Variabili per la finestra Aggiungi Tipo Visita a Volontario
    private JFrame frameAddVisitTypeVoluntary;
    private JPanel containerAddVisitTypeVolontari;
    private JPanel selectedAddVisitVoluntaryPanel = null;
    private String selectedAddVisitVoluntaryID = null;

    // Variabili per la finestra Gestione Luoghi Dinamica
    private JFrame frameGestioneLuoghi;
    private JPanel containerLuoghi;
    private JPanel selectedLuogoPanel = null;
    private String selectedLuogoKey = null; // Salva la chiave "città|indirizzo"

    // Struttura dati per aggregare i luoghi ed evitare righe duplicate
    private class PlaceRowData {
        String city;
        String address;
        List<String> visitTypes = new ArrayList<>();
        JPanel panel;
        JLabel lblTypes;
    }
    private Map<String, PlaceRowData> placesMap = new HashMap<>();

    // Dati e Listener per il Controller
    private List<Long> datePrecluseUnix;
    private ActionListener chiudiRaccoltaListener;
    private ActionListener apriRaccoltaListener;
    private ActionListener confirmCloseListener;
    private JFrame frameChangeMax;

    public ConfiguratorView() {
        initialize();
    }

    private void initialize() {
        this.setTitle("Menù Configuratore");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // --- 1. CREAZIONE MENU LATERALE (SINISTRA) ---
        JPanel leftMenuPanel = new JPanel();
        leftMenuPanel.setLayout(new GridLayout(5, 1, 0, 15));
        leftMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        leftMenuPanel.setBackground(new Color(60, 63, 65));

        JButton btnGestisciVolontari = createMenuButton("Gestisci volontari");
        JButton btnGestisciLuoghi = createMenuButton("Gestisci luoghi");
        JButton btnImpostazioni = createMenuButton("Impostazioni visite");
        JButton btnMostraVisite = createMenuButton("Mostra visite");

        leftMenuPanel.add(btnGestisciVolontari);
        leftMenuPanel.add(btnGestisciLuoghi);
        leftMenuPanel.add(btnImpostazioni);
        leftMenuPanel.add(new JLabel()); // Spazio vuoto
        leftMenuPanel.add(btnMostraVisite);

        this.add(leftMenuPanel, BorderLayout.WEST);

        // --- 2. CREAZIONE AREA CENTRALE DINAMICA (CARDLAYOUT) ---
        cardLayout = new CardLayout();
        centerCardsPanel = new JPanel(cardLayout);
        centerCardsPanel.setBackground(Color.WHITE);

        JPanel cardVuota = createEmptyCard();
        JPanel cardVolontari = createVolontariCard();
        JPanel cardLuoghi = createLuoghiCard();
        JPanel cardImpostazioni = createImpostazioniCard();

        centerCardsPanel.add(cardVuota, "VUOTA");
        centerCardsPanel.add(cardVolontari, "VOLONTARI");
        centerCardsPanel.add(cardLuoghi, "LUOGHI");
        centerCardsPanel.add(cardImpostazioni, "IMPOSTAZIONI");

        this.add(centerCardsPanel, BorderLayout.CENTER);

        // --- 3. AZIONI DEI BOTTONI DEL MENU ---
        btnGestisciVolontari.addActionListener(e -> cardLayout.show(centerCardsPanel, "VOLONTARI"));
        btnGestisciLuoghi.addActionListener(e -> cardLayout.show(centerCardsPanel, "LUOGHI"));
        btnImpostazioni.addActionListener(e -> cardLayout.show(centerCardsPanel, "IMPOSTAZIONI"));

        btnMostraVisite.addActionListener(e -> open_visit_view());

        this.setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(85, 88, 90));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }

    private JPanel createEmptyCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JLabel lblWelcome = new JLabel("Seleziona un'opzione dal menu a sinistra");
        lblWelcome.setFont(new Font("Arial", Font.ITALIC, 16));
        lblWelcome.setForeground(Color.GRAY);
        panel.add(lblWelcome);
        return panel;
    }

    // Schermata 1: Gestisci Volontari
    private JPanel createVolontariCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        btnGestisciVolontariAzione = new JButton("Gestioni volontari");
        btnGestisciVolontariAzione.setPreferredSize(new Dimension(300, 40));
        panel.add(btnGestisciVolontariAzione, gbc);

        gbc.gridy = 1;
        btnChiudiRaccolta = new JButton("Chiudi raccolta disponibilità volontari");
        btnChiudiRaccolta.setPreferredSize(new Dimension(300, 40));
        btnChiudiRaccolta.addActionListener(e -> gestisciChiudiRaccolta());
        panel.add(btnChiudiRaccolta, gbc);

        gbc.gridy = 2;
        btnOpenVoluntaryDisponibility = new JButton("Apri raccolta disponibilità volontari");
        btnOpenVoluntaryDisponibility.setPreferredSize(new Dimension(300, 40));
        panel.add(btnOpenVoluntaryDisponibility, gbc);

        gbc.gridy = 3;
        btnAggiungiTipoVisitaVolontario = new JButton("Aggiungi tipo di visita a volontario");
        btnAggiungiTipoVisitaVolontario.setPreferredSize(new Dimension(300, 40));
        panel.add(btnAggiungiTipoVisitaVolontario, gbc);

        return panel;
    }

    // Schermata 2: Gestisci Luoghi (Modificata come richiesto)
    private JPanel createLuoghiCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        btnGestisciLuoghiAzione = new JButton("Gestisci luoghi");
        btnGestisciLuoghiAzione.setPreferredSize(new Dimension(350, 45));
        btnGestisciLuoghiAzione.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(btnGestisciLuoghiAzione, gbc);

        gbc.gridy = 1;
        btnAggiungiLuogo = new JButton("Aggiungi nuovo luogo");
        btnAggiungiLuogo.setPreferredSize(new Dimension(350, 45));
        btnAggiungiLuogo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(btnAggiungiLuogo, gbc);

        return panel;
    }

    // Schermata 3: Impostazioni
    private JPanel createImpostazioniCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;

        btnCambiaMax = new JButton("Cambia max persone per iscrizione");
        btnCambiaMax.setPreferredSize(new Dimension(250, 40));
        panel.add(btnCambiaMax, gbc);

        gbc.gridy = 1;
        btnDatePrecluse = new JButton("Segna date precluse alle visite");
        btnDatePrecluse.setPreferredSize(new Dimension(250, 40));
        panel.add(btnDatePrecluse, gbc);

        return panel;
    }

    // --- LOGICA: FINESTRA "GESTISCI LUOGHI" ---

    public void addGestisciLuoghiAzioneListener(ActionListener listener) {
        btnGestisciLuoghiAzione.addActionListener(listener);
    }

    public void open_gestione_luoghi_view(Runnable onAddType, Runnable onRemoveType, Runnable onRemovePlace) {
        if (frameGestioneLuoghi != null && frameGestioneLuoghi.isVisible()) {
            frameGestioneLuoghi.toFront();
            return;
        }

        frameGestioneLuoghi = new JFrame("Gestione Luoghi e Tipi di Visita");
        frameGestioneLuoghi.setSize(850, 500); // Più larga per ospitare i pulsanti a destra
        frameGestioneLuoghi.setLocationRelativeTo(this);
        frameGestioneLuoghi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameGestioneLuoghi.setLayout(new BorderLayout(10, 10));

        // Pannello principale con bordi
        JPanel mainPanel = new JPanel(new BorderLayout(15, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Elenco Luoghi Registrati");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Centro: Lista dei luoghi
        containerLuoghi = new JPanel();
        containerLuoghi.setLayout(new BoxLayout(containerLuoghi, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(containerLuoghi);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scroll, BorderLayout.CENTER);

        // Destra: Pannello con i 3 pulsanti
        JPanel rightButtonsPanel = new JPanel();
        rightButtonsPanel.setLayout(new BoxLayout(rightButtonsPanel, BoxLayout.Y_AXIS));
        rightButtonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JButton btnAddType = new JButton("Aggiungi tipo di visita a luogo");
        JButton btnRemType = new JButton("Rimuovi tipo di visita da luogo");
        JButton btnRemPlace = new JButton("Rimuovi luogo");

        Dimension btnSize = new Dimension(220, 45);
        Component[] buttons = {btnAddType, btnRemType, btnRemPlace};
        for (Component c : buttons) {
            JButton b = (JButton) c;
            b.setMaximumSize(btnSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            rightButtonsPanel.add(b);
            rightButtonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        btnRemPlace.setBackground(new Color(255, 100, 100)); // Evidenzia distruzione
        btnRemPlace.setForeground(Color.BLACK);

        // Listeners con controlli di selezione
        btnAddType.addActionListener(e -> {
            if (selectedLuogoKey == null) showMessage("Seleziona prima un luogo dalla lista!");
            else onAddType.run();
        });

        btnRemType.addActionListener(e -> {
            if (selectedLuogoKey == null) showMessage("Seleziona prima un luogo dalla lista!");
            else onRemoveType.run();
        });

        btnRemPlace.addActionListener(e -> {
            if (selectedLuogoKey == null) showMessage("Seleziona prima un luogo dalla lista!");
            else onRemovePlace.run();
        });

        mainPanel.add(rightButtonsPanel, BorderLayout.EAST);
        frameGestioneLuoghi.add(mainPanel);
        frameGestioneLuoghi.setVisible(true);
    }

    public void clearLuoghiList() {
        if (containerLuoghi != null) {
            containerLuoghi.removeAll();
            placesMap.clear();
            selectedLuogoPanel = null;
            selectedLuogoKey = null;
            containerLuoghi.revalidate();
            containerLuoghi.repaint();
        }
    }

    public void addLuogoRow(String city, String address, String visitType) {
        String key = city + "|" + address;

        // Logica di aggregazione: se esiste già, aggiungi solo il tipo di visita (se non duplicato)
        if (placesMap.containsKey(key)) {
            PlaceRowData data = placesMap.get(key);
            if (visitType != null && !visitType.isEmpty() && !data.visitTypes.contains(visitType)) {
                data.visitTypes.add(visitType);
                // Aggiorna l'etichetta grafica
                data.lblTypes.setText("<html><b>Tipi di Visita:</b> " + String.join(", ", data.visitTypes) + "</html>");
            }
            return;
        }

        // Se non esiste, crea la nuova riga
        PlaceRowData data = new PlaceRowData();
        data.city = city;
        data.address = address;
        if (visitType != null && !visitType.isEmpty()) {
            data.visitTypes.add(visitType);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 2));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel lblName = new JLabel("<html><b>Città:</b> " + city + " | <b>Indirizzo:</b> " + address + "</html>");
        lblName.setFont(new Font("Arial", Font.PLAIN, 15));

        String typesStr = data.visitTypes.isEmpty() ? "Nessun tipo assegnato" : String.join(", ", data.visitTypes);
        data.lblTypes = new JLabel("<html><b>Tipi di Visita:</b> " + typesStr + "</html>");
        data.lblTypes.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(lblName);
        panel.add(data.lblTypes);

        // Selezione riga
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedLuogoPanel != null) {
                    selectedLuogoPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                            BorderFactory.createEmptyBorder(10, 15, 10, 15)));
                }
                selectedLuogoPanel = panel;
                selectedLuogoKey = key;
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            }
        });

        data.panel = panel;
        placesMap.put(key, data);

        containerLuoghi.add(panel);
        containerLuoghi.add(Box.createRigidArea(new Dimension(0, 10)));
        containerLuoghi.revalidate();
        containerLuoghi.repaint();
    }

    public String getSelectedLuogoKey() {
        return selectedLuogoKey; // Ritorna "Città|Indirizzo"
    }

    public void addAggiungiLuogoListener(ActionListener listener) {
        btnAggiungiLuogo.addActionListener(listener);
    }

    // --- LOGICA VOLONTARI ---

    public void addGestisciVolontariAzioneListener(ActionListener listener) {
        btnGestisciVolontariAzione.addActionListener(listener);
    }

    public void open_gestione_volontari_view(Runnable onRemoveConfirm) {
        if (frameGestioneVolontari != null && frameGestioneVolontari.isVisible()) {
            frameGestioneVolontari.toFront();
            return;
        }

        frameGestioneVolontari = new JFrame("Gestione Volontari");
        frameGestioneVolontari.setSize(600, 500);
        frameGestioneVolontari.setLocationRelativeTo(this);
        frameGestioneVolontari.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameGestioneVolontari.setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Elenco Volontari Registrati");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        containerVolontari = new JPanel();
        containerVolontari.setLayout(new BoxLayout(containerVolontari, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(containerVolontari);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRimuovi = new JButton("Rimuovi");
        btnRimuovi.setPreferredSize(new Dimension(150, 40));
        btnRimuovi.setBackground(new Color(255, 100, 100));
        btnRimuovi.setForeground(Color.BLACK);

        btnRimuovi.addActionListener(e -> {
            if (selectedVoluntaryID == null) {
                showMessage("Seleziona prima un utente dalla lista per poterlo rimuovere!");
            } else {
                int response = JOptionPane.showConfirmDialog(frameGestioneVolontari,
                        "Sei sicuro di voler rimuovere il volontario:\n" + selectedVoluntaryID + "?",
                        "Conferma Rimozione",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    onRemoveConfirm.run();
                }
            }
        });

        bottomPanel.add(btnRimuovi);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frameGestioneVolontari.add(mainPanel);
        frameGestioneVolontari.setVisible(true);
    }

    public void clearVolontariList() {
        if (containerVolontari != null) {
            containerVolontari.removeAll();
            selectedVoluntaryPanel = null;
            selectedVoluntaryID = null;
            containerVolontari.revalidate();
            containerVolontari.repaint();
        }
    }

    public void addVolontarioRow(String voluntaryID, List<String> visitTypes) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 2));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel lblName = new JLabel("<html><b>ID Volontario:</b> " + voluntaryID + "</html>");
        lblName.setFont(new Font("Arial", Font.PLAIN, 15));

        String typesStr = (visitTypes != null && !visitTypes.isEmpty()) ? String.join(", ", visitTypes) : "Nessun tipo assegnato";
        JLabel lblTypes = new JLabel("<html><b>Tipi di Visita:</b> " + typesStr + "</html>");
        lblTypes.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(lblName);
        panel.add(lblTypes);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedVoluntaryPanel != null) {
                    selectedVoluntaryPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                            BorderFactory.createEmptyBorder(10, 15, 10, 15)));
                }
                selectedVoluntaryPanel = panel;
                selectedVoluntaryID = voluntaryID;
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            }
        });

        containerVolontari.add(panel);
        containerVolontari.add(Box.createRigidArea(new Dimension(0, 10)));
        containerVolontari.revalidate();
        containerVolontari.repaint();
    }

    public String getSelectedVoluntaryID() {
        return selectedVoluntaryID;
    }

    public void addAggiungiTipoVisitaVolontarioListener(ActionListener listener) {
        btnAggiungiTipoVisitaVolontario.addActionListener(listener);
    }

    public void open_add_visit_type_voluntary_view(Runnable onAddClick) {
        if (frameAddVisitTypeVoluntary != null && frameAddVisitTypeVoluntary.isVisible()) {
            frameAddVisitTypeVoluntary.toFront();
            return;
        }

        frameAddVisitTypeVoluntary = new JFrame("Aggiungi tipo di visita a volontario");
        frameAddVisitTypeVoluntary.setSize(600, 500);
        frameAddVisitTypeVoluntary.setLocationRelativeTo(this);
        frameAddVisitTypeVoluntary.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAddVisitTypeVoluntary.setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Elenco Volontari Registrati");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        containerAddVisitTypeVolontari = new JPanel();
        containerAddVisitTypeVolontari.setLayout(new BoxLayout(containerAddVisitTypeVolontari, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(containerAddVisitTypeVolontari);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAggiungi = new JButton("Aggiungi tipo di visita");
        btnAggiungi.setPreferredSize(new Dimension(200, 40));
        btnAggiungi.setBackground(new Color(144, 238, 144));
        btnAggiungi.setForeground(Color.BLACK);

        btnAggiungi.addActionListener(e -> {
            if (selectedAddVisitVoluntaryID == null) {
                showMessage("Seleziona prima un utente dalla lista per poter aggiungere un tipo di visita!");
            } else {
                onAddClick.run();
            }
        });

        bottomPanel.add(btnAggiungi);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frameAddVisitTypeVoluntary.add(mainPanel);
        frameAddVisitTypeVoluntary.setVisible(true);
    }

    public void clearAddVisitVolontariList() {
        if (containerAddVisitTypeVolontari != null) {
            containerAddVisitTypeVolontari.removeAll();
            selectedAddVisitVoluntaryPanel = null;
            selectedAddVisitVoluntaryID = null;
            containerAddVisitTypeVolontari.revalidate();
            containerAddVisitTypeVolontari.repaint();
        }
    }

    public void addAddVisitVolontarioRow(String voluntaryID, List<String> visitTypes) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 2));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel lblName = new JLabel("<html><b>ID Volontario:</b> " + voluntaryID + "</html>");
        lblName.setFont(new Font("Arial", Font.PLAIN, 15));

        String typesStr = (visitTypes != null && !visitTypes.isEmpty()) ? String.join(", ", visitTypes) : "Nessun tipo assegnato";
        JLabel lblTypes = new JLabel("<html><b>Tipi di Visita attuali:</b> " + typesStr + "</html>");
        lblTypes.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(lblName);
        panel.add(lblTypes);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedAddVisitVoluntaryPanel != null) {
                    selectedAddVisitVoluntaryPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                            BorderFactory.createEmptyBorder(10, 15, 10, 15)));
                }
                selectedAddVisitVoluntaryPanel = panel;
                selectedAddVisitVoluntaryID = voluntaryID;
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            }
        });

        containerAddVisitTypeVolontari.add(panel);
        containerAddVisitTypeVolontari.add(Box.createRigidArea(new Dimension(0, 10)));
        containerAddVisitTypeVolontari.revalidate();
        containerAddVisitTypeVolontari.repaint();
    }

    public String getSelectedAddVisitVoluntaryID() {
        return selectedAddVisitVoluntaryID;
    }

    public void open_select_new_visit_type_dialog(String voluntaryID, List<String> newVisitTypes, Consumer<String> onConfirm) {
        JDialog dialog = new JDialog(this, "Seleziona nuovo tipo di visita", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(15, 15));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        infoPanel.add(new JLabel("<html><b>Utente Selezionato:</b> " + voluntaryID + "</html>"));
        infoPanel.add(new JLabel("Seleziona il tipo di visita da aggiungere:"));
        dialog.add(infoPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JComboBox<String> comboTypes = new JComboBox<>(newVisitTypes.toArray(new String[0]));
        centerPanel.add(comboTypes);
        dialog.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnConferma = new JButton("Conferma");
        btnConferma.setPreferredSize(new Dimension(150, 40));
        btnConferma.setBackground(new Color(144, 238, 144));
        btnConferma.addActionListener(e -> {
            String selected = (String) comboTypes.getSelectedItem();
            if (selected != null) {
                onConfirm.accept(selected);
                dialog.dispose();
            }
        });

        bottomPanel.add(btnConferma);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    public void addOpenVoluntaryDisponibility (ActionListener listener) {
        btnOpenVoluntaryDisponibility.addActionListener(listener);
    }

    private void gestisciChiudiRaccolta() {
        JFrame frameConferma = new JFrame("Conferma Chiusura");
        frameConferma.setSize(450, 150);
        frameConferma.setLocationRelativeTo(this);
        frameConferma.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameConferma.setLayout(new BorderLayout(10, 10));

        JLabel lblMessaggio = new JLabel("Vuoi davvero chiudere la raccolta delle disponibilità?", SwingConstants.CENTER);
        lblMessaggio.setFont(new Font("Arial", Font.PLAIN, 15));
        frameConferma.add(lblMessaggio, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        btnConferma = new JButton("Conferma Chiusura");
        btnConferma.setPreferredSize(new Dimension(200, 35));

        if (confirmCloseListener != null) {
            btnConferma.addActionListener(confirmCloseListener);
        }

        bottomPanel.add(btnConferma);
        frameConferma.add(bottomPanel, BorderLayout.SOUTH);
        frameConferma.setVisible(true);
    }

    public void addConfirmCloseVoluntariesDisponibilties(ActionListener listener) {
        this.confirmCloseListener = listener;
    }

    // --- LOGICA IMPOSTAZIONI ---

    public void addClosedDaysListener(ActionListener listener) {
        btnDatePrecluse.addActionListener(listener);
    }

    public void open_closed_days_view(ActionListener listener) {
        LocalDate oggi = LocalDate.now();
        int mesiDaAggiungere = oggi.getDayOfMonth() >= 16 ? 3 : 2;
        YearMonth meseTarget = YearMonth.from(oggi.plusMonths(mesiDaAggiungere));

        DateTimeFormatter formatterOggi = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ITALIAN);
        String stringaOggi = oggi.format(formatterOggi);
        String nomeMeseTarget = meseTarget.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
        nomeMeseTarget = nomeMeseTarget.substring(0, 1).toUpperCase() + nomeMeseTarget.substring(1);

        JFrame frameDate = new JFrame("Segna le date precluse alle visite");
        frameDate.setSize(500, 400);
        frameDate.setLocationRelativeTo(this);
        frameDate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDate.setLayout(new BorderLayout(10, 10));

        String testoIntestazione = String.format("Oggi è il %s, segna le disponibilità del mese di %s %d",
                stringaOggi, nomeMeseTarget, meseTarget.getYear());
        JLabel lblIntestazione = new JLabel(testoIntestazione, SwingConstants.CENTER);
        lblIntestazione.setFont(new Font("Arial", Font.BOLD, 14));
        lblIntestazione.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        frameDate.add(lblIntestazione, BorderLayout.NORTH);

        JPanel panelGiorni = new JPanel(new GridLayout(0, 7, 5, 5));
        panelGiorni.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        int giorniNelMese = meseTarget.lengthOfMonth();
        List<JToggleButton> listaBottoniGiorni = new ArrayList<>();

        for (int i = 1; i <= giorniNelMese; i++) {
            JToggleButton btnGiorno = new JToggleButton(String.valueOf(i));
            btnGiorno.setFont(new Font("Arial", Font.PLAIN, 14));
            btnGiorno.addChangeListener(e -> {
                if (btnGiorno.isSelected()) {
                    btnGiorno.setBackground(new Color(255, 100, 100));
                    btnGiorno.setOpaque(true);
                } else {
                    btnGiorno.setBackground(null);
                }
            });
            listaBottoniGiorni.add(btnGiorno);
            panelGiorni.add(btnGiorno);
        }

        frameDate.add(new JScrollPane(panelGiorni), BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        JButton btnSalva = new JButton("Salva Date Precluse");
        btnSalva.setPreferredSize(new Dimension(200, 40));

        btnSalva.addActionListener(e -> {
            datePrecluseUnix = new ArrayList<>();
            for (JToggleButton btn : listaBottoniGiorni) {
                if (btn.isSelected()) {
                    int giorno = Integer.parseInt(btn.getText());
                    LocalDate dataSelezionata = meseTarget.atDay(giorno);
                    long unixTimestamp = dataSelezionata.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
                    datePrecluseUnix.add(unixTimestamp);
                }
            }
            JOptionPane.showMessageDialog(frameDate, "Date salvate con successo");
            if (listener != null) {
                listener.actionPerformed(e);
            }
            frameDate.dispose();
        });

        panelBottom.add(btnSalva);
        frameDate.add(panelBottom, BorderLayout.SOUTH);
        frameDate.setVisible(true);
    }

    public List<Long> getDatePrecluse() {
        return datePrecluseUnix;
    }

    public void addChangeMaxPeopleListener(ActionListener listener) {
        btnCambiaMax.addActionListener(listener);
    }

    public void setChangeMaxVisibility (Boolean value) {
        frameChangeMax.setVisible (value);
    }

    public void open_change_max_number_subrsctipion_view(int currentMaxValue, ActionListener saveListener) {
        frameChangeMax = new JFrame("Cambia numero massimo di persone per prenotazione");
        frameChangeMax.setSize(500, 250);
        frameChangeMax.setLocationRelativeTo(this);
        frameChangeMax.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameChangeMax.setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String testoIniziale = "Attualmente si possono iscrivere al massimo " + currentMaxValue + " persone per ogni prenotazione";
        JLabel lblStatus = new JLabel(testoIniziale, SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 13));
        mainPanel.add(lblStatus, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblNuovoValore = new JLabel("Nuovo valore:");
        lblNuovoValore.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(lblNuovoValore, gbc);

        txtNewMaxSub = new JTextField();
        txtNewMaxSub.setPreferredSize(new Dimension(200, 28));
        gbc.gridx = 1; gbc.gridy = 0;
        centerPanel.add(txtNewMaxSub, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnAggiorna = new JButton("Aggiorna Valore");
        btnAggiorna.setPreferredSize(new Dimension(150, 35));
        bottomPanel.add(btnAggiorna);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        btnAggiorna.addActionListener((ActionEvent e) -> {
            String inputText = txtNewMaxSub.getText().trim();
            try {
                int newValue = Integer.parseInt(inputText);
                if (newValue > 0) {
                    lblStatus.setText("Attualmente si possono iscrivere al massimo " + newValue + " persone per ogni prenotazione");

                    if(saveListener != null) {
                        saveListener.actionPerformed(e);
                    }
                    txtNewMaxSub.setText("");
                    JOptionPane.showMessageDialog(frameChangeMax, "Valore aggiornato con successo a " + newValue, "Successo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frameChangeMax, "Il valore deve essere maggiore di zero.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameChangeMax, "Per favore, inserisci un numero intero valido.", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        frameChangeMax.add(mainPanel);
        frameChangeMax.setVisible(true);
    }

    public String getNewMaxSub() {
        return txtNewMaxSub.getText();
    }

    public void open_aggiungi_luogo_view(Object data) {
        JFrame frame = new JFrame("Aggiungi nuovo luogo");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(this);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(new Color(240, 240, 240));
        JLabel lbl = new JLabel("Schermata di aggiunta in costruzione...");
        lbl.setFont(new Font("Arial", Font.ITALIC, 16));
        pnl.add(lbl);

        frame.add(pnl);
        frame.setVisible(true);
    }

    private void open_visit_view() {
        JFrame mostraVisiteFrame = new JFrame("Elenco Visite Programmate");
        mostraVisiteFrame.setSize(600, 400);
        mostraVisiteFrame.setLocationRelativeTo(this);
        mostraVisiteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colonne = {"Nome Visita", "Luogo", "Data", "Stato"};
        Object[][] dati = {
                {"Visita Guidata Colosseo", "Roma", "15/05/2026", "Attiva"},
                {"Tour Musei Vaticani", "Vaticano", "20/05/2026", "Completa"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(dati, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabellaVisite = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabellaVisite);

        mostraVisiteFrame.add(scrollPane, BorderLayout.CENTER);
        mostraVisiteFrame.setVisible(true);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}