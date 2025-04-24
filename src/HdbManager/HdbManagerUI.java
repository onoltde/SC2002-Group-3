package HdbManager;
import Users.*;
import Utility.*;
import java.util.regex.Pattern;

/**
 * UI class for handling user interactions for HdbManager.
 */
public final class HdbManagerUI implements UserUI<HdbManager,HdbManagerRepo> {
    private static HdbManagerController managerController;

    /**
     * Constructor to initialize the HdbManagerUI with a controller.
     * @param hdbManagerController The controller to manage the HDB Manager operations.
     */
    public HdbManagerUI(HdbManagerController hdbManagerController) {
        this.managerController = hdbManagerController;
    }

    /**
     * Displays the login screen for the HdbManager.
     * @param managerRepo The repository holding all HdbManager data.
     * @return The logged-in HdbManager object if login is successful, null if failed or cancelled.
     */
    public HdbManager displayLogin(HdbManagerRepo managerRepo) {
        while (true) {
            InputUtils.printBigDivider();
            System.out.println("\nManager Portal:");
            System.out.println("Please choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Forget Password");
            System.out.println("3. Back to Menu");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1:
                    HdbManager hdbManager = login(managerRepo);
                    if (hdbManager != null) {
                        return hdbManager; // Return immediately on successful login
                    }
                    break;
                case 2:
                    forgetPassword(managerRepo);
                    break;
                case 3:
                    exitToMenu();
                    return null; // return null to exit
                default:
                    System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }

    /**
     * Handles the login logic for the HdbManager.
     * @param managerRepo The repository holding all HdbManager data.
     * @return The logged-in HdbManager if successful, null if failed.
     */
    public HdbManager login(HdbManagerRepo managerRepo) {
        try {
            InputUtils.printBigDivider();
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();

            // Check if NRIC is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!\n");
            }

            // Generate hdbManager ID from NRIC
            String managerId = managerRepo.generateID(nric);
            HdbManager hdbManager = managerRepo.getUser(managerId);
            if (hdbManager == null) {
                throw new IllegalArgumentException("NRIC not found!\n");
            }

            System.out.print("Enter Password: ");
            String password = InputUtils.nextLine().trim();
            if (!hdbManager.validatePassword(password)) {
                throw new SecurityException("Incorrect password\n");
            }

            System.out.println("\nLogin successful: Welcome, Manager " + hdbManager.getName() + "!\n");
            return hdbManager;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // NRIC format/hdbManager not found
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
     * Handles the password reset procedure for a forgotten password.
     * @param managerRepo The repository holding all HdbManager data.
     */
    public void forgetPassword(HdbManagerRepo managerRepo) {
        try {
            InputUtils.printBigDivider();
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();

            // Check if NRIC is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!");
            }

            // Check if NRIC exists in the system
            String managerId = managerRepo.generateID(nric);
            HdbManager hdbManager = managerRepo.getUser(managerId);
            if (hdbManager == null) {
                throw new IllegalArgumentException("NRIC not found!");
            }

            // Reset the password to default value
            hdbManager.resetPassword();
            System.out.println("Password is now reset to \"password\". Please proceed to login.\n");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // NRIC format/hdbManager not found
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Allows the manager to change their password.
     * @param hdbManager The HdbManager whose password will be changed.
     */
    public void changePassword(HdbManager hdbManager) {
        final int MIN_PASSWORD_LENGTH = 8;

        System.out.println("Changing password... (Enter 'x' to cancel)");

        while (true) {
            System.out.print("Enter new password (min " + MIN_PASSWORD_LENGTH + " chars): ");
            String newPassword = InputUtils.nextLine().trim(); // Trim to remove extra spaces

            if (newPassword.equals("x")) {
                System.out.println("Password change cancelled.");
                return;
            }

            if (newPassword.length() >= MIN_PASSWORD_LENGTH) {
                hdbManager.changePassword(newPassword);
                System.out.println("Password changed successfully! Signing out...");
                break;
            } else {
                System.out.println("Error: Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
            }
        }
    }

    /**
     * Displays the main dashboard for the logged-in HdbManager.
     * @param hdbManager The HdbManager whose dashboard is being displayed.
     */
    public void displayDashboard(HdbManager hdbManager) {
        while (true) {
            InputUtils.printBigDivider();
            System.out.printf("MANAGER DASHBOARD\n---------------------\n" +
                            "Name: %s | Marital status: %s | Age: %d\n",
                    hdbManager.getName(), hdbManager.getMaritalStatus(), hdbManager.getAge());
            System.out.println("----------------------------------");
            System.out.println("Please choose an option:");
            System.out.println("1. Project menu");
            System.out.println("2. Application menu");
            System.out.println("3. Enquiry menu");
            System.out.println("4. Report Menu");
            System.out.println("5. Change password");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1:
                    managerController.displayProjectMenu(hdbManager);
                    break;
                case 2:
                    displayApplication(hdbManager);
                    break;
                case 3:
                    managerController.displayEnquiryMenu(hdbManager);
                    break;
                case 4:
                    managerController.displayReportMenu(hdbManager);
                    break;
                case 5:
                    changePassword(hdbManager);
                    return;
                case 6:
                    managerController.saveFile();
                    return;
                default:
                    System.out.println("Invalid choice! Please enter 1-6.\n");
            }
        }
    }

    /**
     * Displays the application menu options for the HdbManager.
     * @param hdbManager The HdbManager whose application menu is being displayed.
     */
    public void displayApplication(HdbManager hdbManager) {
        while (true) {
            InputUtils.printSmallDivider();
            System.out.println("====== Application Menu ======");
            System.out.println("Please choose an option:");
            System.out.println("1. Residential application menu");
            System.out.println("2. Team application menu");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1:
                    managerController.displayResApplicationMenu(hdbManager);
                    break;
                case 2:
                    managerController.displayTeamApplicationMenu(hdbManager);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }
}
