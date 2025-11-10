package User;

import Client.Client;
import org.json.*;
public abstract class User 
{
	private static final String PASSWORD_CHANGE_SUCCESSFUL = "La password è stata cambiata!";
	private static final String GET_DATA_PASSWORD = "Inserisci la nuova password: ";
	private static final String ERROR_SERVER = "\nERRORE! Non è stato possibile comunicare col server";
	private static final String ERROR_SERVER_COMMUNICATION = "Errore, non è stato possibile cambiare la password";
	
    protected String userName;
	protected String cityOfResidence;
	protected int birthYear;
	protected String roleTitle;
	protected String userID;
    protected String password;
	protected String organization;

	// metodo per creare nuova password
	protected boolean set_new_password ()
	{
		String tmpPassword = UserTui.getPasswordFromUser (GET_DATA_PASSWORD);
		Client.getInstance().edit_password(tmpPassword);
		String replyChangePassword = Client.getInstance().make_server_request();
		if (!replyChangePassword.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(replyChangePassword))
		{
			JSONObject dictionary = new JSONObject(replyChangePassword);
			boolean changePasswordSuccessfull = dictionary.getBoolean("passwordChangeSuccessful");

			if (changePasswordSuccessfull)
			{
				System.out.println (PASSWORD_CHANGE_SUCCESSFUL);
				Client.getInstance().setUserPassword(tmpPassword);
				return true;
			}
			else
				System.out.println (ERROR_SERVER_COMMUNICATION);
		}
		else
			System.out.println (ERROR_SERVER);
		return false;
	}
}
