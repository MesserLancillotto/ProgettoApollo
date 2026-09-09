package User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtils {
    private DateUtils() {}

    /**
     * Calcola i limiti temporali (inizio giornata 00:01:00 o fine giornata 23:59:59)
     * a partire da un timestamp epoch espresso in millisecondi.
     */
    public static long getDayBoundaries(long unixTimeMillis, boolean isStartDay) {
        ZoneId zone = ZoneId.systemDefault();

        LocalDate date = Instant.ofEpochMilli(unixTimeMillis)
                .atZone(zone)
                .toLocalDate();

        ZonedDateTime startingDate = date.atTime(0, 1, 0).atZone(zone);
        ZonedDateTime endingDate = date.atTime(23, 59, 59).atZone(zone);

        return isStartDay ? startingDate.toInstant().toEpochMilli() : endingDate.toInstant().toEpochMilli();
    }
}
