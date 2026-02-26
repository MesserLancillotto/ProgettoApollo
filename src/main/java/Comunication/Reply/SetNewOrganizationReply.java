package Comunication.Reply;

import org.json.*;
import java.util.List;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class SetNewOrganizationReply extends AuthenticatedUpdateReply
{
    public SetNewOrganizationReply
    (
        Boolean loginSuccessful,
        Boolean updateSuccessful
    ) {
        super(loginSuccessful, updateSuccessful);
    }
}