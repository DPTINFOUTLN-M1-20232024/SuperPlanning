package fr.wedidit.superplanning.superplanning.utils.others;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

@Slf4j
public class TimeUtils {

    private TimeUtils() {}

    /**
     * @param text with the format HH:mm or HHhmm
     * @return an array [hours, minutes]
     * @throws NumberFormatException if the format of the text is not correct
     */
    public static int[] parseHoursMinutes(String text) throws NumberFormatException {
        if (text.length() != 5) throw new NumberFormatException();

        String[] textSplited;
        if (text.contains(":")) textSplited = text.split(":");
        else if (text.contains("h")) textSplited = text.split("h");
        else throw new NumberFormatException();

        return new int[] {Integer.parseInt(textSplited[0]), Integer.parseInt(textSplited[1])};
    }

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

    public static Timestamp[] getCurrentDayDelimitation() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDateTime startDay = LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.of(0, 0, 1));
        LocalDateTime endDay = LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.of(23, 59, 59));

        Timestamp startDayTimestamp = Timestamp.valueOf(startDay);
        Timestamp endDayTimestamp = Timestamp.valueOf(endDay);

        return new Timestamp[] {startDayTimestamp, endDayTimestamp};
    }

    public static String getDateInfo(Timestamp timestamp, String format) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static LocalDateTime currentPageIndexToLocalDateTime(int currentPageIndex) {

        int year = Year.now().getValue() - 1;
        int weekNumber = currentPageIndex + 33;
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
