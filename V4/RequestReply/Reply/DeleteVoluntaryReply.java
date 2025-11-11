package RequestReply.Reply;

import org.json.*;

public class DeleteVoluntaryReply implements ReplyType
{
    private boolean loginSuccessful;
    private int rowsDeleted;

    public DeleteVoluntaryReply
    (
        boolean loginSuccessful,
        int rowsDeleted
    ) {
        this.loginSuccessful = loginSuccessful;
        this.rowsDeleted = rowsDeleted;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("loginSuccessful", loginSuccessful);
        json.put("rowsDeleted", rowsDeleted);
        return json.toString();
    }
}
