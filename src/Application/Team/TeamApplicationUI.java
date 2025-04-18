package Application.Team;
import Application.Residential.ResidentialApplication;
import HdbOfficer.*;
import HdbManager.*;
import Project.ProjectController;
import Utility.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TeamApplicationUI {

    private TeamApplicationController teamAppController;

    public TeamApplicationUI(TeamApplicationController teamAppController){
        this.teamAppController = teamAppController;
    }

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


    // manager methods
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

    public ArrayList<String> approveApplication(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter the officer ID: ");
        String officerId = InputUtils.nextLine();
        return teamAppController.processApplication(manager, officerId, true);
    }

    public ArrayList<String> rejectApplication(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter the officer ID: ");
        String officerId = InputUtils.nextLine();
        return teamAppController.processApplication(manager, officerId, false);
    }

    public ArrayList<String> approveWithdrawal(HdbManager manager) {
        InputUtils.printSmallDivider();
        System.out.print("Enter the officer ID: ");
        String officerId = InputUtils.nextLine();
        return teamAppController.approveWithdrawal(manager, officerId);
    }

    void displayApplications(ArrayList<TeamApplication> applications) {
        //////// !!!!!!!!!!!!!!!!!!!!!!!!!!
        // printing applications
    }
}
