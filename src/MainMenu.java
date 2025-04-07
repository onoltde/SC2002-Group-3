import java.util.*;

public class MainMenu {

    protected static Scanner sc;

    public MainMenu(Scanner scanner){
        sc = scanner;
    }


    public static void displayWelcome() {


        while (true) {
            // Menu Options
            System.out.println("______  _______  ______");
            System.out.println("(____  \\(_______)/ _____)");
            System.out.println("____)  )_  _  _( (____  ");
            System.out.println("|  __  (| ||_|| |\\____ \\ ");
            System.out.println("| |__)  ) |   | |_____) )");
            System.out.println("|______/|_|   |_(______/ ");
            System.out.println("\nWelcome to the BTO Management System!\n");
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
                        ApplicantController controller = new ApplicantController(sc);
                        controller.runPortal();
                    }
                    case 2 -> {
                        //Officer portal
                        HdbOfficerController controller = new HdbOfficerController(sc);
                        controller.runPortal();
                    }
                    case 3 -> {
                        //Manager portal
                        HdbManagerController controller = new HdbManagerController(sc);
                        controller.runPortal();
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



}//end of class




