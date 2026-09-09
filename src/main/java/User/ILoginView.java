package User;

import java.awt.event.ActionListener;

public interface ILoginView {
    String getUsername();
    String getPassword();
    void clearFields();
    void showMessage(String message);
    void setVisible(boolean visible);
    void addLoginListener(ActionListener listener);
    void addRegisterListener(ActionListener listener);
}
