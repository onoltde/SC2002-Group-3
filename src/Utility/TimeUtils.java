package Utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

/**
 * Utility class for handling time-related operations such as converting
 * strings to {@link LocalDate} objects and vice versa.
 * Provides flexible date parsing with various formats and ensures consistent date formatting.
 */
public final class TimeUtils {

    // Formatter for consistent output format "dd/MM/yyyy"
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Flexible date formatter that supports different D/M/YYYY formats
    private static final DateTimeFormatter FLEXIBLE_DMY_FORMATTER =
            new DateTimeFormatterBuilder()
                    // Try patterns in this order:
                    .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("dd/M/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("d/MM/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .toFormatter();

    /**
     * Converts a date string to a {@link LocalDate} object.
     * Tries to parse the date using various D/M/YYYY formats.
     *
     * @param dateString the date string to convert
     * @return a {@link LocalDate} object if the string is valid, or null if the string cannot be parsed
     */
    public static LocalDate stringToDate(String dateString) {
        try {
            // Attempts each of the patterns above until one succeeds
            return LocalDate.parse(dateString, FLEXIBLE_DMY_FORMATTER);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;  // bad date
        }
    }

    /**
     * Converts a {@link LocalDate} object to a string in the format "dd/MM/yyyy".
     * Returns an empty string if the date is null to avoid NullPointerExceptions.
     *
     * @param date the {@link LocalDate} to convert
     * @return the formatted date string or an empty string if the date is null
     */
    public static String dateToString(LocalDate date) {
        // Return an empty string if date is null to avoid NullPointerExceptions
        if (date == null) {
            return "";
        }
        // Always formats as dd/MM/yyyy
        return date.format(OUTPUT_FORMATTER);
    }
}
