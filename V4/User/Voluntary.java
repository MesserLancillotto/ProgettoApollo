package User;
import java.util.*;
import org.json.*;
import Client.Client;

public class Voluntary extends User
{
	private static final String ERROR_FIRST_ACCESS = "\nErrore nel primo accesso, riprova più tardi!\n";
	private static final String ERROR_FAILED_LOGIN = "\nLogin fallito, username o password errati!\n";
	private static final String ERROR_LOAD_DATA = "\nErrore nel caricamento dei dati, riprova più tardi!\n";
	private ArrayList <String> allowedVisitType;

	//COSTRUTTORE BASE
	public Voluntary (String userName, String cityOfResidence, int birthYear, String password, String roleTitle, String organization, ArrayList <String> allowedVisitType)
	{
		this.userName = userName;
		this.cityOfResidence = cityOfResidence;
		this.birthYear = birthYear;
        this.password = password;
		this.roleTitle = roleTitle;
		this.organization = organization;
		this.allowedVisitType = allowedVisitType;

		new VoluntaryMenu (userName, this.allowedVisitType);
	}

	//COSTRUTTORE PRIMO ACCESSO
	public Voluntary (String userName,String password, String roleTitle)
	{
		this.userName = userName;
        this.password = password;
		this.roleTitle = roleTitle;
		
		if(first_access())
			new VoluntaryMenu (userName, this.allowedVisitType);
		else
			System.out.println (ERROR_FIRST_ACCESS);
	}
	
	private boolean first_access()
	{
		while(!set_new_password());
		Client.getInstance().get_user_data(userName);
		String getDataResponse = Client.getInstance().make_server_request();
		if (!getDataResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(getDataResponse))
		{
			JSONObject dictionary = new JSONObject(getDataResponse);
			if (dictionary.getBoolean("loginSuccessful"))
			{
				this.birthYear = dictionary.getInt("birthYear");
				this.organization = dictionary.getString("organization");
				this.cityOfResidence = dictionary.getString("cityOfResidence");
				this.allowedVisitType = JSONObjectMethod.jsonArrayConverter(dictionary.getJSONArray("allowedVisitType"));
				return true;
			}
			else
			{
				System.out.println (ERROR_FAILED_LOGIN);
				return false;
			}
		}
		else
		{
			System.out.println (ERROR_LOAD_DATA);
			return false;
		}

	}

	public ArrayList <String> getAllowedVisitTypes ()
	{
		return allowedVisitType;
	}

}
