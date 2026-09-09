package User;

import java.awt.event.ActionListener;

public interface IFirstAccessView {
    String getName();
    String getSurname();
    String getBirthYear();
    String getCity();
    String getNewPassword();
    String getConfirmPassword();
    void addConfirmListener(ActionListener listener);
    void showMessage(String message);
    void setVisible(boolean visible);
    void clearAllFields();
    void clearPasswordFields();
}
