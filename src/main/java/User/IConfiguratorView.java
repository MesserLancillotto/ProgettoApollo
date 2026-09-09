package User;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

public interface IConfiguratorView {
    void setVisible(boolean visible);
    void showMessage(String message);

    // Listener registrazione menu e azioni
    void addGestisciVolontariAzioneListener(ActionListener listener);
    void addConfirmCloseVoluntariesDisponibilties(ActionListener listener);
    void addOpenVoluntaryDisponibility(ActionListener listener);
    void addAggiungiTipoVisitaVolontarioListener(ActionListener listener);
    void addGestisciLuoghiAzioneListener(ActionListener listener);
    void addAggiungiLuogoListener(ActionListener listener);
    void addChangeMaxPeopleListener(ActionListener listener);
    void addClosedDaysListener(ActionListener listener);

    // Gestione Volontari
    void open_gestione_volontari_view(Runnable onRemoveConfirm);
    void clearVolontariList();
    void addVolontarioRow(String voluntaryID, List<String> visitTypes);
    String getSelectedVoluntaryID();

    // Aggiunta Tipo Visita a Volontario
    void open_add_visit_type_voluntary_view(Runnable onAddClick);
    void clearAddVisitVolontariList();
    void addAddVisitVolontarioRow(String voluntaryID, List<String> visitTypes);
    String getSelectedAddVisitVoluntaryID();
    void open_select_new_visit_type_dialog(String voluntaryID, List<String> newVisitTypes, Consumer<String> onConfirm);

    // Gestione Luoghi
    void open_gestione_luoghi_view(Runnable onAddType, Runnable onRemoveType, Runnable onRemovePlace);
    void clearLuoghiList();
    void addLuogoRow(String city, String address, String visitType);
    String getSelectedLuogoKey();
    List<String> getSelectedLuogoVisitTypes();
    void open_add_visit_type_to_place_dialog(String city, String address, List<String> availableTypes, Consumer<String> onConfirm);
    void open_remove_visit_type_from_place_dialog(String city, String address, List<String> visitTypes, Consumer<String> onConfirm);
    boolean confirmDeletePlace(String city, String address);

    // Aggiungi Luogo
    void open_aggiungi_luogo_view(ActionListener saveListener);
    void close_aggiungi_luogo_view();
    String getNewPlaceCity();
    String getNewPlaceAddress();
    String getNewPlaceDescription();
    String getNewPlaceOrganization();
    String getNewPlaceVisitType();
    String getNewPlaceDefaultVoluntary();

    // Impostazioni
    void open_change_max_number_subrsctipion_view(int currentMaxValue, ActionListener saveListener);
    void close_change_max_number_subrsctipion_view();
    String getNewMaxSub();
    void open_closed_days_view(ActionListener listener);
    List<Long> getDatePrecluse();
}
