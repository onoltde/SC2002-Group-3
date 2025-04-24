package Utility;
import java.util.*;

/**
 * Utility class for reading input from the console and printing dividers.
 * This class uses a single static {@link Scanner} object to read user input,
 * avoiding the need to create multiple instances of {@link Scanner}.
 */
public final class InputUtils {
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Reads an integer from the console. If the input is not a valid integer,
     * it will prompt the user again until a valid integer is entered.
     *
     * @return the integer entered by the user
     */
    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }
    }

    /**
     * Reads a line of text from the console.
     *
     * @return the line entered by the user
     */
    public static String nextLine() {
        return SCANNER.nextLine().trim();
    }

    /**
     * Prints a large divider line to the console.
     * Typically used to separate sections in the console output.
     */
    public static void printBigDivider() {
        System.out.println("===================================================================");
        System.out.println();
    }

    /**
     * Prints a small divider line to the console.
     * Typically used for smaller sections or visual breaks in the output.
     */
    public static void printSmallDivider() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
    }

}
