package Application.Team;
import Application.Residential.ResidentialApplication;
import HdbOfficer.*;
import HdbManager.*;
import Project.ProjectController;
import Utility.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * User Interface class for handling team applications.
 * This class provides the interface for officers and managers to interact with
 * the team application system, including viewing, applying, and managing applications.
 */
public class TeamApplicationUI {

    private TeamApplicationController teamAppController;

    /**
     * Constructs a new TeamApplicationUI with the specified controller.
     *
     * @param teamAppController The controller to handle application logic
     */
    public TeamApplicationUI(TeamApplicationController teamAppController){
        this.teamAppController = teamAppController;
    }

    /**
     * Displays the application menu for an HDB officer.
     * Shows current application details and provides options to view project details,
     * apply for a team, or exit.
     *
     * @param officer The HDB officer viewing the menu
     * @return The created application if a new one is made, null otherwise
     */
    public TeamApplication displayApplicationMenu(HdbOfficer officer){
        if (!officer.hasTeamApplication()){
            System.out.println("You do not have an an active Team application.");
            System.out.println("To make an application, please go to \'Apply for Team\'.");
        }else{
            TeamApplication application = officer.getTeamApplication();
            String projectName = application.getProjectName();
            System.out.println("Applied to : " + projectName + " Team");
            System.out.println("Status : " + application.getStatus());
        }

        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Please choose an option:");
            System.out.println("1. View applied Team details");
            System.out.println("2. Apply for Team");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {// view applied project details
                    if (!officer.hasTeamApplication()){
                        System.out.println("You do not have an an active Team application.");
                        System.out.println("To make an application, please go to \'Apply for Team\'.");
                    }else{
                        teamAppController.displayAssignedProject(officer);
                    }
                }
                case 2 -> {//apply for projects
                    // check if the officer is already applying for other projects
                    if (officer.hasTeamApplication()) {
                        System.out.println("You already have an active application, please wait for approval before applying for another.");
                        continue;
                    }
                    TeamApplication ta = teamAppController.displayProjects(officer);
                    return ta;
                }
                case 3 -> {//exit
                    return null;
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
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
        System.out.println("======= Team Application Menu =======");
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
                teamAppController.displayApplicationsByProject(manager.getManagedProject());
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
            default -> System.out.println("Invalid choice! Please enter 1-5.\n");
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
        System.out.print("Enter the officer ID: ");
        String officerId = InputUtils.nextLine();
        return teamAppController.processApplication(manager, officerId, true);
    }

    /**
     * Handles the rejection process for applications by a manager.
     *
     * @param manager The manager rejecting the applications
     * @return List of rejected application IDs
     */
    public ArrayList<String> rejectApplication(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter the officer ID: ");
        String officerId = InputUtils.nextLine();
        return teamAppController.processApplication(manager, officerId, false);
    }

    /**
     * Handles the withdrawal approval process for applications by a manager.
     *
     * @param manager The manager approving the withdrawals
     * @return List of withdrawal-approved application IDs
     */
    public ArrayList<String> approveWithdrawal(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter the officer ID: ");
        String officerId = InputUtils.nextLine();
        return teamAppController.approveWithdrawal(manager, officerId);
    }

    /**
     * Displays a list of applications with their details.
     *
     * @param applications The list of applications to display
     */
    public void displayApplications(ArrayList<TeamApplication> applications) {
        for(TeamApplication e : applications) {
            InputUtils.printSmallDivider();

            // Display application details
            System.out.println("--------------------------------");
            System.out.println("Officer: " + e.getOfficerID());
            System.out.println("Project: " + e.getProjectName());
            System.out.println("Application status: " + e.getStatus());
        }
    }
}
