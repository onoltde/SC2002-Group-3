package HdbManager;
import Users.*;
import Utility.*;
import java.util.regex.Pattern;

public final class HdbManagerUI implements UserUI<HdbManager,HdbManagerRepo>{
    private static HdbManagerController managerController;

    public HdbManagerUI(HdbManagerController hdbManagerController){
        this.managerController = hdbManagerController;
    }

    public HdbManager displayLogin(HdbManagerRepo managerRepo){

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
                case 1 -> {
                    HdbManager hdbManager = login(managerRepo);
                    if (hdbManager != null) {
                        return hdbManager; // Return immediately on successful login
                    }
                }
                case 2 -> forgetPassword(managerRepo);
                case 3 -> {
                    exitToMenu();
                    return null; // return null to exit
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }

        }
    }

    public HdbManager login(HdbManagerRepo managerRepo) {
        try {
            InputUtils.printBigDivider();
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!\n");
            }

            //2.checks if nric is in database
            String managerId = managerRepo.generateID(nric);  // generate hdbManager ID (OF-last4)
            HdbManager hdbManager = managerRepo.getUser(managerId);
            if (hdbManager == null) {
                throw new IllegalArgumentException("NRIC not found!\n");
            }

            // 3.check if password is correct
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

    public void forgetPassword(HdbManagerRepo managerRepo) {
        try {
            InputUtils.printBigDivider();
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!");
            }

            //2.checks if nric is in database
            String managerId = managerRepo.generateID(nric); // generate hdbManager ID (OF-last4)
            HdbManager hdbManager = managerRepo.getUser(managerId);
            if (hdbManager == null) {
                throw new IllegalArgumentException("NRIC not found!");
            }

            //3.reset password
            hdbManager.resetPassword();

            System.out.println("Password is now reset to \"password\". Please proceed to login.\n");
            return;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // NRIC format/hdbManager not found
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return;
        }

    }

    public void changePassword(HdbManager manager) {
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
                manager.changePassword(newPassword);
                System.out.println("Password changed successfully! Signing out...");
                break;
            } else {
                System.out.println("Error: Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
            }
        }
    }

    public void displayDashboard(HdbManager hdbManager){
        while (true) {
            InputUtils.printBigDivider();
            System.out.printf("MANAGER DASHBOARD" +
                            "\n---------------------\n" +
                            "Name: %s | Marital status: %s | Age: %d\n",
                    hdbManager.getName(),
                    hdbManager.getMaritalStatus(),
                    hdbManager.getAge());
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
                case 1 -> {//project menu CRUD
                    managerController.displayProjectMenu(hdbManager);
                }
                case 2 -> {
                    displayApplicationMenu(hdbManager);
                }
                case 3 -> {//enquiry menu
                    managerController.displayEnquiryMenu(hdbManager);
                }
                case 4 -> {// report
                    managerController.displayReportMenu(hdbManager);
                }
                case 5 -> changePassword(hdbManager);
                case 6 -> {//exit
                    managerController.saveFile();
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-4.\n");
            }

        }
    }

    public void displayApplicationMenu(HdbManager hdbManager) {
        while (true) {
            InputUtils.printSmallDivider();
            System.out.println("Please choose an option:");
            System.out.println("1. Residential application menu");
            System.out.println("2. Team application menu");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {//project menu CRUD
                    managerController.displayResApplicationMenu(hdbManager);
                }
                case 2 -> {
                    managerController.displayTeamApplicationMenu(hdbManager);
                }
                case 3 -> {//enquiry menu
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }

        }
    }

}//end of class

