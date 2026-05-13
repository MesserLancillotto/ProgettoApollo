package Comunication.Request;

import java.util.*;
import org.json.*;

import Comunication.ComunicationType.ComunicationType;
import Comunication.Request.Interfaces.*;

public class GetPossibleVisitsRequest extends AuthenticatedRequest
{    
    public GetPossibleVisitsRequest
    (
        String organization
    ) {
        super(ComunicationType.GET_POSSIBLE_VISITS, "", "");
        json.put("organization", organization);
    }
}