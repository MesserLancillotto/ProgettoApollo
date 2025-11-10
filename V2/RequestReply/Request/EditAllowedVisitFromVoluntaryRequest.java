package RequestReply.Request;

import org.json.*;
import java.util.*;

public class EditAllowedVisitFromVoluntaryRequest implements RequestType 
{
    private String targetID;
    private ArrayList<String> append;
    private ArrayList<String> remove;

    public EditAllowedVisitFromVoluntaryRequest
    (
        String targetID,
        ArrayList<String> append,
        ArrayList<String> remove
    ) {
        this.targetID = targetID;
        this.append = append;
        this.remove = remove;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("targetID", targetID);
        json.put("append", append); 
        json.put("remove", remove); 
        return json.toString();
    }
}