package User;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;
import Client.Client;

public class UserLogin 
{   
    private static HashMap <String, UserCreator> userFactory = new HashMap <>();
	private static HashMap <String, UserFirstAccessCreator> userFirstAccessFactory = new HashMap <>();

    private static final String TEMPORARY_SYMBOL = "T";
    private static final String CONFIGURATOR_SYMBOL = "C";
    private static final String VOLUNTARY_SYMBOL = "V";
    private static final String BENEFICIARY_SYMBOL = "B";
    private static final String APP_NAME = "Apollo V1.0";
    private static final String WHAT_USER_DO_LOGIN = "Vuoi eseguire il login";
    private static final String WHAT_USER_DO_CREATE_ACCOUNT = "Vuoi creare un nuovo account";
    private static final String INSERT_USERNAME = "Inserisci username";
    private static final String INSERT_PASSWORD = "Inserisci password";
    private static final String MSG_LOGIN_SUCCESSFUL = "LOGIN EFFETTUATO CON SUCCESSO";
    private static final String ERROR_LOGIN = "\nLogin fallito, username o password errati!\n";
    private static final String ERROR_USER_TYPE_NOT_FOUND = "\nERRORE! Login fallito";
    private static final String ERROR_CONNECTION_SERVER = "\nErrore di connessione col server, riprova più tardi!\n";

    private static void initialize_user_factory ()
    {
        userFactory.put (CONFIGURATOR_SYMBOL, (username, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType) -> new Configurator (username, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType));  
        userFactory.put (VOLUNTARY_SYMBOL, (username, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType) -> new Voluntary (username, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType));  

		userFirstAccessFactory.put (CONFIGURATOR_SYMBOL, (tmpUsernName, tmpPassword, roleTitle) -> new Configurator (tmpUsernName, tmpPassword, roleTitle));
        userFirstAccessFactory.put (VOLUNTARY_SYMBOL, (tmpUsernName, tmpPassword, roleTitle) -> new Voluntary (tmpUsernName, tmpPassword, roleTitle));
    }

	public static void main (String[] args)
	{
        initialize_user_factory();
        UserTui.printWelcomeMessage();
        UserTui.printCenteredTitle(APP_NAME);
        if (UserTui.getYesNoAnswer(WHAT_USER_DO_LOGIN))
		    login();

        UserTui.printExitMessage();
	}

	private static void login ()
	{
        boolean loginSuccessfull = false;
        do
        {
            String userName = UserTui.getString(INSERT_USERNAME);
            String password = UserTui.getString(INSERT_PASSWORD);
            Client.getInstance().setUserID (userName);
            Client.getInstance().setUserPassword (password);

            Client.getInstance().get_user_data(userName);
            String loginResult = Client.getInstance().make_server_request();
            if (!loginResult.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(loginResult))
            {   
                JSONObject dictionary = new JSONObject(loginResult);
                loginSuccessfull = dictionary.getBoolean("loginSuccessful");
                
                if (loginSuccessfull)
                {
                    UserTui.printCenteredTitle(MSG_LOGIN_SUCCESSFUL);
                    boolean passwordNeedsToBeChanged = dictionary.getBoolean("passwordChangeDue");
                    String roleTitle = dictionary.getString("role");
                    if (passwordNeedsToBeChanged)
                    {
                        if (userName.substring(0,1).equals (TEMPORARY_SYMBOL))
                            userFirstAccessFactory.get(CONFIGURATOR_SYMBOL).create (userName, password, roleTitle);
                        else
                        {
                            if (userFirstAccessFactory.containsKey(userName.substring(0,1)))
                            {
                                userFirstAccessFactory.get(userName.substring(0,1)).create(userName, password, roleTitle);
                            }
                            else
                                System.out.println (ERROR_USER_TYPE_NOT_FOUND);
                        }
                    }
                    else 
                    {
                        String cityOfResidence = dictionary.getString("cityOfResidence");
                        int birthYear = dictionary.getInt("birthYear");
                        String organization = dictionary.getString("organization");
                        ArrayList <String> allowedVisitType = JSONObjectMethod.jsonArrayConverter(dictionary.getJSONArray("allowedVisitType"));
                        if (userFirstAccessFactory.containsKey(userName.substring(0,1)))
                        {
                            userFactory.get(userName.substring(0,1)).create (userName, cityOfResidence, birthYear, password, roleTitle, organization, allowedVisitType);
                        }
                        else
                            System.out.println (ERROR_USER_TYPE_NOT_FOUND);
                    }
                }
                else
                {
                    System.out.println (ERROR_LOGIN);
                }
            }
            else
            {
                System.out.println (ERROR_CONNECTION_SERVER);
                loginSuccessfull = true;
            }
        }while (!loginSuccessfull);
	}    
}
