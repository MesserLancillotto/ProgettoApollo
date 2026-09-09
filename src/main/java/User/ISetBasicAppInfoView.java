package User;

import java.awt.event.ActionListener;
import java.util.List;

public interface ISetBasicAppInfoView {
    String getOrganizationName();
    Integer getMaxPeoplePerBooking();
    List<String> getLocations();
    void addConfirmListener(ActionListener listener);
    void showMessage(String message);
    void clearFields();
    void setVisible(boolean visible);
    void dispose();
}
