package User;

import java.util.*;
import org.json.*;

public class JSONObjectMethod 
{
    /* 
    public static ArrayList <String> jsonArrayConverter (JSONArray jsonArray)
    {
        try
        {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) 
            {
                list.add(jsonArray.getString(i));
            }

            return list;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
        */
    public static ArrayList<String> jsonArrayConverter(JSONArray jsonArray) 
    {
        ArrayList<String> list = new ArrayList<>();
        
        if (jsonArray == null) 
        {
            return list; 
        }
        
        for (int i = 0; i < jsonArray.length(); i++) 
        {
            // Usa optString per evitare eccezioni
            String value = jsonArray.optString(i, null);
            if (value != null) 
            {
                list.add(value);
            }
        }
        
        return list;
    }

    // Ritorna una lista di stringhe in maiuscolo e senza spazi bianchi per confronti
    public static ArrayList<String> jsonArrayConverterForComparisons(JSONArray jsonArray) 
    {
        ArrayList<String> list = new ArrayList<>();
        
        if (jsonArray == null) 
        {
            return list; 
        }
        
        for (int i = 0; i < jsonArray.length(); i++) 
        {
            // Usa optString per evitare eccezioni
            String value = jsonArray.optString(i, null);
            if (value != null) 
            {
                list.add(value.toUpperCase().trim());
            }
        }
        
        return list;
    }

    public static boolean isValidJSONObject(String jsonString) 
    {
        if (jsonString == null || jsonString.trim().isEmpty()) 
        {
            return false;
        }
    
        String trimmed = jsonString.trim();
        
        // Controllo rapido sintattico
        if (!trimmed.startsWith("{") || !trimmed.endsWith("}")) 
        {
            return false;
        }
        
        try 
        {
            new JSONObject(trimmed);
            return true;
        } 
        catch (JSONException e) 
        {
            return false;
        }
    }

    

    public static boolean isValidJSONArray(String jsonString) 
    {
        if (jsonString == null || jsonString.trim().isEmpty()) 
        {
            return false;
        }
    
        String trimmed = jsonString.trim();
        
        // Controllo rapido sintattico
        if (!trimmed.startsWith("[") || !trimmed.endsWith("]")) 
        {
            return false;
        }
        
        try 
        {
            new JSONArray(trimmed);
            return true;
        } 
        catch (JSONException e) 
        {
            return false;
        }
    }
}
