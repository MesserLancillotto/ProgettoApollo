package User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DataManagerPeriod extends DataManager
{
    private int startDate;
    private int endDate;

    public DataManagerPeriod ()
    {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		String formattedData = data.format(formatter);
        initialize_month_association(Integer.parseInt(formattedData.substring(4)));
        
        boolean periodIsUnvalid = true;
        startDate = acquireDate("Inserisci la data di inizio (formato: DD/MM/YYYY)", "Inserisci l'ora di inizio (formato: HH:MM)");

        while (periodIsUnvalid)
        {
            endDate = acquireDate("Inserisci la data di fine (formato: DD/MM/YYYY)", "Inserisci l'ora di fine (formato: HH:MM)");
            if (endDate > startDate)    //controllo che la data sia dopo quella di inizio
                periodIsUnvalid = false;    
            else
                System.out.println ("Errore la data di fine che hai inserito è prima della data di inizio dell'evento!");
        }
        
    }

    public int getStartDate ()
    {
        return startDate;
    }

    public int getEndDate ()
    {
        return endDate;
    }

    public int acquireDate (String thingToSayToUser, String thingToSayToUserAboutHour)
    {
        String dateString;
        String hourString;
        do
        {
            dateString = UserTui.getString(thingToSayToUser);
        }while (!checkDateFormat(dateString));  // controllo che sia nel formato DD/MM/YYYY
        int dayReference = Integer.parseInt(dateString.substring(0, 2));
        int monthReference = Integer.parseInt(dateString.substring(3, 5));
        int yearReference = Integer.parseInt(dateString.substring(6, 10));
        do
        {
            hourString = UserTui.getString(thingToSayToUserAboutHour);
        }while (!checkSpecificHourFormat(hourString));  // controllo che sia nel formato HH:MM
        int tmpHours = Integer.parseInt(hourString.substring(0, 2));
        int tmpMinutes = Integer.parseInt(hourString.substring(3, 5));
        return getUnixDate(dayReference, monthReference, yearReference, tmpHours, tmpMinutes);
    }

    public static int acquireDate(String thingToSayToUser)
    {
        String dateString;
        do
        {
            dateString = UserTui.getString(thingToSayToUser);
        }while (!checkDateFormat(dateString));  // controllo che sia nel formato DD/MM/YYYY
        int dayReference = Integer.parseInt(dateString.substring(0, 2));
        int monthReference = Integer.parseInt(dateString.substring(3, 5));
        int yearReference = Integer.parseInt(dateString.substring(6, 10));
        return getUnixDate(dayReference, monthReference, yearReference, 0, 0);
    }    
    
    public static boolean checkDateFormat (String dateString)
    {
        if (dateString == null || dateString.length() != 10) 
        {
            return false;
        }
    
         // Verifica il formato base: DD/MM/YYYY
        if (dateString.charAt(2) != '/' || dateString.charAt(5) != '/') 
        {
            return false;
        }
    
        try 
        {
            // Estrae giorno, mese e anno
            int day = Integer.parseInt(dateString.substring(0, 2));
            int month = Integer.parseInt(dateString.substring(3, 5));
            int year = Integer.parseInt(dateString.substring(6, 10));
            
            // Verifica i range base
            if (year < 2025 || year > 2030) 
            {
                System.out.println ("\nErrore l'anno inserito non è valido");
                return false;
            }
            
            int checkDay = daysInAMonth.get(month);
            if (day > checkDay)
            {
                System.out.println ("\nErrore il giorno inserito non è valido");  
                return false;   //controllo che il giorno esista
            }
            return true;
        } 
         catch (NumberFormatException e) 
        {
            return false;
        }
        catch (Exception e)
        {
            //controllo che il mese esista
            System.out.println ("\nErrore il mese inserito non è valido");
            return false;
        }
    }

    public static int getUnixDate(int giorno, int mese, int anno, int ora, int minuto) 
    {
        LocalDateTime dateTime = LocalDateTime.of(anno, mese, giorno, ora, minuto, 0);
        return (int)dateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
