package User;
import javax.swing.*;


public class UserLogin
{
    public static void main(String[] args)
    {
        // Assicura che l'interfaccia grafica venga creata nell'EDT
        SwingUtilities.invokeLater(() ->
        {
            UserLoginModel model = new UserLoginModel();
            UserLoginView view = new UserLoginView();
            UserLoginController controller = new UserLoginController (model, view);
        });
    }
}
