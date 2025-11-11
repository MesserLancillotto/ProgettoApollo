package RequestReply.Reply;

import org.json.*;

public class DeleteVoluntaryRequest implements ReplyType
{
    private String targetID;

    public DeleteVoluntaryRequest
    (
        String targetID
    ) {
        this.targetID = targetID;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("targetID", targetID);
        return json.toString();
    }
}
