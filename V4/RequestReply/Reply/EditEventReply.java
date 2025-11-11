package RequestReply.Reply;

import org.json.*;

public class EditEventReply implements ReplyType
{
    private boolean accessSuccesful;
    private boolean editSuccesful;
    private boolean credentialsInsufficent;
    private boolean eventNotExisting;

    public EditEventReply(boolean accessSuccesful)
    {
        this.accessSuccesful = accessSuccesful;
        this.editSuccesful = false;
        this.credentialsInsufficent = false;
        this.eventNotExisting = false;
    }

    public EditEventReply
    (
        boolean accessSuccesful,
        boolean editSuccesful,
        boolean credentialsInsufficent,
        boolean eventNotExisting
    ) { 
        this.accessSuccesful = accessSuccesful;
        this.editSuccesful = editSuccesful;
        this.credentialsInsufficent = credentialsInsufficent;
        this.eventNotExisting = eventNotExisting;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("accessSuccesful", accessSuccesful);
        json.put("editSuccesful", editSuccesful);
        json.put("credentialsInsufficent", credentialsInsufficent);
        json.put("eventNotExisting", eventNotExisting);
        return json.toString();
    }
}