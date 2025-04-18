package HdbOfficer;
import Project.Flat;
import Project.Flat.Type;
import Project.Project;
import Project.ProjectControllerInterface;
import Utility.*;
import Users.*;

import java.util.Map;
import java.util.regex.Pattern;

public final class HdbOfficerUI implements UserUI<HdbOfficer, HdbOfficerRepo> {

    private final HdbOfficerController officerController;

    public HdbOfficerUI(HdbOfficerController hdbOfficerController) {
        officerController = hdbOfficerController;
    }

    public HdbOfficer displayLogin(HdbOfficerRepo officerRepo) {
        while (true) {
            InputUtils.printBigDivider();
            System.out.println("\nOfficer Portal:");
            System.out.println("Please choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Forget Password");
            System.out.println("3. Back to Menu");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    HdbOfficer hdbOfficer = login(officerRepo);
                    if (hdbOfficer != null) {
                        return hdbOfficer;
                    }
                }
                case 2 -> forgetPassword(officerRepo);
                case 3 -> {
                    exitToMenu();
                    return null;
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }

    public HdbOfficer login(HdbOfficerRepo officerRepo) {
        try {
            InputUtils.printBigDivider();
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!\n");
            }

            String officerId = officerRepo.generateID(nric);
            HdbOfficer hdbOfficer = officerRepo.getUser(officerId);
            if (hdbOfficer == null) {
                throw new IllegalArgumentException("NRIC not found!\n");
            }

            System.out.print("Enter Password: ");
            String password = InputUtils.nextLine().trim();
            if (!hdbOfficer.validatePassword(password)) {
                throw new SecurityException("Incorrect password\n");
            }

            System.out.println("\nLogin successful: Welcome, Officer " + hdbOfficer.getName() + "!\n");
            return hdbOfficer;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void forgetPassword(HdbOfficerRepo officerRepo) {
        try {
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!");
            }

            String officerId = officerRepo.generateID(nric);
            HdbOfficer hdbOfficer = officerRepo.getUser(officerId);
            if (hdbOfficer == null) {
                throw new IllegalArgumentException("NRIC not found!");
            }

            hdbOfficer.resetPassword();
            System.out.println("Password reset successfully.\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayDashboard(HdbOfficer hdbOfficer) {
        while (true) {
            InputUtils.printBigDivider();
            System.out.printf("OFFICER DASHBOARD" +
                            "\n---------------------\n" +
                            "Name: %s | Marital status: %s | Age: %d\n",
                    hdbOfficer.getName(),
                    hdbOfficer.getMaritalStatus(),
                    hdbOfficer.getAge());
            InputUtils.printSmallDivider();
            System.out.println("1. Residential Application Menu");
            System.out.println("2. Team Application Menu");
            System.out.println("3. Assigned Project Menu");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> displayResidentialMenu(hdbOfficer);
                case 2 -> officerController.displayTeamApplicationMenu(hdbOfficer);
                case 3 -> displayAssignedProjectMenu(hdbOfficer);
                case 4 -> {
                    officerController.saveFile();
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-4.\n");
            }
        }
    }

    public void displayAssignedProjectMenu(HdbOfficer officer) {

    }

    public void printStatus(String message) {
        System.out.println("\n[STATUS]: " + message + "\n");
    }

    public void printProjectDetails(Project project) {
        System.out.println("\nPROJECT DETAILS:");
        System.out.println("Name: " + project.getName());
        //System.out.println("Flat Availability:");

        //Map<Flat.Type, Integer> flatAvailability = (Map<Type, Integer>) project.getFlatTypesAvailability();

        //for (Map.Entry<Flat.Type, Integer> entry : flatAvailability.entrySet()) {
            //System.out.println(entry.getKey() + ": " + entry.getValue() + " units remaining");
        //}

        System.out.println();
    }

    public void displayResidentialMenu(HdbOfficer officer) {
        officerController.displayResidentialMenu(officer);
    }
}
