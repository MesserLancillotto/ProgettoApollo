package RequestReply.Reply;

import org.json.*;

public class DeleteVisitTypeFromPlaceReply implements ReplyType
{
    private boolean loginSuccessful;
    private int rowsDeleted;

    public DeleteVisitTypeFromPlaceReply
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
