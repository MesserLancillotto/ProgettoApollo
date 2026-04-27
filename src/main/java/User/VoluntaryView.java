package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class VoluntaryView extends JFrame {

    private JPanel centerCardsPanel;
    private CardLayout cardLayout;

    // Componenti per i dati dinamici
    private DefaultListModel<String> listModelTipiVisita;
    private JPanel containerVisiteConfermate; // Contenitore per le righe degli eventi

    // Callback per passare i dati al Controller in modo pulito
    private Consumer<List<Long>> onDisponibilitaSaved;

    // Bottoni Menù Laterale
    private JButton btnVisualizeTypes;
    private JButton btnDaiDisponibilita;
    private JButton btnShowConfirmedVisits;

    private JPanel cardTipiVisita;

    public VoluntaryView() {
        initialize();
    }

    private void initialize() {
        this.setTitle("Pannello Volontario");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(850, 600); // Leggermente allargata per far spazio ai dati degli eventi
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // --- 1. MENU LATERALE (SINISTRA) ---
        JPanel leftMenuPanel = new JPanel();
        leftMenuPanel.setLayout(new GridLayout(6, 1, 0, 15)); // Aumentato a 6 righe per far spazio
        leftMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        leftMenuPanel.setBackground(new Color(60, 63, 65));

        btnVisualizeTypes = createMenuButton("<html><center>Visualizza i tipi di visita<br>a cui sei associato</center></html>");
        btnDaiDisponibilita = createMenuButton("Dai disponibilità");
        btnShowConfirmedVisits = createMenuButton("Mostra visite confermate");

        leftMenuPanel.add(btnVisualizeTypes);
        leftMenuPanel.add(btnDaiDisponibilita);
        leftMenuPanel.add(btnShowConfirmedVisits);

        this.add(leftMenuPanel, BorderLayout.WEST);

        // --- 2. AREA CENTRALE (CARDLAYOUT) ---
        cardLayout = new CardLayout();
        centerCardsPanel = new JPanel(cardLayout);
        centerCardsPanel.setBackground(Color.WHITE);

        // Creazione delle "carte"
        JPanel cardVuota = createEmptyCard();
        cardTipiVisita = createTipiVisitaCard();
        JPanel cardDisponibilita = createDisponibilitaCard();
        JPanel cardVisiteConfermate = createVisiteConfermateCard(); // Nuova Card

        centerCardsPanel.add(cardVuota, "VUOTA");
        centerCardsPanel.add(cardTipiVisita, "TIPI_VISITA");
        centerCardsPanel.add(cardDisponibilita, "DISPONIBILITA");
        centerCardsPanel.add(cardVisiteConfermate, "VISITE_CONFERMATE"); // Aggiunta al layout

        this.add(centerCardsPanel, BorderLayout.CENTER);

        // --- 3. AZIONI MENU LATERALE (Navigazione Interna) ---
        btnVisualizeTypes.addActionListener(e -> cardLayout.show(centerCardsPanel, "TIPI_VISITA"));
        btnDaiDisponibilita.addActionListener(e -> cardLayout.show(centerCardsPanel, "DISPONIBILITA"));
        btnShowConfirmedVisits.addActionListener(e -> cardLayout.show(centerCardsPanel, "VISITE_CONFERMATE"));

        this.setVisible(true);
    }

    // --- METODI HELPER UI ---

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(85, 88, 90));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
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

    // --- SCHERMATA 1: Visualizza i tipi di visita ---
    private JPanel createTipiVisitaCard() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    public void openVisitTypeCard(List<String> list) {
        cardTipiVisita.removeAll(); // Pulisce prima di ricreare

        JLabel lblTitolo = new JLabel("Tipi di visita a te associati:");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 16));
        cardTipiVisita.add(lblTitolo, BorderLayout.NORTH);

        listModelTipiVisita = new DefaultListModel<>();
        if (list != null) {
            listModelTipiVisita.addAll(list);
        }

        JList<String> listTipiVisita = new JList<>(listModelTipiVisita);
        listTipiVisita.setFont(new Font("Arial", Font.PLAIN, 14));

        cardTipiVisita.add(new JScrollPane(listTipiVisita), BorderLayout.CENTER);
        cardTipiVisita.revalidate();
        cardTipiVisita.repaint();
    }

    // --- SCHERMATA 2: Dai disponibilità ---
    private JPanel createDisponibilitaCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        JButton btnApriCalendario = new JButton("Seleziona i giorni di disponibilità");
        btnApriCalendario.setPreferredSize(new Dimension(300, 45));
        btnApriCalendario.setFont(new Font("Arial", Font.BOLD, 14));

        btnApriCalendario.addActionListener(e -> apriFinestraCalendario());

        panel.add(btnApriCalendario);
        return panel;
    }

    // --- SCHERMATA 3: Mostra Visite Confermate (NUOVA) ---
    private JPanel createVisiteConfermateCard() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titolo in alto
        JLabel lblTitolo = new JLabel("Visite confermate:");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitolo, BorderLayout.NORTH);

        // Pannello contenitore per la lista degli eventi (scorre in verticale)
        containerVisiteConfermate = new JPanel();
        containerVisiteConfermate.setLayout(new BoxLayout(containerVisiteConfermate, BoxLayout.Y_AXIS));
        containerVisiteConfermate.setBackground(new Color(245, 245, 245));

        // ScrollPane per permettere lo scrolling se ci sono molti eventi
        JScrollPane scrollPane = new JScrollPane(containerVisiteConfermate);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scorrimento più fluido

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Svuota la lista delle visite confermate.
     * DA CHIAMARE SEMPRE prima di fare il ciclo for per aggiungere i nuovi elementi.
     */
    public void clearConfirmedVisits() {
        containerVisiteConfermate.removeAll();
        containerVisiteConfermate.revalidate();
        containerVisiteConfermate.repaint();
    }

    /**
     * Aggiunge un singolo evento alla grafica.
     * Da invocare dentro il ciclo for nel Controller.
     */
    public void addConfirmedVisitRow(String eventName, String eventDescription, String eventRandezvous, Integer eventStartDate, Integer eventEndDate, List<String> eventUsers) {

        // Creazione del pannello per il singolo evento (stile "Card")
        JPanel eventPanel = new JPanel(new GridLayout(0, 1, 5, 2)); // Grid flessibile verticale
        eventPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        eventPanel.setBackground(Color.WHITE);
        eventPanel.setMaximumSize(new Dimension(800, 250)); // Evita che le schede si allunghino troppo

        // Formattazione Date (Da Unix a Stringa leggibile)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());
        String dataInizio = (eventStartDate != null) ? formatter.format(Instant.ofEpochSecond(eventStartDate)) : "N/D";
        String dataFine = (eventEndDate != null) ? formatter.format(Instant.ofEpochSecond(eventEndDate)) : "N/D";

        // Creazione e aggiunta dei testi (Usiamo HTML per fare il testo in grassetto inline)
        JLabel lblName = new JLabel("<html><b>Evento:</b> " + eventName + "</html>");
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblDesc = new JLabel("<html><b>Descrizione:</b> " + eventDescription + "</html>");
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblRand = new JLabel("<html><b>Luogo d'incontro:</b> " + eventRandezvous + "</html>");
        lblRand.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblDates = new JLabel("<html><b>Data:</b> " + dataInizio + " - " + dataFine + "</html>");
        lblDates.setFont(new Font("Arial", Font.PLAIN, 14));

        // Formattazione lista utenti
        String usersStr = (eventUsers != null && !eventUsers.isEmpty()) ? String.join(", ", eventUsers) : "Nessun utente iscritto";
        JLabel lblUsers = new JLabel("<html><b>Utenti iscritti:</b> " + usersStr + "</html>");
        lblUsers.setFont(new Font("Arial", Font.PLAIN, 14));

        eventPanel.add(lblName);
        eventPanel.add(lblDesc);
        eventPanel.add(lblRand);
        eventPanel.add(lblDates);
        eventPanel.add(lblUsers);

        // Aggiunge la scheda creata al contenitore generale
        containerVisiteConfermate.add(eventPanel);
        // Aggiunge un po' di spazio vuoto (10 pixel) tra un evento e l'altro
        containerVisiteConfermate.add(Box.createRigidArea(new Dimension(0, 10)));

        // Forza la GUI ad aggiornarsi
        containerVisiteConfermate.revalidate();
        containerVisiteConfermate.repaint();
    }


    // --- LOGICA DELLA FINESTRA DI ACQUISIZIONE DATE ---
    private void apriFinestraCalendario() {
        LocalDate oggi = LocalDate.now();
        int mesiDaAggiungere = oggi.getDayOfMonth() <= 15 ? 1 : 2;
        YearMonth meseTarget = YearMonth.from(oggi.plusMonths(mesiDaAggiungere));

        String nomeMeseTarget = meseTarget.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
        nomeMeseTarget = nomeMeseTarget.substring(0, 1).toUpperCase() + nomeMeseTarget.substring(1);

        JFrame frameDate = new JFrame("Segna Disponibilità");
        frameDate.setSize(550, 400);
        frameDate.setLocationRelativeTo(this);
        frameDate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDate.setLayout(new BorderLayout(10, 10));

        JLabel lblIntestazione = new JLabel("Segna i giorni in cui sei disponibile a " + nomeMeseTarget + " " + meseTarget.getYear(), SwingConstants.CENTER);
        lblIntestazione.setFont(new Font("Arial", Font.BOLD, 15));
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
                    btnGiorno.setBackground(new Color(144, 238, 144));
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
        JButton btnSave = new JButton("Conferma Disponibilità"); // Messo come variabile locale (Sicuro)
        btnSave.setPreferredSize(new Dimension(200, 40));

        btnSave.addActionListener(e -> {
            List<Long> dateSelezionateUnix = new ArrayList<>(); // Reso locale per sicurezza

            for (JToggleButton btn : listaBottoniGiorni) {
                if (btn.isSelected()) {
                    int giorno = Integer.parseInt(btn.getText());
                    LocalDate dataSelezionata = meseTarget.atDay(giorno);

                    long unixTimestamp = dataSelezionata.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
                    dateSelezionateUnix.add(unixTimestamp);
                }
            }

            if (onDisponibilitaSaved != null) {
                onDisponibilitaSaved.accept(dateSelezionateUnix);
            }

            JOptionPane.showMessageDialog(frameDate, "Disponibilità salvate con successo!\nGiorni selezionati: " + dateSelezionateUnix.size());
            frameDate.dispose();
        });

        panelBottom.add(btnSave);
        frameDate.add(panelBottom, BorderLayout.SOUTH);

        frameDate.setVisible(true);
    }

    // --- METODI PER IL CONTROLLER ---

    public void setOnDisponibilitaSavedListener(Consumer<List<Long>> listener) {
        this.onDisponibilitaSaved = listener;
    }

    public void addVisualizeTypesListener(ActionListener actionListener) {
        btnVisualizeTypes.addActionListener(actionListener);
    }

    // Nuovo listener per il Controller
    public void addShowConfirmedVisitsListener(ActionListener actionListener) {
        btnShowConfirmedVisits.addActionListener(actionListener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}