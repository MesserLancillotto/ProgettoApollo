package RequestReply.UserRoleTitle;

import java.util.*;

public final class UserRoleTitleStringConverter
{
    public static final UserRoleTitle stringToRole(String type)
    {
        return UserRoleTitle.valueOf(type);
    }

    public static final String roleToString(UserRoleTitle type)
    {
        return type.name();
    }
}


