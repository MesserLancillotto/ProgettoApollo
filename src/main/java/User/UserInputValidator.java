package User;

public final class UserInputValidator
{
    private UserInputValidator (){}

    public static String format_String (String str)
    {
        if (str != null || !str.trim().isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append (str.substring(0,1).toUpperCase());
            sb.append (str.substring(1,str.length()).toLowerCase());
            return sb.toString();
        }
        return str;
    }

    public static boolean checkYearOfBirth(String year)
    {
        try
        {
            int value = Integer.parseInt(year);
            if (value > 1930 && value < 2025)
            {
                return true;
            }
            else
                return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static boolean passwordIsSafe(String pswd)
    {
        if (pswd.length() < 8)
        {
            return false;
        }
        if (pswd == null || pswd.trim().isEmpty()) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        for (char c : pswd.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        return hasUpperCase && hasLowerCase && hasDigit;
    }
}
