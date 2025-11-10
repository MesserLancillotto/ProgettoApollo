package RequestReply.Request;

import RequestReply.UserRoleTitle.*;

public class SetNewUserRequest implements RequestType
{
    private String userName;
    private String newPassword;
    private String cityOfResidence;
    private Integer birthYear;

    public SetNewUserRequest
    (
        String userName,
        String newPassword,
        String cityOfResidence,
        Integer birthYear
    ) {
        this.userName = userName;
        this.newPassword = newPassword;
        this.cityOfResidence = cityOfResidence;
        this.birthYear = birthYear;
    }

    public String toJSONString()
    {
        String template = """
        \t"userName": "%s",
        \t"newPassword":"%s",
        \t"cityOfResidence": "%s", 
        \t"birthYear": "%s"
        """;
        return String.format(
            template, 
            userName,
            newPassword,
            cityOfResidence,
            birthYear
        );
    }
}