package Application.Residential;
import Applicant.*;
import Application.Application;
import HdbOfficer.HdbOfficer;
import Project.*;
import Utility.*;
import Project.Flat;


public class ResidentialApplicationUI {
    private ResidentialApplicationController controller;

    public ResidentialApplicationUI(ResidentialApplicationController controller){
        this.controller = controller;
    }

    //applicant methods
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
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {//view project details
                    controller.displayProjectFlatDetails(projectName,flatType);
                }
                case 2 -> {//request withdrawal
                    controller.requestWithdrawal(application);
                }
                case 3 -> {//exit
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }

    //officer methods
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
}
