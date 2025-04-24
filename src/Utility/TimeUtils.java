package Utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

/**
 * Utility class for date formatting and parsing.
 * Provides flexible input parsing and consistent output formatting for LocalDate.
 * This class cannot be instantiated.
 */
public final class TimeUtils {

    /**
     * Formatter to convert a LocalDate into a string of the format dd/MM/yyyy.
     * Used for consistent output formatting.
     */
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Flexible formatter that accepts various formats of day/month/year (e.g., d/M/yyyy, dd/MM/yyyy).
     * Attempts multiple parsing patterns in order until one succeeds.
     */
    private static final DateTimeFormatter FLEXIBLE_DMY_FORMATTER =
            new DateTimeFormatterBuilder()
                    // Try patterns in this order:
                    .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("dd/M/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("d/MM/yyyy"))
                    .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .toFormatter();

    /**
     * Parses a date string in flexible day/month/year format into a LocalDate.
     *
     * @param dateString the date string to parse
     * @return the parsed LocalDate, or null if the input is invalid
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
     * Converts a LocalDate into a string formatted as dd/MM/yyyy.
     *
     * @param date the LocalDate to format
     * @return a formatted date string, or an empty string if the input is null
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
