package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BeneficiaryView extends JFrame {

    private JPanel centerCardsPanel;
    private CardLayout cardLayout;

    private JPanel containerPrenotazione;
    private JPanel containerGestione;

    private JPanel selectedBookingPanel = null;
    private JPanel selectedManagePanel = null;

    // Struttura per memorizzare i dati completi dell'evento selezionato
    public static class EventSelectionData {
        public String name, desc, place;
        public Integer startDate, endDate;

        public EventSelectionData(String name, String desc, String place, Integer startDate, Integer endDate) {
            this.name = name; this.desc = desc; this.place = place;
            this.startDate = startDate; this.endDate = endDate;
        }
    }

    private EventSelectionData selectedBookingData = null;
    private EventSelectionData selectedManageData = null;

    private JButton btnEffettuaPrenotazione;
    private JButton btnGestisciPrenotazione;

    private JButton btnPrenota;
    private JButton btnDisdici;

    // Interfaccia di Callback per comunicare i risultati al controller
    public interface BookingConfirmListener {
        void onConfirm(String eventName, Integer eventStartDate, List<String> nominativi);
    }

    public BeneficiaryView() {
        initialize();
    }

    private void initialize() {
        this.setTitle("Pannello Beneficiario");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 650);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        JPanel leftMenuPanel = new JPanel();
        leftMenuPanel.setLayout(new GridLayout(6, 1, 0, 15));
        leftMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        leftMenuPanel.setBackground(new Color(45, 45, 48));

        btnEffettuaPrenotazione = createMenuButton("Effettua una prenotazione");
        btnGestisciPrenotazione = createMenuButton("Gestisci prenotazione");

        leftMenuPanel.add(btnEffettuaPrenotazione);
        leftMenuPanel.add(btnGestisciPrenotazione);

        this.add(leftMenuPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        centerCardsPanel = new JPanel(cardLayout);

        JPanel cardBenvenuto = createEmptyCard("Seleziona un'operazione dal menu");
        JPanel cardPrenotazione = createPrenotazioneCard();
        JPanel cardGestione = createGestioneCard();

        centerCardsPanel.add(cardBenvenuto, "BENVENUTO");
        centerCardsPanel.add(cardPrenotazione, "PRENOTAZIONE");
        centerCardsPanel.add(cardGestione, "GESTIONE");

        this.add(centerCardsPanel, BorderLayout.CENTER);

        btnEffettuaPrenotazione.addActionListener(e -> cardLayout.show(centerCardsPanel, "PRENOTAZIONE"));
        btnGestisciPrenotazione.addActionListener(e -> cardLayout.show(centerCardsPanel, "GESTIONE"));

        this.setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(220, 220, 220));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        return btn;
    }

    private JPanel createEmptyCard(String text) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.ITALIC, 16));
        label.setForeground(Color.GRAY);
        panel.add(label);
        return panel;
    }

    // --- PRENOTAZIONE ---

    private JPanel createPrenotazioneCard() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel lblTitle = new JLabel("Effettua una prenotazione");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        containerPrenotazione = new JPanel();
        containerPrenotazione.setLayout(new BoxLayout(containerPrenotazione, BoxLayout.Y_AXIS));
        containerPrenotazione.setBackground(new Color(240, 240, 240));

        JScrollPane scroll = new JScrollPane(containerPrenotazione);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scroll, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPrenota = new JButton("Prenota");
        btnPrenota.setPreferredSize(new Dimension(150, 40));
        southPanel.add(btnPrenota);

        mainPanel.add(southPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    public void clearPrenotazioniDisponibili() {
        containerPrenotazione.removeAll();
        selectedBookingPanel = null;
        selectedBookingData = null;
        containerPrenotazione.revalidate();
        containerPrenotazione.repaint();
    }

    public void addEventoPrenotabileRow(String eventName, String eventDescription, String eventRandezvous, Integer eventStartDate, Integer eventEndDate) {
        JPanel eventPanel = buildEventPanelBase(eventName, eventDescription, eventRandezvous, eventStartDate, eventEndDate);

        eventPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedBookingPanel != null) {
                    selectedBookingPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                            BorderFactory.createEmptyBorder(10, 15, 10, 15)));
                }
                selectedBookingPanel = eventPanel;
                selectedBookingData = new EventSelectionData(eventName, eventDescription, eventRandezvous, eventStartDate, eventEndDate);

                eventPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            }
        });

        containerPrenotazione.add(eventPanel);
        containerPrenotazione.add(Box.createRigidArea(new Dimension(0, 10)));
        containerPrenotazione.revalidate();
        containerPrenotazione.repaint();
    }

    // --- GESTIONE ---

    private JPanel createGestioneCard() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel lblTitle = new JLabel("Gestisci le tue prenotazioni");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        containerGestione = new JPanel();
        containerGestione.setLayout(new BoxLayout(containerGestione, BoxLayout.Y_AXIS));
        containerGestione.setBackground(new Color(240, 240, 240));

        JScrollPane scroll = new JScrollPane(containerGestione);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scroll, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnDisdici = new JButton("Disdici");
        btnDisdici.setPreferredSize(new Dimension(150, 40));
        southPanel.add(btnDisdici);

        mainPanel.add(southPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    public void clearPrenotazioniAttive() {
        containerGestione.removeAll();
        selectedManagePanel = null;
        selectedManageData = null;
        containerGestione.revalidate();
        containerGestione.repaint();
    }

    public void addEventoDaDisdireRow(String eventName, String eventDescription, String eventRandezvous, Integer eventStartDate, Integer eventEndDate) {
        JPanel eventPanel = buildEventPanelBase(eventName, eventDescription, eventRandezvous, eventStartDate, eventEndDate);

        eventPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedManagePanel != null) {
                    selectedManagePanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                            BorderFactory.createEmptyBorder(10, 15, 10, 15)));
                }
                selectedManagePanel = eventPanel;
                selectedManageData = new EventSelectionData(eventName, eventDescription, eventRandezvous, eventStartDate, eventEndDate);

                eventPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.RED, 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            }
        });

        containerGestione.add(eventPanel);
        containerGestione.add(Box.createRigidArea(new Dimension(0, 10)));
        containerGestione.revalidate();
        containerGestione.repaint();
    }

    // --- FINESTRE DI DIALOGO ---

    public void openBookingDialog(EventSelectionData data, int maxParticipants, BookingConfirmListener listener) {
        JDialog dialog = new JDialog(this, "Conferma Prenotazione", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(15, 15));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        infoPanel.add(new JLabel("<html><h2>" + data.name + "</h2></html>"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());
        String dataInizio = (data.startDate != null) ? formatter.format(Instant.ofEpochSecond(data.startDate)) : "N/D";

        infoPanel.add(new JLabel("<html><b>Data Inizio:</b> " + dataInizio + "</html>"));
        infoPanel.add(new JLabel("<html><b>Luogo:</b> " + data.place + "</html>"));
        dialog.add(infoPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectorPanel.add(new JLabel("Numero Partecipanti:"));

        Integer[] nums = new Integer[maxParticipants];
        for(int i=0; i<maxParticipants; i++) nums[i] = i+1;
        JComboBox<Integer> comboParticipants = new JComboBox<>(nums);
        selectorPanel.add(comboParticipants);
        centerPanel.add(selectorPanel, BorderLayout.NORTH);

        JPanel namesPanel = new JPanel();
        namesPanel.setLayout(new BoxLayout(namesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollNames = new JScrollPane(namesPanel);
        scrollNames.setBorder(BorderFactory.createTitledBorder("Nominativi Partecipanti"));
        centerPanel.add(scrollNames, BorderLayout.CENTER);
        dialog.add(centerPanel, BorderLayout.CENTER);

        List<JTextField> textFields = new ArrayList<>();

        Runnable updateFields = () -> {
            namesPanel.removeAll();
            textFields.clear();
            int count = (Integer) comboParticipants.getSelectedItem();
            for (int i = 1; i <= count; i++) {
                JPanel row = new JPanel(new BorderLayout(10, 0));
                row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                row.add(new JLabel("Partecipante " + i + ":"), BorderLayout.WEST);
                JTextField tf = new JTextField();
                textFields.add(tf);
                row.add(tf, BorderLayout.CENTER);
                namesPanel.add(row);
            }
            namesPanel.revalidate();
            namesPanel.repaint();
        };

        updateFields.run();
        comboParticipants.addActionListener(e -> updateFields.run());

        JPanel bottomPanel = new JPanel();
        JButton btnConferma = new JButton("Conferma");
        btnConferma.setPreferredSize(new Dimension(200, 40));
        btnConferma.setBackground(new Color(144, 238, 144));

        btnConferma.addActionListener(e -> {
            List<String> nominativi = new ArrayList<>();
            for (JTextField tf : textFields) {
                String nome = tf.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Errore: Inserisci tutti i nominativi dei partecipanti!", "Campi vuoti", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                nominativi.add(nome);
            }
            listener.onConfirm(data.name, data.startDate, nominativi);
            dialog.dispose();
        });

        bottomPanel.add(btnConferma);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // Finestra di dialogo per la disdetta
    public void openCancelConfirmDialog(EventSelectionData data, Runnable onConfirm) {
        int response = JOptionPane.showConfirmDialog(this,
                "Sei sicuro di voler disdire la prenotazione per l'evento:\n" + data.name + "?",
                "Conferma Disdetta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            onConfirm.run();
        }
    }

    // --- UTILITY ---

    private JPanel buildEventPanelBase(String eventName, String eventDescription, String eventRandezvous, Integer eventStartDate, Integer eventEndDate) {
        JPanel eventPanel = new JPanel(new GridLayout(0, 1, 5, 2));
        eventPanel.setBackground(Color.WHITE);
        eventPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        eventPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());
        String dataInizio = (eventStartDate != null) ? formatter.format(Instant.ofEpochSecond(eventStartDate)) : "N/D";
        String dataFine = (eventEndDate != null) ? formatter.format(Instant.ofEpochSecond(eventEndDate)) : "N/D";

        eventPanel.add(new JLabel("<html><b>Evento:</b> " + eventName + "</html>"));
        eventPanel.add(new JLabel("<html><b>Descrizione:</b> " + eventDescription + "</html>"));
        eventPanel.add(new JLabel("<html><b>Luogo:</b> " + eventRandezvous + "</html>"));
        eventPanel.add(new JLabel("<html><b>Data:</b> " + dataInizio + " - " + dataFine + "</html>"));

        return eventPanel;
    }

    public EventSelectionData getSelectedBookingData() { return selectedBookingData; }
    public EventSelectionData getSelectedManageData() { return selectedManageData; }

    public void addEffettuaPrenotazioneListener (ActionListener listener) { btnEffettuaPrenotazione.addActionListener(listener); }
    public void addGestisciPrenotazioneListener (ActionListener listener) { btnGestisciPrenotazione.addActionListener(listener); }
    public void addPrenotaActionListener(ActionListener al) { btnPrenota.addActionListener(al); }
    public void addDisdiciActionListener(ActionListener al) { btnDisdici.addActionListener(al); }
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
}