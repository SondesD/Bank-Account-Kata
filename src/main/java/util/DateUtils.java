package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime getDate() {
        return LocalDateTime.now();
    }

    public static String getFormattedDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }
}
