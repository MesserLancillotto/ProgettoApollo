package User;

public enum DayOfWeek 
{
    LUNEDI, MARTEDI, MERCOLEDI, GIOVEDI, VENERDI, SABATO, DOMENICA;

    public static boolean isValid(String value) 
    {
        if (value == null) 
            return false;
    
        try 
        {
            DayOfWeek.valueOf(value.toUpperCase());
            return true;
        } 
        catch (IllegalArgumentException e) 
        {
            return false;
        }
    }
}