package Utility;
import java.util.*;

//Use this utility class instead of Scanner class to keep using the same scanner object

public final class InputUtils {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }
    }

    public static String nextLine() {
        return SCANNER.nextLine().trim();

    }

}