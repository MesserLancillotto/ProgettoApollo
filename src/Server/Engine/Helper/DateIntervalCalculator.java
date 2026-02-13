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

    public static long[] calculateDateInterval(
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
        
        return new long[]{startTimestamp, endTimestamp};
    }
}
