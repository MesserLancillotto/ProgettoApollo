package User;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class DataManagerDisponibility extends DataManager
{
    private static final int MAX_CONSECUTIVE_CLOSING_DAYS = 8;
    private static final String ERROR_TOO_MANY_MISTAKES = "\nErrore durante l'acquisizione !\n";
    private static final String ERROR_DATE_ALREADY_GIVEN = "\nHai già inserito questa data scegline un'altra!\n";
    private static final String ERROR_DATE_ALREADY_GIVEN_WHICH_ONES = "Queste sono le date che hai già inserito nel mese di ";
    private static final String ERROR_DATE_INVALID_DATE = "\nData inserita non valida!\n";
    private Set <Integer> enteredDates = new TreeSet<>();

    private int referenceYear;
    private int referenceMonth;
    private int endDayOfClosure;

    public DataManagerDisponibility (int reference)
    {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		String formattedData = data.format(formatter);

        this.day = Integer.parseInt(formattedData.substring(0, 2));
        this.month = Integer.parseInt(formattedData.substring(2, 4));
        this.year = Integer.parseInt(formattedData.substring(4));
        this.referenceYear = this.year;

        if (day < 16)
            referenceMonth = getIncreasedMonth(reference-1, month);
        else
            referenceMonth = getIncreasedMonth(reference, month);
        initialize_month_association(referenceYear);
    }

    public int getReferenceDay (String thingToSayForDay, String thingToSayForDuration)
    {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(thingToSayForDay);
        userMessage.append(" nel mese di ");
        userMessage.append(monthNumberToName.get(referenceMonth));
        userMessage.append(" (formato: DD)");
        
        int howManyDaysClosed;
        int unaviableDay;

        unaviableDay = UserTui.getInteger(userMessage.toString(), "Hai inserito il giorno ", 0, daysInAMonth.get(referenceMonth)+1);
        // ottengo il giorno come numero
        if (unaviableDay == -1)
        {
            System.out.println (ERROR_TOO_MANY_MISTAKES);
            return -1;
        }
        else if (enteredDates.contains(unaviableDay))
        {
            System.out.println (ERROR_DATE_ALREADY_GIVEN);
            UserTui.stamp_integer_list(ERROR_DATE_ALREADY_GIVEN_WHICH_ONES+monthNumberToName.get(referenceMonth), enteredDates);
            return -1;
        }
        else if (unaviableDay > daysInAMonth.get(referenceMonth))
        {
            System.out.println (ERROR_DATE_INVALID_DATE);
            return -1;
        }
        else if (unaviableDay == daysInAMonth.get(referenceMonth))
        {
            enteredDates.add(unaviableDay);
            endDayOfClosure = setToEndOfDay(getUnixDate(unaviableDay)); 
        }
        else
        {
            int daysToTheEndOfMonth = daysInAMonth.get(referenceMonth) - unaviableDay;
            howManyDaysClosed = UserTui.getInteger(thingToSayForDuration, 0,Math.min(MAX_CONSECUTIVE_CLOSING_DAYS, daysToTheEndOfMonth+1));    
            if (howManyDaysClosed == 1)
            {
                enteredDates.add(unaviableDay);
                endDayOfClosure = setToEndOfDay(getUnixDate(unaviableDay)); 
            }
            else
            {
                for (int i = 0; i < howManyDaysClosed-1; i++)
                {
                    enteredDates.add(unaviableDay + i);
                }
                endDayOfClosure = setToEndOfDay(getUnixDate(unaviableDay+howManyDaysClosed));
            } 
        }
        return unaviableDay;
    }

    //CONTROLLA, metodo per prendere un giorno solo
    public int getReferenceDay (String thingToSayForDay)
    {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(thingToSayForDay);
        userMessage.append(" nel mese di ");
        userMessage.append(monthNumberToName.get(referenceMonth));
        userMessage.append(" (formato: DD)");
        
        int unaviableDay;
        unaviableDay = UserTui.getInteger(userMessage.toString(), "Hai inserito il giorno ", 0, daysInAMonth.get(referenceMonth)+1);
        //il metodo chiede conferma sull'input e poi controlla il range di validità
        if (unaviableDay == -1)
        {
            System.out.println (ERROR_TOO_MANY_MISTAKES);
            return -1;
        }
        else if (enteredDates.contains(unaviableDay))
        {
            System.out.println (ERROR_DATE_ALREADY_GIVEN);
            return -1;
        }
        else
        {
            enteredDates.add(unaviableDay);
            return unaviableDay;
        }
    }

    private int getIncreasedMonth (int increase, int tmpMonth)
    {
        for (int i = 0; i < increase; i++)
        {
            tmpMonth += 1;
            if (tmpMonth > 12)
            {
                tmpMonth = 1;
                referenceYear +=1;
            }
        }

        return tmpMonth;
    }

    public int getUnixDate (int referenceDay)
    {
        LocalDate date = LocalDate.of(referenceYear, referenceMonth, referenceDay);
        return (int)date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    public int getReferenceMonth ()
    {
        return referenceMonth;
    }

    public String getMonthName (int month)
    {
        return monthNumberToName.get(month);
    }

    public int getYear ()
    {
        return year;
    }

    public int getEndDayOfClosure ()
    {
        return endDayOfClosure;
    }
}
