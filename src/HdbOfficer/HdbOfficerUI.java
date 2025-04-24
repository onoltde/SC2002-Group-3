package HdbOfficer;

import HdbManager.HdbManager;
import Project.Flat;
import Project.Flat.Type;
import Project.Project;
import Project.ProjectControllerInterface;
import Utility.*;
import Users.*;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * User interface for managing HDB Officer functionalities.
 * Provides methods for logging in, changing passwords, and navigating the officer dashboard.
 */
public final class HdbOfficerUI implements UserUI<HdbOfficer, HdbOfficerRepo> {

    private final HdbOfficerController officerController;

    /**
     * Constructs the HdbOfficerUI with the given HdbOfficerController.
     *
     * @param hdbOfficerController The controller responsible for managing officer-related actions.
     */
    public HdbOfficerUI(HdbOfficerController hdbOfficerController) {
        officerController = hdbOfficerController;
    }

    /**
     * Displays the login menu, allowing an officer to log in, reset their password, or exit to the main menu.
     *
     * @param officerRepo The repository of HDB officers.
     * @return The logged-in officer, or null if the user chooses to exit.
     */
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

    /**
     * Handles officer login by verifying NRIC and password.
     *
     * @param officerRepo The repository of HDB officers.
     * @return The logged-in officer if successful, or null if login fails.
     */
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

    /**
     * Allows an officer to reset their password if they forget it.
     *
     * @param officerRepo The repository of HDB officers.
     */
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

    /**
     * Allows an officer to change their password, ensuring it meets the minimum length requirement.
     *
     * @param officer The officer whose password is being changed.
     */
    public void changePassword(HdbOfficer officer) {
        final int MIN_PASSWORD_LENGTH = 8;

        System.out.println("Changing password... (Enter 'x' to cancel)");

        while (true) {
            System.out.print("Enter new password (min " + MIN_PASSWORD_LENGTH + " chars): ");
            String newPassword = InputUtils.nextLine().trim(); // Trim to remove extra spaces

            if (newPassword.equalsIgnoreCase("x")) {
                System.out.println("Password change cancelled.");
                return;
            }

            if (newPassword.length() >= MIN_PASSWORD_LENGTH) {
                officer.changePassword(newPassword);
                System.out.println("Password changed successfully! Signing out...");
                break;
            } else {
                System.out.println("Error: Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
            }
        }
    }

    /**
     * Displays the officer's dashboard with various menu options for managing applications, projects, and password.
     *
     * @param hdbOfficer The officer whose dashboard is being displayed.
     */
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
            System.out.println("4. Change password");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> officerController.displayResidentialMenu(hdbOfficer);
                case 2 -> officerController.displayTeamApplicationMenu(hdbOfficer);
                case 3 -> officerController.displayAssignedProjectMenu(hdbOfficer);
                case 4 -> {
                    changePassword(hdbOfficer);
                    return;
                }
                case 5 -> {
                    officerController.saveFile();
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-4.\n");
            }
        }
    }

    /**
     * Displays the menu for managing assigned projects.
     *
     * @param hdbOfficer The officer whose assigned projects are being managed.
     */
    public void displayAssignedProjectMenu(HdbOfficer hdbOfficer) {
        while (true) {
            InputUtils.printSmallDivider();
            System.out.println("====Assigned Project Menu===");
            System.out.println("1. View applications to this project");
            System.out.println("2. View enquiries to this project");
            System.out.println("3. Report menu");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> officerController.viewProjectApplications(hdbOfficer);
                case 2 -> officerController.viewProjectEnquiries(hdbOfficer);
                case 3 -> officerController.viewReport(hdbOfficer);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }
}