package RequestReply.Reply;

import org.json.*;

public class SetNewEventReply implements ReplyType
{
    private boolean accessSuccesful;
    private boolean registrationSuccesful;
    private boolean duringClosedPeriods;

    public SetNewEventReply
    (
        boolean accessSuccesful,
        boolean registrationSuccesful,
        boolean duringClosedPeriods
    ) { 
        this.accessSuccesful = accessSuccesful;;
        this.registrationSuccesful = registrationSuccesful;
        this.duringClosedPeriods = duringClosedPeriods;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("accessSuccesful", accessSuccesful);
        json.put("registrationSuccesful", registrationSuccesful);
        json.put("duringClosedPeriods", duringClosedPeriods);
        return json.toString();
    }
}