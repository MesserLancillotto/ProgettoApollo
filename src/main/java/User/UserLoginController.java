package User;
import Client.Client;
import org.json.JSONObject;

import java.util.HashMap;

public class UserLoginController
{
    private UserLoginView view;
    private UserLoginModel model;

    private HashMap<UserType, Runnable> userToCreate = new HashMap<>();
    private UserModel userModel;

    private void initialize_user_to_create ()
    {
        userToCreate.put(UserType.CONFIGURATOR, () -> configurator_created ());
        userToCreate.put(UserType.VOLUNTARY, () -> voluntary_created());
        userToCreate.put(UserType.BENEFICIARY, () -> beneficiary_created());
    }

    public UserLoginController(UserLoginModel model, UserLoginView view)
    {
        initialize_user_to_create();
        this.view = view;
        this.model = model;

        this.view.addLoginListener(e -> handleLoginTry());
        this.view.addRegisterListener(e -> handleRegister());
    }

    private void handleRegister ()
    {
        view.setVisible(false);
        BeneficiaryView beneficiaryView = new BeneficiaryView();
        BeneficiaryController benController = new BeneficiaryController(beneficiaryView, new BeneficiaryModel(true));
    }

    private void handleLoginTry ()
    {
        model.setUsername(view.getUsername());
        model.setPassword(view.getPassword());
        Client.getInstance().setUserID(model.getUsername());
        Client.getInstance().setUserPassword(model.getPassword());
        Client.getInstance().get_personal_data(model.getUsername());
        String loginResult = Client.getInstance().make_server_request();
        try
        {
            JSONObject dictionaryResponse = login_is_valid(loginResult);
            if (dictionaryResponse != null)
            {
                userModel = UserFactory.create_user(model.getUsername(), dictionaryResponse);
                view.setVisible(false);
                if (userModel == null)
                    throw new Exception();
                else
                {
                    userToCreate.get(userModel.getRoleTitle()).run();
                }
            }
        }
        catch (Exception e)
        {
            this.view.showMessage("Errore nella comunicazione col server!");
        }
    }

    // Metodi per HashMap
    private void configurator_created ()
    {
        ConfiguratorView configuratorView = new ConfiguratorView();
        ConfiguratorController confController = new ConfiguratorController(configuratorView, (ConfiguratorModel) userModel);
    }
    private void voluntary_created ()
    {
        VoluntaryView voluntaryView = new VoluntaryView();
        VoluntaryController volController = new VoluntaryController(voluntaryView, (VoluntaryModel) userModel);
    }
    private void beneficiary_created ()
    {
        BeneficiaryView beneficiaryView = new BeneficiaryView();
        BeneficiaryController benController = new BeneficiaryController(beneficiaryView, (BeneficiaryModel) userModel);
    }

    // Controlli
    private JSONObject login_is_valid (String response)
    {
        if (response.trim().isEmpty())
        {
            this.view.showMessage("Errore nella comunicazione col server!");
            return null;
        }
        else
        {
            try
            {
                JSONObject userData = new JSONObject(response);
                if (userData.getBoolean("loginSuccessful"))
                {
                    return userData;
                }
                else
                {
                    this.view.showMessage("Credenziali errate!");
                    return null;
                }
            }
            catch (Exception e)
            {
                this.view.showMessage("Errore nella comunicazione col server!");
                return null;
            }
        }
    }
}
