package RequestReply.Request;

import java.util.*;
import org.json.*;

public class SetNewOrganizationRequest implements RequestType
{
    private String organizationName;
    private ArrayList<String> territoriesOfCompetence ;

    public SetNewOrganizationRequest
    (
        String organizationName,
        ArrayList<String> territoriesOfCompetence
    ) {
        this.organizationName = organizationName;
        this.territoriesOfCompetence = territoriesOfCompetence;
    }

    public String toJSONString()
    {
        JSONObject json = new JSONObject();
        json.put("organizationName", organizationName);
        json.put("territoriesOfCompetence", territoriesOfCompetence);
        return json.toString();
    }
}