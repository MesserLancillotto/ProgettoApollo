package User;

import org.json.JSONArray;
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
        if (usrData == null) {
            return null;
        }

        // Se i dati utente sono annidati dentro la chiave "user" (formato GetPersonalDataReply)
        JSONObject userData = usrData.has("user") ? usrData.getJSONObject("user") : usrData;

        // Determina il ruolo: prima controlla "role" nel JSON
        String userType = "";
        if (userData.has("role")) {
            userType = userData.getString("role").toUpperCase();
        } else if (username != null && username.contains(".")) {
            // Fallback: primo segmento dell'username (usato nei vecchi mock)
            int dotIndex = username.indexOf('.');
            userType = username.substring(0, dotIndex).toUpperCase();
        }

        BiFunction<String, JSONObject, UserModel> creator = CREATORS.get(userType);
        if (creator != null) {
            UserModel model = creator.apply(username, userData);
            if (model != null) {
                // Popola anche name e surname se presenti
                String name = userData.optString("name", "");
                String surname = userData.optString("surname", "");
                if ((name.isEmpty() || surname.isEmpty()) && username != null && username.contains(".")) {
                    String[] parts = username.split("\\.");
                    if (name.isEmpty() && parts.length > 0) name = parts[0];
                    if (surname.isEmpty() && parts.length > 1) surname = parts[1];
                }
                model.setName(name);
                model.setSurname(surname);
            }
            return model;
        }
        else {
            System.err.println("❌ Ruolo utente sconosciuto: " + userType);
            return null;
        }
    }

    private static String getCity(JSONObject userData) {
        if (userData.has("city")) return userData.getString("city");
        if (userData.has("cityOfResidence")) return userData.getString("cityOfResidence");
        return "";
    }

    private static int getBirthYear(JSONObject userData) {
        if (userData.has("birth_yy")) return userData.getInt("birth_yy");
        if (userData.has("birthYear")) return userData.getInt("birthYear");
        return 2000;
    }

    private static boolean getPasswordChangeDue(JSONObject userData) {
        if (userData.has("changePasswordDue")) return userData.getBoolean("changePasswordDue");
        if (userData.has("passwordChangeDue")) return userData.getBoolean("passwordChangeDue");
        return false;
    }

    private static String getOrganization(JSONObject userData) {
        return userData.optString("organization", "");
    }

    private static UserModel create_configurator(String username, JSONObject userData)
    {
        try
        {
            return new ConfiguratorModel(
                    username,
                    getCity(userData),
                    getBirthYear(userData),
                    UserType.CONFIGURATOR,
                    getOrganization(userData),
                    getPasswordChangeDue(userData)
            );
        }
        catch (Exception e)
        {
            System.err.println("❌ Errore creazione ConfiguratorModel: " + e.getMessage());
            return null;
        }
    }

    private static UserModel create_voluntary(String username, JSONObject userData)
    {
        try
        {
            JSONArray allowedVisits = new JSONArray();
            if (userData.has("allowedVisits")) {
                JSONArray ja = userData.getJSONArray("allowedVisits");
                for (int i = 0; i < ja.length(); i++) {
                    Object item = ja.get(i);
                    if (item instanceof String s) {
                        allowedVisits.put(s);
                    } else if (item instanceof JSONObject jo) {
                        allowedVisits.put(jo.optString("visitType", jo.optString("name", "")));
                    }
                }
            } else if (userData.has("allowedVisitType")) {
                JSONArray ja = userData.getJSONArray("allowedVisitType");
                for (int i = 0; i < ja.length(); i++) {
                    allowedVisits.put(ja.getString(i));
                }
            }

            return new VoluntaryModel(
                    username,
                    getCity(userData),
                    getBirthYear(userData),
                    UserType.VOLUNTARY,
                    getOrganization(userData),
                    getPasswordChangeDue(userData),
                    allowedVisits
            );
        }
        catch (Exception e)
        {
            System.err.println("❌ Errore creazione VoluntaryModel: " + e.getMessage());
            return null;
        }
    }

    private static UserModel create_beneficiary(String username, JSONObject userData)
    {
        try
        {
            return new BeneficiaryModel(
                    username,
                    getCity(userData),
                    getBirthYear(userData),
                    UserType.BENEFICIARY,
                    getOrganization(userData),
                    getPasswordChangeDue(userData)
            );
        }
        catch (Exception e)
        {
            System.err.println("❌ Errore creazione BeneficiaryModel: " + e.getMessage());
            return null;
        }
    }
}
