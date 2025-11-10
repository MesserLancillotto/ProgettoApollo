package RequestReply.Request;

import org.json.*;
import java.util.*;
import RequestReply.ComunicationType.*;

public class Request 
{
    private ComunicationType requestType;
    private String userID;
    private String userPassword;
    private String requestBody;

    public Request
    (
        ComunicationType requestType,
        String userID,
        String userPassword,
        RequestType request
    ) {
        this.requestType = requestType;
        this.userID = userID;
        this.userPassword = userPassword;
        this.requestBody = request.toJSONString();
    } 

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("requestType", requestType);
        json.put("userID", userID);
        json.put("userPassword", userPassword);
        for(Map.Entry<String, Object> entry : new JSONObject(requestBody).toMap().entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toString();
    }
}
