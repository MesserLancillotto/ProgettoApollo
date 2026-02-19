package Server.Engine.Helper;
import java.time.*;
import java.util.*;

public class DateIntervalCalculator {
    
    /**
     * Calcola il timestamp UNIX degli estremi dell'intervallo di giorni del 
     * mese i + monthOffset, dal giorno startDay alle 0:00 fino ad endDay 
     * alle 23:59
     * 
     * @param monthStartOffset
     * @param startDay
     * @param monthEndOffset
     * @param endDay
     *
     * @return Lista di coppie [startTimestamp, endTimestamp] in secondi UNIX
     */

    public static ArrayList<Long> calculateDateInterval(
        int monthStartOffset, 
        int startDay,
        int monthEndOffset,
        int endDay
    ) {
        // Ottieni la data corrente
        LocalDate baseDate = LocalDate.now();
        
        // Calcola il mese di inizio e il mese di fine
        LocalDate targetStartMonth = baseDate.plusMonths(monthStartOffset);
        LocalDate startDate = targetStartMonth.withDayOfMonth(startDay);
        LocalDate targetEndMonth = baseDate.plusMonths(monthEndOffset);
        LocalDate endDate = targetEndMonth.withDayOfMonth(endDay);

        // Crea i ZonedDateTime per l'inizio e la fine
        ZonedDateTime startDateTime = startDate.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime endDateTime = endDate.atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault());
        
        // Ottieni i timestamp in secondi UNIX
        long startTimestamp = startDateTime.toEpochSecond();
        long endTimestamp = endDateTime.toEpochSecond();    
        
        ArrayList<Long> list = new ArrayList<>();
        list.add(startTimestamp);
        list.add(endTimestamp);
        return list;
    }
    
    /**
     * Restituisce l'intervallo dal giorno 16 del mese i al giorno 15 del mese i+1
     * @param year l'anno
     * @param month il mese di partenza (1-12)
     * @return una stringa con l'intervallo formattato
     */
    public static String getMidMonthInterval(int year, int month) {
        // Data inizio: 16 del mese corrente
        LocalDate startDate = LocalDate.of(year, month, 16);
        
        // Calcola il mese successivo (gestisce il cambio d'anno)
        YearMonth nextMonth = YearMonth.from(startDate).plusMonths(1);
        
        // Data fine: 15 del mese successivo
        LocalDate endDate = nextMonth.atDay(15);
        
        return startDate.toString() + " - " + endDate.toString();
    }

    public static Boolean validInterval(Integer startDate, Integer endDate)
    {
        return true;
    }
}
