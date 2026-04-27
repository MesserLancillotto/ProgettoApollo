package Client;
import java.nio.file.*;
import java.util.*;
import java.net.*;
import java.io.*;
import org.json.*;
public class Client
{
    private String value;
    private String whichFile = "";
    private static Client instance;
    private String userID = "";
    private String userPassword = "";
    private boolean isObject = true;

    private String filePath = "configurazione.json";


    public static JSONObject readJSONObjectFromFile(String filename) {
        try {
            if (!filename.endsWith(".json")) {
                filename += ".json";
            }

            String jsonContent = Files.readString(Paths.get(filename));
            return new JSONObject(jsonContent);

        } catch (Exception e) {
            System.err.println("❌ Errore lettura JSONObject da: " + filename);
            System.err.println("   Dettaglio: " + e.getMessage());
            return null;
        }
    }

    public static JSONArray readJSONArrayFromFile(String filename) {
        try {
            if (!filename.endsWith(".json")) {
                filename += ".json";
            }

            String jsonContent = Files.readString(Paths.get(filename));
            return new JSONArray(jsonContent);

        } catch (Exception e) {
            System.err.println("❌ Errore lettura JSONArray da: " + filename);
            System.err.println("   Dettaglio: " + e.getMessage());
            return null;
        }
    }


    private Client ()
    {

    }

    public static synchronized Client getInstance ()
    {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void setUserID (String tmpuUserID)
    {
        this.userID = tmpuUserID;
    }

    public void setUserPassword (String tmpUserPassword)
    {
        this.userPassword = tmpUserPassword;
    }

    public void delete_place(String city, String address)
    {
        isObject = true;
        this.whichFile = "delete_place.json";
    }

    public void set_new_organization(String organizationName, List<String> territoriesOfCompetence)
    {
        isObject = true;
        this.whichFile = "change_password.json";
    }

    public void set_new_user(
            String userName,
            String newPassword,
            String cityOfResidence,
            Integer birthYear
    )
    {
        isObject = true;
        this.whichFile = "set_new_user.json";
    }
    public void edit_password(String newPassword)
    {
        isObject = true;
        this.whichFile = "edit_password.json";
    }

    public void set_voluntary_to_event(
            String event,
            String targetID,
            int time
    ) {
        isObject = true;
        this.whichFile = "set_voluntary_to_event.json";
    }

    public void set_disponibility (String eventName, int unixDate)
    {
        isObject = true;
        this.whichFile = "set_disponibility.json";
    }

    public void get_voluntaries(Map<String, Object> filters)
    {
        isObject = false;
        this.whichFile = "get_voluntaries.json";
    }

    public void get_user_data(String target)
    {
        isObject = true;
        this.whichFile = "get_user_data_configurator_pw_false.json";
    }

    public void remove_voluntary (String voluntaryID)
    {
        isObject = true;
        this.whichFile = "change_password.json";
    }

    public void open_voluntary_disponibility ()
    {
        isObject = true;
        this.whichFile = "change_password.json";
    }
    public void close_voluntary_disponibility ()
    {
        isObject = true;
        this.whichFile = "change_password.json";
    }

    public void set_disponibility_request (List <Integer> disponibility)
    {
        isObject = true;
        this.whichFile = "set_disponibility_request.json";
    }

    public void set_max_people_subscription (int value)
    {
        JSONObject maxPeopleSubscription = new JSONObject();
        maxPeopleSubscription.put("MaxPeople", value);

        try (FileWriter file = new FileWriter(filePath)) {
            // Il numero 4 passato a toString() serve per l'indentazione (pretty print),
            // così il file sarà leggibile anche a occhio nudo andando a capo!
            file.write(maxPeopleSubscription.toString());
            System.out.println("File JSON creato e salvato con successo!");
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file: " + e.getMessage());
        }
    }

    public int get_max_people_subscription()
    {
        int maxPeople = -1;
        try {
            String contenutoFile = new String(Files.readAllBytes(Paths.get(filePath)));

            // Creiamo un nuovo JSONObject passandogli la stringa appena letta
            JSONObject jsonLetto = new JSONObject(contenutoFile);

            // Otteniamo il valore associato alla chiave "MaxPeople"
            // Usiamo getInt() perché sappiamo che è un numero intero
            maxPeople = jsonLetto.getInt("MaxPeople");

            System.out.println("Valore recuperato dal file: " + maxPeople);

        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        } catch (org.json.JSONException e)
        {
            System.err.println("Errore nel parsing del JSON (forse la chiave non esiste?): " + e.getMessage());
        }

        return maxPeople;
    }

    public void set_new_event(
            String eventName,
            String description,
            String city,
            String address,
            String meetingPoint,
            int startDate,
            int endDate,
            String organizationName,
            int minimumUsers,
            int maximumUsers,
            int maximumFriends,
            String visitType,
            float price,
            ArrayList<String> visitDays,
            ArrayList<Integer> startHour,
            ArrayList<Integer> duration
    )
    {
        isObject = true;
        this.whichFile = "set_new_event.json";
    }

    public void get_event(Map<String, Object> filters)
    {
        isObject = false;
        this.whichFile = "get_event.json";
    }

    public void set_closed_days(int startDate, int endDate, String organization)
    {
        isObject = true;
        this.whichFile = "set_closed_days_successful.json";
    }

    public void delete_visit_type_from_place (String city, String address, String visitType)
    {
        isObject = true;
        this.whichFile = "delete_visit_type_from_place.json";
    }

    public void change_password (String newPassword)
    {
        isObject = true;
        this.whichFile = "change_password.json";
    }

    public void set_user_subscription_to_event (List<String> users, String eventName, Integer startDate) {}

    public void delete_voluntary (String voluntaryID)
    {
        isObject = true;
    }

    public String make_server_request()
    {
        if (isObject)
            value = readJSONObjectFromFile(whichFile).toString();
        else
            value = readJSONArrayFromFile(whichFile).toString();
        return value;
    }
}
