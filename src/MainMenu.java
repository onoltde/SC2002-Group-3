import java.util.*;

public class MainMenu {

    protected static Scanner sc;

    public MainMenu(Scanner scanner){
        sc = scanner;
    }


    public static void displayWelcome() {
        ApplicantController applicantController = new ApplicantController(sc);
        HdbOfficerController officerController = new HdbOfficerController(sc);
        HdbManagerController managerController = new HdbManagerController(sc);

        while (true) {
            // Menu Options
            System.out.println("-----------------------------------------------------------");
            System.out.println("| ██████╗ ████████╗ ██████╗         ███╗   ███╗ ███████╗  |");
            System.out.println("| ██╔══██╗╚══██╔══╝██╔═══██╗        ████╗ ████║ ██╔════╝  |");
            System.out.println("| ██████╔╝   ██║   ██║   ██║ █████╗ ██╔████╔██║ ███████╗  |");
            System.out.println("| ██╔══██╗   ██║   ██║   ██║ ╚════╝ ██║╚██╔╝██║ ╚════██║  |");
            System.out.println("| ██████╔╝   ██║   ╚██████╔╝        ██║ ╚═╝ ██║ ███████║  |");
            System.out.println("| ╚═════╝    ╚═╝    ╚═════╝         ╚═╝     ╚═╝ ╚══════╝  |");
            System.out.println("-----------------------------------------------------------");
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
                        applicantController.runPortal();
                    }
                    case 2 -> {
                        //Officer portal
                        officerController.runPortal();
                    }
                    case 3 -> {
                        //Manager portal
                        managerController.runPortal();
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




