package User;

import java.util.*;
import java.time.*;
import java.time.format.*;

public class DataManager 
{
    public int day;
    public int month;
    public int year;
    
    public HashMap <Integer, String> monthNumberToName = new HashMap <>();
    public static HashMap <Integer, Integer> daysInAMonth = new HashMap<>();
    public static Set <DayOfWeek> daysAlreadyInserted = new HashSet<>();
    

    public void initialize_month_association (int toCheckLeapYear)
    {
        monthNumberToName.put(1, "Gennaio");
        monthNumberToName.put(2, "Febbraio");
        monthNumberToName.put(3, "Marzo");
        monthNumberToName.put(4, "Aprile");
        monthNumberToName.put(5, "Maggio");
        monthNumberToName.put(6, "Giugno");
        monthNumberToName.put(7, "Luglio");
        monthNumberToName.put(8, "Agosto");
        monthNumberToName.put(9, "Settembre");
        monthNumberToName.put(10, "Ottobre");
        monthNumberToName.put(11, "Novembre");
        monthNumberToName.put(12, "Dicembre");

        daysInAMonth.put(1, 31);
        if ((toCheckLeapYear % 4 == 0) && (toCheckLeapYear % 100 != 0 || toCheckLeapYear % 400 == 0))
            daysInAMonth.put(2, 29);
        else
            daysInAMonth.put(2, 28);
        daysInAMonth.put(3, 31);
        daysInAMonth.put(4, 30);
        daysInAMonth.put(5, 31);
        daysInAMonth.put(6, 30);
        daysInAMonth.put(7, 31);
        daysInAMonth.put(8, 31);
        daysInAMonth.put(9, 30);
        daysInAMonth.put(10, 31);
        daysInAMonth.put(11, 30);
        daysInAMonth.put(12, 31);

    }

    private static void initialize_month_association_from_user (int toCheckLeapYear)
    {
         daysInAMonth.put(1, 31);
        if ((toCheckLeapYear % 4 == 0) && (toCheckLeapYear % 100 != 0 || toCheckLeapYear % 400 == 0))
            daysInAMonth.put(2, 29);
        else
            daysInAMonth.put(2, 28);
        daysInAMonth.put(3, 31);
        daysInAMonth.put(4, 30);
        daysInAMonth.put(5, 31);
        daysInAMonth.put(6, 30);
        daysInAMonth.put(7, 31);
        daysInAMonth.put(8, 31);
        daysInAMonth.put(9, 30);
        daysInAMonth.put(10, 31);
        daysInAMonth.put(11, 30);
        daysInAMonth.put(12, 31);

    }

    public static int setToEndOfDay(long unixTimestamp) //OK
    {
        Instant instant = Instant.ofEpochSecond(unixTimestamp);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        
        ZonedDateTime endOfDay = zonedDateTime.with(LocalTime.MAX);
        int endOfDayDate = (int)endOfDay.toEpochSecond();
        
        return endOfDayDate;
    }

    public static String fromUnixToNormal(int value) //OK
    {
        try 
        {
            // Verifica che il timestamp non sia negativo
            if (value < 0) 
            {
                return "Data non valida";
            }
            
            // Converti il timestamp Unix in LocalDateTime
            Instant instant = Instant.ofEpochSecond(value);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            
            // Definisci il formato italiano
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'alle' HH:mm");
            
            // Formatta la data
            return dateTime.format(formatter);
            
        } 
        catch (Exception e) 
        {
            return "Data non valida";
        }
    }

    public static int acquireDateFromUser(String thingToSayToUser)
    {
        String dateString;
        do
        {
            dateString = UserTui.getString(thingToSayToUser);
        }while (!checkDateFormatFromUser(dateString));  // controllo che sia nel formato DD/MM/YYYY
        int dayReference = Integer.parseInt(dateString.substring(0, 2));
        int monthReference = Integer.parseInt(dateString.substring(3, 5));
        int yearReference = Integer.parseInt(dateString.substring(6, 10));
        return getUnixDate(dayReference, monthReference, yearReference, 0, 0);
    }

    public static boolean checkDateFormatFromUser (String dateString)
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
            initialize_month_association_from_user(year);
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

    public static String getDayOfWeekFromUser (String thingToSayToUser) //OK
    {
        String dayFromUser;
        boolean dayIsValid;
        boolean continueSearching = true;
        do
        {
            dayFromUser = UserTui.getString(thingToSayToUser+ " (Lunedi, Martedi, Mercoledi, Giovedi, Venerdi, Sabato, Domenica)");
            dayIsValid = DayOfWeek.isValid(dayFromUser.toLowerCase().replace("ì","i").toUpperCase());
            if (!dayIsValid)
            {
                System.out.println ("Il giorno inserito non è valido");
                System.out.println ("I giorni validi sono: Lunedi, Martedi, Mercoledi, Giovedi, Venerdi, Sabato, Domenica");
                dayFromUser = "";
                continueSearching = UserTui.getYesNoAnswer("Vuoi riprovare ");
            }
            else
            {
                DayOfWeek dayInsertedFromUser = DayOfWeek.valueOf(dayFromUser.toLowerCase().replace("ì","i").toUpperCase());
                if (daysAlreadyInserted.contains(dayInsertedFromUser))
                {
                    System.out.println ("Hai già inserito questo giorno scegline un'altro!");
                    dayFromUser = "";
                    continueSearching = UserTui.getYesNoAnswer("Vuoi riprovare ");
                }
                else
                {
                    continueSearching = false;
                    daysAlreadyInserted.add(dayInsertedFromUser);
                }
            }
        }while (continueSearching);
        if (!dayFromUser.trim().isEmpty())
        {
            StringBuilder returnValue = new StringBuilder();
            returnValue.append(dayFromUser.substring(0,1).toUpperCase());
            returnValue.append(dayFromUser.substring(1, 3));
            return returnValue.toString();   // ritorno come Lun,Mar,Mer ecc...
        }
        else
            return "";
    }

    public static int getAnHourFromUser (String thingToSayToUser)
    {
        String hourFromUser;
        boolean hourIsValid;
        do
        {
            hourFromUser = UserTui.getString(thingToSayToUser);
            hourIsValid = checkSpecificHourFormat(hourFromUser);
            if (!hourIsValid)
                System.out.println ("\nOrario inserito non valido!");
        }while (!hourIsValid);
        int tmpHours = Integer.parseInt(hourFromUser.substring(0, 2));
        int tmpMinutes = Integer.parseInt(hourFromUser.substring(3, 5));
        int completeHour = tmpHours * 100+tmpMinutes;
        return completeHour;
    }
    
    public static boolean checkSpecificHourFormat (String hour)
    {
        if (hour == null || hour.length() != 5) 
        {
            return false;
        }
        // Verifica che il separatore sia ":"
        if (hour.charAt(2) != ':') 
        {
            return false;
        }
        
        try 
        {
            // Estrae ore e minuti
            int tmpHours = Integer.parseInt(hour.substring(0, 2));
            int tmpMinutes = Integer.parseInt(hour.substring(3, 5));
            
            // Verifica i range validi
            if (tmpHours >= 0 && tmpHours <= 23 && tmpMinutes >= 0 && tmpMinutes <= 59)
                return true;
            System.out.println ("\nOrario inserito non valido!");
            return false;
            
        } 
        catch (Exception e) 
        {
            return false;
        }
    }

    public static boolean isSameDay(long firstUnixDate, long secondUnixDate) 
    {
        // Controllo base per valori non validi
        if (firstUnixDate < 0 || secondUnixDate < 0) 
        {
            //System.err.println("Timestamp non validi: " + firstUnixDate + ", " + secondUnixDate);
            return false;
        }
        try 
        {
            LocalDate firstDate = Instant.ofEpochSecond(firstUnixDate)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
                    
            LocalDate secondDate = Instant.ofEpochSecond(secondUnixDate)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            
            boolean sameDay = firstDate.equals(secondDate);
            
            /*  Log opzionale per debug
            if (sameDay) 
            {
                System.out.println("Le date appartengono allo stesso giorno: " + firstDate);
            }*/
            return sameDay;
        } 
        catch (DateTimeException e) 
        {
            //System.err.println("Errore di conversione date: " + e.getMessage());
            return false;
        } 
        catch (Exception e) 
        {
            //System.err.println("Errore imprevisto: " + e.getMessage());
            return false;
        }
    }

}
