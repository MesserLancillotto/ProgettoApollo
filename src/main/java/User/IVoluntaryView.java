package User;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

public interface IVoluntaryView {
    void setVisible(boolean visible);
    void showMessage(String message);
    void addVisualizeTypesListener(ActionListener listener);
    void addShowConfirmedVisitsListener(ActionListener listener);
    void setOnDisponibilitaSavedListener(Consumer<List<Long>> listener);
    void openVisitTypeCard(List<String> list);
    void clearConfirmedVisits();
    void addConfirmedVisitRow(String eventName, String eventDescription, String eventRandezvous,
                              Integer eventStartDate, Integer eventEndDate, List<String> eventUsers);
}
