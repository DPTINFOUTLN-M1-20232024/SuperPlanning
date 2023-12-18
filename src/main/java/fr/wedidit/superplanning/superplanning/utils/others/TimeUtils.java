package fr.wedidit.superplanning.superplanning.utils.others;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class TimeUtils {

    public static final int CURRENT_YEAR = 2023;

    private TimeUtils() {}

    public static Timestamp[] getCurrentWeekDelimitation(int currentPageIndex) {
        LocalDateTime now = currentPageIndexToLocalDateTime(currentPageIndex);

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

        Timestamp startTimestamp = Timestamp.valueOf(startOfWeek);
        Timestamp endTimestamp = Timestamp.valueOf(endOfWeek);

        return new Timestamp[] {startTimestamp, endTimestamp};
    }

    public static String getDateInfo(Timestamp timestamp, String format) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static LocalDateTime currentPageIndexToLocalDateTime(int currentPageIndex) {

        int year = CURRENT_YEAR;
        int weekNumber = currentPageIndex + 34;
        currentPageIndex += 34;
        if (weekNumber >= 53) {
            weekNumber -= 52;
            year++;
        }

        LocalDate date = LocalDate.of(year, 1, 1);
        date = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        for (int i = 1; i < weekNumber; i++) {
            date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        return date.atStartOfDay();
    }

    public static int getPaginationIndexFromCurrentDate() {
        return LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()) - 1;
    }
}
