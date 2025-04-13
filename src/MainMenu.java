import java.util.*;
import Utility.*;
import Applicant.*;
import HdbOfficer.*;
import HdbManager.*;

public class MainMenu {

    public MainMenu(){
    }


    public static void displayWelcome() {

        while (true) {
            // Menu Options
            System.out.println();
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


            int choice = InputUtils.readInt();  // Fixed input reading

            switch(choice) {
                case 1 -> {
                    //Applicant portal
                    ApplicantController applicantController = new ApplicantController();
                    applicantController.runPortal();
                }
                case 2 -> {
                    //Officer portal
                    HdbOfficerController officerController = new HdbOfficerController();
                    officerController.runPortal();
                }
                case 3 -> {
                    //Manager portal
                    HdbManagerController managerController = new HdbManagerController();
                    managerController.runPortal();
                }
                case 4 -> {
                    System.out.println("Exiting program. Goodbye!");
                    return;  // Exit the method
                }
                default -> System.out.println("Invalid choice! Please enter 1-4.\n");
            }


        }
    }



}//end of class




