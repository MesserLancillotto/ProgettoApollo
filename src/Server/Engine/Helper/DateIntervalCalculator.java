package Server.Engine.Helper;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateIntervalCalculator {
    
    /**
     * Calcola l'intervallo di giorni dal giorno i al giorno j per n mesi successivi
     * a partire dalla data corrente (timestamp UNIX)
     * 
     * @param currentTimestamp Timestamp UNIX corrente (in secondi)
     * @param monthOffset Numero di mesi da aggiungere (0 = mese corrente)
     * @param startDay Giorno di inizio dell'intervallo (1-31)
     * @param endDay Giorno di fine dell'intervallo (1-31, deve essere >= startDay)
     * @return Lista di coppie [startTimestamp, endTimestamp] in secondi UNIX
     */

    public static List<long[]> calculateDayIntervalsForFutureMonths(
            long currentTimestamp, 
            int monthOffset, 
            int startDay, 
            int endDay) {
        
        List<long[]> intervals = new ArrayList<>();
        
        // Converti timestamp a LocalDate (ignorando l'ora)
        LocalDate baseDate = Instant.ofEpochSecond(currentTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        
        // Aggiungi l'offset dei mesi
        LocalDate targetMonth = baseDate.plusMonths(monthOffset);
        
        // Calcola l'intervallo per il mese target
        long[] interval = calculateMonthDayInterval(targetMonth, startDay, endDay);
        if (interval != null) {
            intervals.add(interval);
        }
        
        return intervals;
    }
    
    /**
     * Calcola l'intervallo di giorni dal giorno i al giorno j per i prossimi n mesi
     * a partire dalla data corrente
     * 
     * @param currentTimestamp Timestamp UNIX corrente (in secondi)
     * @param numberOfMonths Numero di mesi futuri da considerare
     * @param startDay Giorno di inizio dell'intervallo (1-31)
     * @param endDay Giorno di fine dell'intervallo (1-31)
     * @return Lista di coppie [startTimestamp, endTimestamp] per ogni mese
     */
    
    public static List<long[]> calculateDayIntervalsForNextNMonths(
            long currentTimestamp, 
            int numberOfMonths, 
            int startDay, 
            int endDay) {
        
        List<long[]> intervals = new ArrayList<>();
        
        // Converti timestamp a LocalDate
        LocalDate baseDate = Instant.ofEpochSecond(currentTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        
        // Calcola per ogni mese da 1 a numberOfMonths
        for (int monthOffset = 1; monthOffset <= numberOfMonths; monthOffset++) {
            LocalDate targetMonth = baseDate.plusMonths(monthOffset);
            long[] interval = calculateMonthDayInterval(targetMonth, startDay, endDay);
            if (interval != null) {
                intervals.add(interval);
            }
        }
        
        return intervals;
    }
    
    /**
     * Calcola intervalli per un periodo continuo di mesi
     * 
     * @param currentTimestamp Timestamp UNIX corrente
     * @param startMonthOffset Mese di inizio (0 = corrente)
     * @param endMonthOffset Mese di fine (>= startMonthOffset)
     * @param startDay Giorno di inizio (1-31)
     * @param endDay Giorno di fine (1-31)
     * @return Lista di intervalli per ogni mese
     */
    public static List<long[]> calculateIntervalsForMonthRange(
            long currentTimestamp,
            int startMonthOffset,
            int endMonthOffset,
            int startDay,
            int endDay) {
        
        List<long[]> intervals = new ArrayList<>();
        
        LocalDate baseDate = Instant.ofEpochSecond(currentTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        
        for (int monthOffset = startMonthOffset; monthOffset <= endMonthOffset; monthOffset++) {
            LocalDate targetMonth = baseDate.plusMonths(monthOffset);
            long[] interval = calculateMonthDayInterval(targetMonth, startDay, endDay);
            if (interval != null) {
                intervals.add(interval);
            }
        }
        
        return intervals;
    }
    
    /**
     * Calcola l'intervallo per un mese specifico
     */
    private static long[] calculateMonthDayInterval(LocalDate monthDate, int startDay, int endDay) {
        try {
            // Aggiusta i giorni se eccedono i giorni del mese
            YearMonth yearMonth = YearMonth.from(monthDate);
            int daysInMonth = yearMonth.lengthOfMonth();
            
            int adjustedStartDay = Math.min(startDay, daysInMonth);
            int adjustedEndDay = Math.min(endDay, daysInMonth);
            
            if (adjustedStartDay > adjustedEndDay) {
                // Scambia se start > end
                int temp = adjustedStartDay;
                adjustedStartDay = adjustedEndDay;
                adjustedEndDay = temp;
            }
            
            // Crea le date con i giorni specificati
            LocalDate startDate = monthDate.withDayOfMonth(adjustedStartDay);
            LocalDate endDate = monthDate.withDayOfMonth(adjustedEndDay);
            
            // Imposta l'ora a mezzanotte per l'inizio e 23:59:59 per la fine
            ZonedDateTime startDateTime = startDate.atStartOfDay(ZoneId.systemDefault());
            ZonedDateTime endDateTime = endDate.atTime(23, 59, 59)
                    .atZone(ZoneId.systemDefault());
            
            // Converti in timestamp UNIX (secondi)
            long startTimestamp = startDateTime.toEpochSecond();
            long endTimestamp = endDateTime.toEpochSecond();
            
            return new long[]{startTimestamp, endTimestamp};
            
        } catch (DateTimeException e) {
            System.err.println("Errore nel calcolo dell'intervallo: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Versione che ritorna timestamp in millisecondi (Java standard)
     */
    public static List<long[]> calculateDayIntervalsInMillis(
            long currentTimestampMillis,
            int numberOfMonths,
            int startDay,
            int endDay) {
        
        // Converti da millisecondi a secondi
        long currentTimestampSeconds = currentTimestampMillis / 1000;
        
        List<long[]> intervalsInSeconds = calculateDayIntervalsForNextNMonths(
                currentTimestampSeconds, numberOfMonths, startDay, endDay);
        
        // Converti di nuovo in millisecondi
        List<long[]> intervalsInMillis = new ArrayList<>();
        for (long[] interval : intervalsInSeconds) {
            intervalsInMillis.add(new long[]{interval[0] * 1000, interval[1] * 1000});
        }
        
        return intervalsInMillis;
    }
}