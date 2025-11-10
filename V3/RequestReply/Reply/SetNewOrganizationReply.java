package RequestReply.Reply;

import java.util.*;
import org.json.*;


public class SetNewOrganizationReply implements ReplyType
{
    private boolean accessSuccesful;
    private boolean registrationSuccessful;
    private int territoriesAdded;

    public SetNewOrganizationReply
    (
        boolean accessSuccesful,
        boolean registrationSuccessful,
        Integer territoriesAdded  
    ) {
        this.accessSuccesful = accessSuccesful;
        this.registrationSuccessful = registrationSuccessful;
        this.territoriesAdded = territoriesAdded;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("accessSuccesful", accessSuccesful);
        json.put("registrationSuccessful", registrationSuccessful);
        json.put("territoriesAdded", territoriesAdded);
        return json.toString();
    }
}