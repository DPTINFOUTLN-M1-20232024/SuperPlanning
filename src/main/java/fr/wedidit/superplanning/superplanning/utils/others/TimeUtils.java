package fr.wedidit.superplanning.superplanning.utils.others;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class TimeUtils {

    private TimeUtils() {}

    public static Timestamp[] getCurrentWeekDelimitation() {
        LocalDateTime now = LocalDateTime.now();

        // First day of the current week
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .withHour(0)
                .withMinute(0)
                .withSecond(1);

        // Last day of the current week
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        Timestamp startTimestamp = java.sql.Timestamp.valueOf(startOfWeek);
        Timestamp endTimestamp = java.sql.Timestamp.valueOf(endOfWeek);

        return new Timestamp[] {startTimestamp, endTimestamp};
    }

    public static String getDateInfo(Timestamp timestamp, String format) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }
}
