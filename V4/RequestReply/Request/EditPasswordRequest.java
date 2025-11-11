package RequestReply.Request;

import org.json.*;

public class EditPasswordRequest implements RequestType 
{

    private String newPassword;

    public EditPasswordRequest
    (
        String newPassword
    ) {
        this.newPassword = newPassword;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("newPassword", newPassword);
        return json.toString();
    }
}