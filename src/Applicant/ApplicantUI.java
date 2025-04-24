package Applicant;

import Enquiry.EnquiryController;
import Utility.*;
import Users.*;
import java.util.regex.Pattern;

/**
 * Handles the user interface for the Applicant role in the BTO system.
 * Provides login, password management, dashboard, and application viewing features.
 * Implements the UserUI interface with Applicant and ApplicantRepo types.
 */
public final class ApplicantUI implements UserUI<Applicant, ApplicantRepo>{

    /** Controller responsible for handling applicant operations and logic. */
    private final ApplicantController applicantController;

    /**
     * Constructs an ApplicantUI with the specified controller.
     *
     * @param applicantController the controller used for applicant logic
     */
    public ApplicantUI(ApplicantController applicantController) {
        this.applicantController = applicantController;
    }

    /**
     * Displays the login menu for applicants and processes user input for login,
     * password reset, or exit to main menu.
     *
     * @param applicantRepo the repository containing applicant data
     * @return the authenticated applicant if login is successful; null otherwise
     */
    public Applicant displayLogin(ApplicantRepo applicantRepo){

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
     * Performs applicant login by validating NRIC and password credentials.
     *
     * @param applicantRepo the repository to verify applicant identity
     * @return the logged-in applicant, or null if login fails
     */
    public Applicant login(ApplicantRepo applicantRepo){
        try {
            InputUtils.printBigDivider();
            System.out.print("Enter NRIC: ");

            String nric = InputUtils.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!\n");
            }

            //2.checks if nric is in database
            String applicantId = "AP-" + nric.substring(5);  // generate applicant ID (AP-last4)
            Applicant applicant = applicantRepo.getUser(applicantId);
            if (applicant == null) {
                throw new IllegalArgumentException("NRIC not found!\n");
            }

            // 3.check if password is correct
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
     * Allows applicants to reset their password to a default value after verifying their NRIC.
     *
     * @param applicantRepo the repository to identify the applicant
     */
    public void forgetPassword(ApplicantRepo applicantRepo) {
        try {
            InputUtils.printBigDivider();
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!");
            }

            //2.checks if nric is in database
            String applicantId = "AP-" + nric.substring(5);  // generate applicant ID (AP-last4)
            Applicant applicant = applicantRepo.getUser(applicantId);
            if (applicant == null) {
                throw new IllegalArgumentException("NRIC not found!");
            }

            //3.reset password
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
     * Allows the logged-in applicant to change their password. Enforces minimum length.
     *
     * @param applicant the currently authenticated applicant
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
     * Displays the dashboard menu for applicants after login. Provides access to
     * application, BTO project viewing, enquiries, password change, and exit options.
     *
     * @param applicant the currently authenticated applicant
     */
    public void displayDashboard(Applicant applicant){
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
            System.out.println("4. Change password");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {//view application menu
                    displayApplicationMenu(applicant);
                }
                case 2 ->{//view current BTO projects
                    applicantController.viewCurrentProjects(applicant);
                }
                case 3 -> {//go to enquiry menu
                    applicantController.displayEnquiryMenu(applicant);
                }
                case 4 -> {//change password
                    changePassword(applicant);
                    return;
                }
                case 5 -> {//exit
                    applicantController.saveFile();    //saves changes to file
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-5.\n");
            }

        }
    }

    /**
     * Displays the application menu if the applicant has an active application.
     * Otherwise, prompts the user to apply via the BTO project menu.
     *
     * @param applicant the currently authenticated applicant
     */
    public void displayApplicationMenu(Applicant applicant){
        InputUtils.printBigDivider();
        if (applicant.getResidentialApplication() == null){
            System.out.println("You do not have an active application.");
            System.out.println("To apply for a BTO, please go to \"View current BTO projects\".");
        }else{
            applicantController.displayApplicationMenu(applicant);
        }
    }

}//end of class
