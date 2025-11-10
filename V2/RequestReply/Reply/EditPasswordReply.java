package RequestReply.Reply;

import org.json.*;

public class EditPasswordReply implements ReplyType 
{
    private boolean accessSuccesful;
    private boolean passwordChangeSuccessful;

    public EditPasswordReply
    (
        boolean accessSuccesful,
        boolean passwordChangeSuccessful
    ) {
        this.accessSuccesful = accessSuccesful;
        this.passwordChangeSuccessful = passwordChangeSuccessful;
    }

    public String toJSONString()
    {
        String template = """
        {
        \t"loginSuccessful":"%s"
        \t"passwordChangeSuccessful":"%s"
        }
        """;
        return String.format(
            template, 
            accessSuccesful, 
            passwordChangeSuccessful);
    }
}