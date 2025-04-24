package Utility;

import java.util.*;

//Use this utility class instead of Scanner class to keep using the same scanner object

/**
 * Utility class for handling user input consistently across the application.
 * Centralizes reading and formatting of input using a shared Scanner instance.
 * This class is final and cannot be instantiated.
 */
public final class InputUtils {

    /**
     * Shared Scanner instance for reading user input from standard input.
     * Avoids creating multiple Scanner objects on System.in.
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Reads an integer from user input.
     * Keeps prompting until a valid integer is entered.
     *
     * @return the integer input from the user
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
     * Reads a line of text input from the user.
     * Trims any leading and trailing whitespace.
     *
     * @return the trimmed input line
     */
    public static String nextLine() {
        return SCANNER.nextLine().trim();
    }

    /**
     * Prints a large divider line to visually separate sections in the console output.
     */
    public static void printBigDivider(){
        System.out.println("===================================================================");
        System.out.println();
    }

    /**
     * Prints a smaller divider line for use between content sections.
     */
    public static void printSmallDivider(){
        System.out.println("------------------------------------------------------------------");
        System.out.println();
    }

}
