import java.util.Scanner;
import java.util.regex.Pattern;

public class HdbManagerUI implements UserUI<HdbManager>{
    private static Scanner sc;
    private static HdbManagerController controller;

    public HdbManagerUI(Scanner scanner, HdbManagerController hdbManagerController){
        sc = scanner;
        controller = hdbManagerController;
    }

    public HdbManager displayLogin(){
        System.out.println("\nManager Portal Selected");
        while (true) {

            System.out.println("Please choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Forget Password");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {
                        HdbManager hdbManager = login();
                        if (hdbManager != null) {
                            return hdbManager; // Return immediately on successful login
                        }
                    }
                    case 2 -> forgetPassword();
                    case 3 -> {
                        exitMessage();
                        return null; // return null to exit
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-3.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.\n");
            }
        }
    }

    public HdbManager login() {
        try {
            printDivider();
            System.out.print("Enter NRIC: ");
            String nric = sc.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!\n");
            }

            //2.checks if nric is in database
            String managerId = controller.getRepo().generateID(nric);  // generate hdbManager ID (OF-last4)
            HdbManager hdbManager = controller.getRepo().getUser(managerId);
            if (hdbManager == null) {
                throw new IllegalArgumentException("NRIC not found!\n");
            }

            // 3.check if password is correct
            System.out.print("Enter Password: ");
            String password = sc.nextLine().trim();
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

    public void forgetPassword() {
        try {
            System.out.print("Enter NRIC: ");
            String nric = sc.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!");
            }

            //2.checks if nric is in database
            String managerId = controller.getRepo().generateID(nric); // generate hdbManager ID (OF-last4)
            HdbManager hdbManager = controller.getRepo().getUser(managerId);
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

    public void displayDashboard(HdbManager hdbManager){
        printDivider();
        System.out.printf("\nMANAGER\nName: %s | Marital status: %s | Age: %d\n",
                hdbManager.getName(),
                hdbManager.getMaritalStatus(),
                hdbManager.getAge());

        while (true) {
            printDivider();
            System.out.println("Please choose an option:");
            System.out.println("1. MANAGER DASHBOARD");
            System.out.println("2. MANAGER DASHBOARD");
            System.out.println("3. MANAGER DASHBOARD");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {//view application menu

                    }
                    case 2 ->{//view current BTO projects

                    }
                    case 3 -> {//view enquiry menu

                    }
                    case 4 -> {//exit
                        controller.exitProgram();
                        printDivider();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-4.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.\n");
            }
        }

    }

}//end of class

