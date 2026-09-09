package User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtils {
    private DateUtils() {}

    /**
     * Calcola i limiti temporali (inizio giornata 00:01:00 o fine giornata 23:59:59)
     * a partire da un timestamp epoch espresso in secondi (o millisecondi).
     * Restituisce il timestamp in secondi coerente con il formato epoch second del sistema.
     */
    public static long getDayBoundaries(long unixTime, boolean isStartDay) {
        ZoneId zone = ZoneId.systemDefault();

        // Se il timestamp fornito è in millisecondi (valore > 100 miliardi), lo convertiamo in secondi
        long epochSecond = (unixTime > 100_000_000_000L) ? (unixTime / 1000) : unixTime;

        LocalDate date = Instant.ofEpochSecond(epochSecond)
                .atZone(zone)
                .toLocalDate();

        ZonedDateTime startingDate = date.atTime(0, 1, 0).atZone(zone);
        ZonedDateTime endingDate = date.atTime(23, 59, 59).atZone(zone);

        return isStartDay ? startingDate.toEpochSecond() : endingDate.toEpochSecond();
    }
}
