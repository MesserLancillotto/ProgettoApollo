package User;

import java.util.*;

public interface UserCreator 
{
    User create (String userName, String cityOfResidence, int birthYear, String password, String roleTitle, String organization, ArrayList <String> allowedVisitType);
}
