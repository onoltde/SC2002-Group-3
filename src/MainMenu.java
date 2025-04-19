import java.util.*;

import Application.Residential.ResidentialApplicationController;
import Application.Team.TeamApplicationController;
import Enquiry.EnquiryController;
import Project.ProjectController;
import Project.ProjectControllerInterface;
import Report.ReportController;
import Utility.*;
import Applicant.*;
import HdbOfficer.*;
import HdbManager.*;

public class MainMenu {
    private ProjectControllerInterface projectController;
    private EnquiryController enquiryController;
    private ReportController reportController;

    private ResidentialApplicationController resAppController;
    private TeamApplicationController teamAppController;

    private static ApplicantController applicantController;
    private static HdbOfficerController officerController;
    private static HdbManagerController managerController;


    public MainMenu(){
        projectController = new ProjectController();
        enquiryController = new EnquiryController(projectController);
        reportController = new ReportController();

        resAppController = new ResidentialApplicationController(projectController);
        teamAppController = new TeamApplicationController(projectController);

        applicantController = new ApplicantController(resAppController,projectController, enquiryController);

        officerController = new HdbOfficerController(projectController,resAppController,teamAppController, enquiryController);
        managerController = new HdbManagerController(   projectController, officerController, applicantController,
                                                        resAppController,teamAppController,
                                                        enquiryController, reportController);
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


        }
    }



}//end of class




