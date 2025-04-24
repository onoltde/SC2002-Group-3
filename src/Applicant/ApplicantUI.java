package Applicant;

import Enquiry.EnquiryController;
import Utility.*;
import Users.*;
import java.util.regex.Pattern;

/**
 * The ApplicantUI class provides the user interface (UI) for applicants in the portal.
 * It handles all interactions with the user, such as login, password reset, and navigation
 * within the applicant dashboard and menus.
 */
public final class ApplicantUI implements UserUI<Applicant, ApplicantRepo> {

    private final ApplicantController applicantController;

    /**
     * Constructor for ApplicantUI.
     * Initializes the applicant controller to manage the application's logic.
     *
     * @param applicantController The controller for managing applicant-related logic.
     */
    public ApplicantUI(ApplicantController applicantController) {
        this.applicantController = applicantController;
    }

    /**
     * Displays the login menu where the user can choose to login, reset password, or exit.
     *
     * @param applicantRepo The repository containing all applicants.
     * @return The logged-in applicant, or null if login is unsuccessful or the user exits.
     */
    public Applicant displayLogin(ApplicantRepo applicantRepo) {
        while (true) {
            InputUtils.printBigDivider();
            System.out.println("\nApplicant Portal:");
            System.out.println("--------------------");
            System.out.println("Please choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Forget Password");
            System.out.println("3. Back to Menu");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    Applicant applicant = login(applicantRepo);
                    if (applicant != null) {
                        return applicant; // Return immediately on successful login
                    }
                }
                case 2 -> forgetPassword(applicantRepo);
                case 3 -> {
                    exitToMenu();
                    return null; // Explicitly return null on exit
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }

    /**
     * Handles the login process for the applicant.
     * It checks the validity of the NRIC, retrieves the applicant from the repository,
     * and verifies the password.
     *
     * @param applicantRepo The repository containing all applicants.
     * @return The logged-in applicant, or null if login fails.
     */
    public Applicant login(ApplicantRepo applicantRepo) {
        try {
            InputUtils.printBigDivider();
            System.out.print("Enter NRIC: ");

            String nric = InputUtils.nextLine().trim().toUpperCase();
            // Validate NRIC format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!\n");
            }

            // Retrieve applicant by generated ID
            String applicantId = "AP-" + nric.substring(5);  // Generate applicant ID (AP-last4)
            Applicant applicant = applicantRepo.getUser(applicantId);
            if (applicant == null) {
                throw new IllegalArgumentException("NRIC not found!\n");
            }

            // Verify password
            System.out.print("Enter Password: ");
            String password = InputUtils.nextLine().trim();
            if (!applicant.validatePassword(password)) {
                throw new SecurityException("Incorrect password\n");
            }

            System.out.println("\nLogin successful: Welcome, Applicant " + applicant.getName() + "!");
            return applicant;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // NRIC format/applicant not found
            return null;
        } catch (SecurityException e) {
            System.out.println(e.getMessage()); // Wrong password
            return null;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Initiates the password reset process for an applicant who has forgotten their password.
     *
     * @param applicantRepo The repository containing all applicants.
     */
    public void forgetPassword(ApplicantRepo applicantRepo) {
        try {
            InputUtils.printBigDivider();
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();
            // Validate NRIC format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!");
            }

            // Retrieve applicant by generated ID
            String applicantId = "AP-" + nric.substring(5);  // Generate applicant ID (AP-last4)
            Applicant applicant = applicantRepo.getUser(applicantId);
            if (applicant == null) {
                throw new IllegalArgumentException("NRIC not found!");
            }

            // Reset the password to a default value
            applicant.resetPassword();

            System.out.println("Password is now reset to \"password\". Please proceed to login.\n");
            return;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // NRIC format/applicant not found
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return;
        }
    }

    /**
     * Allows the applicant to change their password.
     * The new password must meet a minimum length requirement.
     *
     * @param applicant The applicant whose password is to be changed.
     */
    public void changePassword(Applicant applicant) {
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
                applicant.changePassword(newPassword);
                System.out.println("Password changed successfully! Signing out...");
                break;
            } else {
                System.out.println("Error: Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
            }
        }
    }

    /**
     * Displays the main dashboard for the applicant.
     * The dashboard allows the applicant to access various options such as viewing applications,
     * checking current BTO projects, managing enquiries, and changing their password.
     *
     * @param applicant The applicant whose dashboard is being displayed.
     */
    public void displayDashboard(Applicant applicant) {
        while (true) {
            InputUtils.printBigDivider();
            System.out.printf("APPLICANT DASHBOARD" +
                            "\n---------------------\n" +
                            "Name: %s | Marital status: %s | Age: %d\n",
                    applicant.getName(),
                    applicant.getMaritalStatus(),
                    applicant.getAge());
            InputUtils.printSmallDivider();
            System.out.println("Please choose an option:");
            System.out.println("1. View my application");
            System.out.println("2. View current BTO projects");
            System.out.println("3. Enquiry menu");
            System.out.println("4. See reports");
            System.out.println("5. Change password");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    // View application menu
                    displayApplicationMenu(applicant);
                }
                case 2 -> {
                    // View current BTO projects
                    applicantController.viewCurrentProjects(applicant);
                }
                case 3 -> {
                    // Go to enquiry menu
                    applicantController.displayEnquiryMenu(applicant);
                }
                case 4 -> {
                    // Display reports
                    applicantController.displayReports(applicant);
                }
                case 5 -> {
                    // Change password
                    changePassword(applicant);
                    return;
                }
                case 6 -> {
                    // Exit
                    applicantController.saveFile(); // Save changes to file
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-5.\n");
            }
        }
    }

    /**
     * Displays the application menu for the applicant.
     * If the applicant does not have an active application, they are instructed to apply for a BTO.
     *
     * @param applicant The applicant whose application status is checked.
     */
    public void displayApplicationMenu(Applicant applicant) {
        InputUtils.printBigDivider();
        if (applicant.getResidentialApplication() == null) {
            System.out.println("You do not have an active application.");
            System.out.println("To apply for a BTO, please go to \"View current BTO projects\".");
        } else {
            applicantController.displayApplicationMenu(applicant);
        }
    }
}
