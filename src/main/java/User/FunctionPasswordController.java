package User;

import Client.Client;
import org.json.JSONObject;

public class FunctionPasswordController extends FunctionController
{
    private UserModel model;
    private ChangePswdView view;
    private Runnable onClompleteAction;
    public FunctionPasswordController (UserModel model, ChangePswdView view, Runnable onCompleteAction)
    {
        this.model = model;
        this.view = view;
        this.onClompleteAction = onCompleteAction;
        view.addConfirmListener(e -> handle_confirm_psw_change());
        view.addCancelListener(e -> handle_cancel_psw_change());
    }
    @Override
    public Boolean execute ()
    {
        return false;
    }

    private void handle_confirm_psw_change ()
    {
        Boolean changeSucessfull = false;
        String newPswd = view.getTxtNuovaPassword();
        if (UserInputValidator.passwordIsSafe(newPswd))
        {
            if (newPswd.equals(view.getTxtConfermaPassword()))
            {
                Client.getInstance().change_password(newPswd);
                String changePswresponse = Client.getInstance().make_server_request();
                try
                {
                    JSONObject changePswDictionary = new JSONObject(changePswresponse);
                    if (changePswDictionary.getBoolean("loginSuccessful") && changePswDictionary.getBoolean("updateSuccessful"))
                    {
                        view.showMessage("Password cambiata con successo!");
                        changeSucessfull = true;
                        Client.getInstance().change_password(newPswd);
                    }
                    else
                        throw new Exception();
                }
                catch (Exception e)
                {
                    view.showMessage("Errore nella comunizione col server");
                }
            }
            else
                view.showMessage("Le password non coincidono");
        }
        else
            view.showMessage("La password non soddisfa i requisiti");

        if (changeSucessfull)
        {
            handle_cancel_psw_change();
        }
        else
        {
            view.clearAllFields();
        }
    }

    private void handle_cancel_psw_change ()
    {
        view.setVisible(false);
        onClompleteAction.run();
    }
}
