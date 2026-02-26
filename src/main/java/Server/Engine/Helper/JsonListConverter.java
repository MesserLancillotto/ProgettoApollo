package Helper;

import org.json.*;
import java.util.*;

public class JsonListConverter
{
    public static <T> JSONArray listToJsonArray(List<T> list) 
    {
        JSONArray jsonArray = new JSONArray();
        if(list == null)
        {
            return jsonArray;
        }
        try 
        {
            for (T item : list) 
            {
                if (item != null) 
                {
                    jsonArray.put(item);
                } 
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static List<Object> jsonArrayToList(JSONArray jsonArray) {
        if (jsonArray == null) 
        {
            return Collections.emptyList();
        }
        List<Object> list = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) 
        {
            try 
            {
                Object item = jsonArray.get(i);
                if (item != JSONObject.NULL) 
                {
                    list.add(item);
                }
            } 
            catch (JSONException e) 
            {
                e.printStackTrace();
            }
        }
        return list;
    }
}