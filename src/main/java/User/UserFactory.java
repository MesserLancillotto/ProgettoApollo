package User;

import org.json.JSONObject;
import java.util.*;
import java.util.function.*;

public class UserFactory
{
    private static final Map<String, BiFunction<String, JSONObject, UserModel>> CREATORS =
            Map.of(
                    "CONFIGURATOR", UserFactory::create_configurator,
                    "VOLUNTARY", UserFactory::create_voluntary,
                    "BENEFICIARY", UserFactory::create_beneficiary
            );

    public static UserModel create_user (String username, JSONObject usrData)
    {
        if (username == null || usrData == null) {
            return null;
        }

        int dotIndex = username.indexOf('.');
        String userType = (dotIndex != -1) ? username.substring(0, dotIndex).toUpperCase() : "";

        // Fallback: se il nome utente non ha il prefisso col punto, prova a leggere il ruolo dal JSON
        if (!CREATORS.containsKey(userType) && usrData.has("role")) {
            userType = usrData.optString("role", "").toUpperCase();
        }

        BiFunction<String, JSONObject, UserModel> creator = CREATORS.get(userType);
        if (creator != null) {
            return creator.apply(username, usrData);
        }
        else
            return null;
    }

    private static UserModel create_configurator(String username, JSONObject userData)
    {
        try
        {
            return new ConfiguratorModel(
                    username,
                    userData.getString("cityOfResidence"),
                    userData.getInt("birthYear"),
                    UserType.valueOf(userData.getString("role").toUpperCase()),
                    userData.getString("organization"),
                    userData.getBoolean("passwordChangeDue")
            );
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static UserModel create_voluntary (String username, JSONObject userData)
    {
        try
        {
            return new VoluntaryModel(
                    username,
                    userData.getString("cityOfResidence"),
                    userData.getInt("birthYear"),
                    UserType.valueOf(userData.getString("role").toUpperCase()),
                    userData.getString("organization"),
                    userData.getBoolean("passwordChangeDue"),
                    userData.getJSONArray("allowedVisits")
            );
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static UserModel create_beneficiary (String username, JSONObject userData)
    {
        try
        {
            return new BeneficiaryModel(
                    username,
                    userData.getString("cityOfResidence"),
                    userData.getInt("birthYear"),
                    UserType.valueOf(userData.getString("role").toUpperCase()),
                    userData.getString("organization"),
                    userData.getBoolean("passwordChangeDue")
            );
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
