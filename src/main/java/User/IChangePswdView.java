package User;

import java.awt.event.ActionListener;

public interface IChangePswdView {
    String getTxtNuovaPassword();
    String getTxtConfermaPassword();
    void addConfirmListener(ActionListener actionListener);
    void addCancelListener(ActionListener actionListener);
    void showMessage(String message);
    void setVisible(boolean visible);
    void clearAllFields();
}
