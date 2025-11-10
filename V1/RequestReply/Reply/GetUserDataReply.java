package RequestReply.Reply;

import org.json.*;
import java.util.*;

public class GetUserDataReply implements ReplyType
{
    private boolean loginSuccessful;
    private String userName;
    private String cityOfResidence;
    private int birthYear;
    private String role;
    private String organization;
    private boolean passwordChangeDue;
    private ArrayList<String> allowedVisitType;

    public GetUserDataReply()
    {
        this.loginSuccessful = false;
        this.passwordChangeDue = false;
        this.userName = "";
        this.cityOfResidence = "";
        this.birthYear = 0;
        this.role = "";
        this.organization = "";
    }

    public GetUserDataReply(
        boolean passwordChangeDue,
        String userName,
        String cityOfResidence,
        int birthYear,
        String role,
        String organization,
        ArrayList<String> allowedVisitType
    ) {
        if(!passwordChangeDue)
        {
            this.loginSuccessful = true;
            this.passwordChangeDue = passwordChangeDue;
            this.userName = userName;
            this.cityOfResidence = cityOfResidence;
            this.birthYear = birthYear;
            this.role = role;
            this.organization = organization;
            this.allowedVisitType = allowedVisitType;
        } else {
            this.loginSuccessful = true;
            this.passwordChangeDue = true;
            this.userName = "";
            this.cityOfResidence = "";
            this.birthYear = 0;
            this.role = "";
            this.organization = "";
        }
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("loginSuccessful", loginSuccessful);
        json.put("passwordChangeDue", passwordChangeDue);
        json.put("userName", userName);
        json.put("cityOfResidence", cityOfResidence);
        json.put("birthYear", birthYear);
        json.put("role", role);
        json.put("organization", organization);
        json.put("allowedVisitType", allowedVisitType);
        return json.toString();
    }
}