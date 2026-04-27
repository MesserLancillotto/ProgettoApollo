package User;
import Client.Client;
import org.json.JSONObject;

public class UserLoginController
{
    private UserLoginView view;
    private UserLoginModel model;

    public UserLoginController(UserLoginModel model, UserLoginView view)
    {
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
        Client.getInstance().get_user_data(model.getUsername());
        String loginResult = Client.getInstance().make_server_request();
        try
        {
            JSONObject dictionaryResponse = login_is_valid(loginResult);
            if (dictionaryResponse != null)
            {
                UserModel userModel = UserFactory.create_user(model.getUsername(), dictionaryResponse);
                view.setVisible(false);
                if (userModel == null)
                    throw new Exception();
                else
                {
                    switch (userModel.getRoleTitle())
                    {
                        case CONFIGURATOR:
                            ConfiguratorView configuratorView = new ConfiguratorView();
                            ConfiguratorController confController = new ConfiguratorController(configuratorView, (ConfiguratorModel) userModel);
                            break;
                        case VOLUNTARY:
                            VoluntaryView voluntaryView = new VoluntaryView();
                            VoluntaryController volController = new VoluntaryController(voluntaryView, (VoluntaryModel) userModel);
                            break;
                        case BENEFICIARY:
                            BeneficiaryView beneficiaryView = new BeneficiaryView();
                            BeneficiaryController benController = new BeneficiaryController(beneficiaryView, (BeneficiaryModel) userModel);
                            break;
                        default:
                            throw new Exception();
                    }
                }
            }
        }
        catch (Exception e)
        {
            this.view.showMessage("Errore nella comunicazione col server!");
        }
    }

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
