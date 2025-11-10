package User;

public enum StateOfVisit 
{
    PROPOSTA, COMPLETA, CONFERMATA, CANCELLATA, EFFETTUATA;

    public static StateOfVisit fromString(String value) 
    {
        try 
        {
            return StateOfVisit.valueOf(value.toUpperCase());
        } 
        catch (IllegalArgumentException e) 
        {
            throw new IllegalArgumentException("Stato non valido: " + value);
        }
    }
}
