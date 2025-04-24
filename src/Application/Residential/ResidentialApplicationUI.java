package Application.Residential;
import Applicant.*;
import Application.Application;
import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.Project;
import Utility.*;
import Project.Flat;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * User Interface class for handling residential applications.
 * This class provides the interface for applicants, officers, and managers to interact with
 * the residential application system, including viewing, applying, and managing applications.
 */
public class ResidentialApplicationUI {
    private ResidentialApplicationController controller;

    /**
     * Constructs a new ResidentialApplicationUI with the specified controller.
     *
     * @param controller The controller to handle application logic
     */
    public ResidentialApplicationUI(ResidentialApplicationController controller){
        this.controller = controller;
    }

    /**
     * Displays the application menu for an applicant.
     * Shows current application details and provides options to view project details,
     * withdraw application, make a booking, or exit.
     *
     * @param applicant The applicant viewing the menu
     */
    public void displayApplicationMenu(Applicant applicant){
        ResidentialApplication application = applicant.getResidentialApplication();
        String projectName = application.getProjectName();
        Flat.Type flatType = application.getFlatType();
        System.out.println("Applied to: " + projectName );
        System.out.println("Flat type: " + flatType);
        System.out.println("Application Status: " + application.getStatus());
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Please choose an option:");
            System.out.println("1. View applied project details");
            System.out.println("2. Withdraw application");
            System.out.println("3. Make a Booking");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {//view project details
                    controller.displayProjectFlatDetails(projectName,flatType);
                }
                case 2 -> {//request withdrawal
                    controller.requestWithdrawal(application);
                }
                case 3 -> {
                    controller.makeBooking(application);
                }
                case 4 -> {//exit
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }

    /**
     * Displays the application menu for an HDB officer.
     * If the officer has no current application, shows options to view and apply for projects.
     * If the officer has an application, shows options to manage their existing application.
     *
     * @param officer The HDB officer viewing the menu
     * @return The created application if a new one is made, null otherwise
     */
    public ResidentialApplication displayApplicationMenu(HdbOfficer officer) {
        if (!officer.hasResidentialApplication()) {
            System.out.println();
            System.out.println("You do not have a current application as a resident.");

            while (true) {
                System.out.println("----------------------------------");
                System.out.println("Please choose an option:");
                System.out.println("1. View current 2-Room BTO projects as resident");
                System.out.println("2. View current 3-Room BTO projects as resident");
                System.out.println("3. Exit");
                System.out.print("Enter your choice (1-3): ");

                int choice = InputUtils.readInt();

                switch (choice) {
                    case 1 -> {//view 2 room projects and possibly apply
                        return controller.displayProjectsToApply(officer,Flat.Type.TWOROOM);
                    }
                    case 2 -> {//view 3 room projects and possibly apply
                        return controller.displayProjectsToApply(officer, Flat.Type.THREEROOM);
                    }
                    case 3 -> {//exit
                        return null;
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-3.\n");

                }
            }
        } else {
            ResidentialApplication application = officer.getResidentialApplication();
            String projectName = application.getProjectName();
            Flat.Type flatType = application.getFlatType();
            System.out.println("----------------------------------");
            System.out.println();
            System.out.println("Applied to: " + projectName);
            System.out.println("Flat type: " + flatType);
            System.out.println("Application Status: " + application.getStatus());

            while (true) {
                System.out.println("----------------------------------");
                System.out.println("Please choose an option:");
                System.out.println("1. View applied project details");
                System.out.println("2. Withdraw application");
                System.out.println("3. Exit");
                System.out.print("Enter your choice (1-3): ");

                int choice = InputUtils.readInt();

                switch (choice) {
                    case 1 -> {//view project details
                        controller.displayProjectFlatDetails(projectName, flatType);
                    }
                    case 2 -> {//request withdrawal
                        controller.requestWithdrawal(application);
                    }
                    case 3 -> {//exit
                        return null;
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-3.\n");

                }
            }

        }
    }

    /**
     * Handles the withdrawal request process for an application.
     * Prompts for confirmation and updates the application status accordingly.
     *
     * @param application The application to withdraw
     * @return true if withdrawal request was successful, false otherwise
     */
    public boolean requestWithdrawal(ResidentialApplication application){
        if (application.getStatus() == Application.Status.WITHDRAWING){
            System.out.println("You have already submitted a request to withdraw this application.");
            return false;
        }
        System.out.println("Are you sure you want to request to withdraw this application?");
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("1. YES");
            System.out.println("2. NO");
            System.out.print("Enter your choice (1-2): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {//withdraw
                    System.out.println("You have submitted a request to withdraw your application.");
                    return true;
                }
                case 2-> {//exit
                    System.out.println("\'NO\' selected");
                    return false;
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }

    /**
     * Displays a list of applications for review.
     * Provides options to approve, reject, or book units for each application.
     *
     * @param applications The list of applications to display
     */
    public void viewApplications(ArrayList<ResidentialApplication> applications){
        if (applications.size() == 0){
            System.out.println("No applications to display.");
            return;
        }
        int currentIndex = 0;

        while (currentIndex < applications.size()) {
            ResidentialApplication currentApplication = applications.get(currentIndex);

            InputUtils.printSmallDivider();
            System.out.printf("Application (%d/%d):\n", currentIndex + 1, applications.size());

            // Display application details
            System.out.println("--------------------------------");
            System.out.println("Applicant: " + currentApplication.getApplicantId());
            System.out.println("Application status: " + currentApplication.getStatus());

            // Show menu options
            System.out.println("-------------------------------");
            System.out.println("\n Please choose an option:");
            System.out.println("-------------------------------");
            System.out.println("1. Approve application");
            System.out.println("2. Reject application");
            System.out.println("3. Book unit for applicant");
            System.out.println("4. Next application");
            System.out.println("5. Exit to menu");
            System.out.println("Enter your choice (1-5): ");


            int choice = InputUtils.readInt();

            switch (choice) {
                case 1: // approve application
                    if(currentApplication.approve() != true){
                        System.out.println("Error! Cannot approve this application");
                        return;
                    }else{
                        return;
                    }

                case 2: // reject applicatiom
                    if (currentApplication.reject() != true) {
                        System.out.println("Error! cannot reject this application!");
                        return;
                    }else{
                        return;
                    }
                case 3: //book unit
                    if (currentApplication.getStatus() != Application.Status.BOOKING){
                        System.out.println("Error! Cannot book a Non-Booking application!");
                        return;
                    }
                    else if (controller.bookUnit(currentApplication) == false){
                        System.out.println("Error! cannot make booking! Not enough units!");
                    }else{
                        System.out.println("Success! Booking made!");
                    }
                    return;
                case 4:
                    currentIndex++;
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("No more projects to display.");
    }

    /**
     * Displays the application menu for an HDB manager.
     * Provides options to view, approve, reject applications, and approve withdrawals.
     *
     * @param manager The HDB manager viewing the menu
     * @return List of application IDs that were processed, or null if no action was taken
     */
    public ArrayList<String> displayApplicationMenu(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.println("===== Residential Application Menu =====");
        System.out.println("Please choose an option:");
        System.out.println("1. View applications");
        System.out.println("2. Approve application");
        System.out.println("3. Reject application");
        System.out.println("4. Approve withdrawal");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");

        int choice = InputUtils.readInt();

        switch (choice) {
            case 1 -> {
                controller.displayApplicationsByProject(manager.getManagedProject());
                return null;
            }
            case 2 -> {
                return approveApplication(manager);
            }
            case 3 -> {
                return rejectApplication(manager);
            }
            case 4 -> {
                return approveWithdrawal(manager);
            }
            case 5 -> {
                return new ArrayList<String>(Arrays.asList("c"));
            }
            default -> System.out.println("Invalid choice! Please enter 1-3.\n");
        }
        return null;
    }

    /**
     * Handles the approval process for applications by a manager.
     *
     * @param manager The manager approving the applications
     * @return List of approved application IDs
     */
    public ArrayList<String> approveApplication(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter the applicant ID: ");
        String applicantId = InputUtils.nextLine();
        return controller.processApplication(manager, applicantId, true);
    }

    /**
     * Handles the rejection process for applications by a manager.
     *
     * @param manager The manager rejecting the applications
     * @return List of rejected application IDs
     */
    public ArrayList<String> rejectApplication(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter the applicant ID: ");
        String applicantId = InputUtils.nextLine();
        return controller.processApplication(manager, applicantId, false);
    }

    /**
     * Handles the withdrawal approval process for applications by a manager.
     *
     * @param manager The manager approving the withdrawals
     * @return List of withdrawal-approved application IDs
     */
    public ArrayList<String> approveWithdrawal(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter the applicant ID: ");
        String applicantId = InputUtils.nextLine();
        return controller.approveWithdrawal(manager, applicantId);
    }

    /**
     * Displays a list of applications with their details.
     *
     * @param applications The list of applications to display
     */
    public void displayApplications(ArrayList<ResidentialApplication> applications) {
        if(applications.isEmpty()) {
            System.out.println("There is no application at the time!");
            return;
        }
        for(ResidentialApplication e : applications) {
            InputUtils.printSmallDivider();

            // Display application details
            System.out.println("--------------------------------");
            System.out.println("Applicant: " + e.getApplicantId());
            System.out.println("Project: " + e.getProjectName());
            System.out.println("Application status: " + e.getStatus());
        }
    }
}
