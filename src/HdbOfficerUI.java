import java.util.Scanner;
import java.util.regex.Pattern;

public final class HdbOfficerUI implements UserUI<HdbOfficer>{
    private static Scanner sc;
    private static HdbOfficerController controller;

    public HdbOfficerUI(Scanner scanner,HdbOfficerController hdbOfficerController){
        sc = scanner;
        controller = hdbOfficerController;
    }

    public HdbOfficer displayLogin(){
        System.out.println("\nOfficer Portal Selected");
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
                        HdbOfficer hdbOfficer = login();
                        if (hdbOfficer != null) {
                            return hdbOfficer; // Return immediately on successful login
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

    public HdbOfficer login() {
        try {
            printDivider();
            System.out.print("Enter NRIC: ");
            String nric = sc.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!\n");
            }

            //2.checks if nric is in database
            String officerId = controller.getRepo().generateID(nric);  // generate hdbOfficer ID (OF-last4)
            HdbOfficer hdbOfficer = controller.getRepo().getUser(officerId);
            if (hdbOfficer == null) {
                throw new IllegalArgumentException("NRIC not found!\n");
            }

            // 3.check if password is correct
            System.out.print("Enter Password: ");
            String password = sc.nextLine().trim();
            if (!hdbOfficer.validatePassword(password)) {
                throw new SecurityException("Incorrect password\n");
            }

            System.out.println("\nLogin successful: Welcome, Officer " + hdbOfficer.getName() + "!\n");
            return hdbOfficer;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // NRIC format/hdbOfficer not found
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
            String officerId = controller.getRepo().generateID(nric); // generate hdbOfficer ID (OF-last4)
            HdbOfficer hdbOfficer = controller.getRepo().getUser(officerId);
            if (hdbOfficer == null) {
                throw new IllegalArgumentException("NRIC not found!");
            }

            //3.reset password
            hdbOfficer.resetPassword();

            System.out.println("Password is now reset to \"password\". Please proceed to login.\n");
            return;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // NRIC format/hdbOfficer not found
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return;
        }

    }

    public void displayDashboard(HdbOfficer hdbOfficer){
        printDivider();
        System.out.printf("\nOFFICER\nName: %s | Marital status: %s | Age: %d\n",
                hdbOfficer.getName(),
                hdbOfficer.getMaritalStatus(),
                hdbOfficer.getAge());
        displayApplicationStatus(hdbOfficer);

        while (true) {
            printDivider();
            System.out.println("Please choose an option:");
            System.out.println("1. OFFICER DASHBOARD");
            System.out.println("2. OFFICER DASHBOARD");
            System.out.println("3. OFFICER DASHBOARD");
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

    //need to think of how to design officer application thing
    public static void displayApplicationStatus(HdbOfficer hdbOfficer){
        if (hdbOfficer.getApplication() == null){
            System.out.println("You do not have an active application.");
        }else{
            System.out.println("Application Status: " + hdbOfficer.getApplication().getStatus());
        }
    }
}
