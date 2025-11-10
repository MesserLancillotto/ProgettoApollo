package User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.json.*;


public class JSONObjectCreator 
{
    private static final String KEY = "maxPeopleForSubscription";
    private static final String CONFIG_FILE = "config.json";

    public static void setMaxPeopleForSubscription (int maxPeopleForSubscription)
    {
        JSONObject json = new JSONObject();
        json.put(KEY, maxPeopleForSubscription);
        saveJsonToFile (json.toString(), CONFIG_FILE);
    }

    private static void saveJsonToFile(String jsonString, String filePath) 
    {
        try 
        {
            // Scrive la stringa JSON nel file
            Files.write(Paths.get(filePath), 
                       jsonString.getBytes(), 
                       StandardOpenOption.CREATE, 
                       StandardOpenOption.TRUNCATE_EXISTING);
            
        } 
        catch (IOException e) 
        {
            System.err.println("Errore nel salvataggio: " + e.getMessage());
        }
    }

    public static int getMaxPeopleForSubscription ()
    {
        JSONObject json = new JSONObject(readJsonFromFile(CONFIG_FILE));
        return json.getInt(KEY);
    }

    private static String readJsonFromFile(String filePath) 
    {
        try 
        {
            // Legge tutto il contenuto del file come stringa
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            return jsonString;    
        } 
        catch (IOException e) 
        {
            System.err.println("Errore nella lettura: " + e.getMessage());
            return null;
        }
    }
    
}

