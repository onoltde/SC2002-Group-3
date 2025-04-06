import java.util.*;

public class UserUI {

    protected static Scanner sc;

    public UserUI(Scanner scanner){
        sc = scanner;
    }


    public static void displayWelcome() {
        System.out.println("______  _______  ______");
        System.out.println("(____  \\(_______)/ _____)");
        System.out.println("____)  )_  _  _( (____  ");
        System.out.println("|  __  (| ||_|| |\\____ \\ ");
        System.out.println("| |__)  ) |   | |_____) )");
        System.out.println("|______/|_|   |_(______/ ");
        System.out.println("\nWelcome to the BTO Management System!\n");

        while (true) {
            // Menu Options
            System.out.println("Please choose an option:");
            System.out.println("1.Applicant Portal");
            System.out.println("2.Officer Portal");  // Added missing option 2
            System.out.println("3.Manager Portal");
            System.out.println("4.Exit");
            System.out.print("Enter your choice (1-4): ");

            try {
                int choice = Integer.parseInt(sc.nextLine());  // Fixed input reading

                switch(choice) {
                    case 1 -> {
                        //Applicant portal
                        new ApplicantController(sc);
                        ApplicantController.runPortal();
                        return;
                    }
                    case 2 -> {
                        System.out.println("\nOfficer Portal Selected");
                        // Call officer-related methods here
                    }
                    case 3 -> {
                        System.out.println("\nManager Portal Selected");
                        // Call manager-related methods here
                    }
                    case 4 -> {
                        System.out.println("Exiting program. Goodbye!");
                        return;  // Exit the method
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-4.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.\n");
            }
        }
    }

    public static void exitMessage(){
        printDivider();
        System.out.println("Thank you for using BTO portal. Goodbye!");
    }

    public static void printDivider(){
        System.out.println("=========================================================================");
    }

}//end of class




