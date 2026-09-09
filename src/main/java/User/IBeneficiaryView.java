package User;

import java.awt.event.ActionListener;
import java.util.List;

public interface IBeneficiaryView {

    class EventSelectionData {
        public String name, desc, place;
        public Integer startDate, endDate;

        public EventSelectionData(String name, String desc, String place, Integer startDate, Integer endDate) {
            this.name = name; this.desc = desc; this.place = place;
            this.startDate = startDate; this.endDate = endDate;
        }
    }

    interface BookingConfirmListener {
        void onConfirm(String eventName, Integer eventStartDate, List<String> nominativi);
    }

    void setVisible(boolean visible);
    void showMessage(String msg);
    void addEffettuaPrenotazioneListener(ActionListener listener);
    void addGestisciPrenotazioneListener(ActionListener listener);
    void addPrenotaActionListener(ActionListener al);
    void addDisdiciActionListener(ActionListener al);

    void clearPrenotazioniDisponibili();
    void addEventoPrenotabileRow(String eventName, String eventDescription, String eventRandezvous, Integer eventStartDate, Integer eventEndDate);
    EventSelectionData getSelectedBookingData();
    void openBookingDialog(EventSelectionData data, int maxParticipants, BookingConfirmListener listener);

    void clearPrenotazioniAttive();
    void addEventoDaDisdireRow(String eventName, String eventDescription, String eventRandezvous, Integer eventStartDate, Integer eventEndDate);
    EventSelectionData getSelectedManageData();
    void openCancelConfirmDialog(EventSelectionData data, Runnable onConfirm);
}
