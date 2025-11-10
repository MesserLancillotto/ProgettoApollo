package RequestReply.Request;

import org.json.*;

public class GetUserDataRequest implements RequestType
{
    private String target;

    public GetUserDataRequest(String target){
        this.target = target;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("target", target);
        return json.toString();
    }
}