import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public final class TimeUtils {

    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter FLEXIBLE_DMY_FORMATTER =
            new DateTimeFormatterBuilder()
                    // Try patterns in this order:
                    .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("dd/M/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("d/MM/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .toFormatter();

    public static LocalDate stringToDate(String dateString) {
        try {
            // Attempts each of the patterns above until one succeeds
            return LocalDate.parse(dateString, FLEXIBLE_DMY_FORMATTER);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;  // bad date
        }
    }

    public static String dateToString(LocalDate date) {
        // Return an empty string if date is null to avoid NullPointerExceptions
        if (date == null) {
            return "";
        }
        // Always formats as dd/MM/yyyy
        return date.format(OUTPUT_FORMATTER);
    }


}
