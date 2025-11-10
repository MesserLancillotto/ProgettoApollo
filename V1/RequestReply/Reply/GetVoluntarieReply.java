package RequestReply.Reply;

import java.util.*;
import org.json.*;

public class GetVoluntarieReply
{
    private boolean loginSuccessful;    
    private JSONArray result = new JSONArray();
    
    public GetVoluntarieReply(boolean login)
    {
        loginSuccessful = login;
    }

    public void insertUser(
        String userName,
        String cityOfResidence,
        int birthYear,
        String role,
        String userID,
        String organization,
        boolean passwordChangeDue,
        ArrayList<String> allowedVisitType
    ) {
        JSONObject user = new JSONObject();
        user.put("userName", userName);
        user.put("cityOfResidence", cityOfResidence);
        user.put("birthYear", birthYear);
        user.put("role", role);
        user.put("userID", userID);
        user.put("organization", organization);
        user.put("passwordChangeDue", passwordChangeDue);
        user.put("allowedVisitType", new JSONArray(allowedVisitType)); // // ArrayList -> JSONArray
        result.put(user);
    }
    
    public String toJSONString() {
        return result.toString();
    }
}