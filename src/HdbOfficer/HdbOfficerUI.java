package HdbOfficer;
import Utility.*;
import Users.*;

import java.util.regex.Pattern;

public final class HdbOfficerUI implements UserUI<HdbOfficer>{
    private static HdbOfficerController officerController;
    private static HdbOfficerRepo officerRepo;

    public HdbOfficerUI(HdbOfficerController hdbOfficerController, HdbOfficerRepo hdbOfficerRepo){
        officerController = hdbOfficerController;
        officerRepo = hdbOfficerRepo;
    }

    public HdbOfficer displayLogin(){

        while (true) {
            System.out.println();
            printDivider();
            System.out.println("\nOfficer Portal:");
            System.out.println("Please choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Forget Password");
            System.out.println("3. Back to Menu");
            System.out.print("Enter your choice (1-3): ");


            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    HdbOfficer hdbOfficer = login();
                    if (hdbOfficer != null) {
                        return hdbOfficer; // Return immediately on successful login
                    }
                }
                case 2 -> forgetPassword();
                case 3 -> {
                    exitToMenu();
                    return null; // return null to exit
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }

        }
    }

    public HdbOfficer login() {
        try {
            printDivider();
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!\n");
            }

            //2.checks if nric is in database
            String officerId = officerRepo.generateID(nric);  // generate hdbOfficer ID (OF-last4)
            HdbOfficer hdbOfficer = officerRepo.getUser(officerId);
            if (hdbOfficer == null) {
                throw new IllegalArgumentException("NRIC not found!\n");
            }

            // 3.check if password is correct
            System.out.print("Enter Password: ");
            String password = InputUtils.nextLine().trim();
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
            String nric = InputUtils.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!");
            }

            //2.checks if nric is in database
            String officerId = officerRepo.generateID(nric); // generate hdbOfficer ID (OF-last4)
            HdbOfficer hdbOfficer = officerRepo.getUser(officerId);
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
        System.out.println();
        System.out.printf("OFFICER DASHBOARD" +
                        "\n---------------------\n" +
                        "Name: %s | Marital status: %s | Age: %d\n",
                hdbOfficer.getName(),
                hdbOfficer.getMaritalStatus(),
                hdbOfficer.getAge());

        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Please choose an option:");
            System.out.println("1. Residential Application Menu");
            System.out.println("2. Team Application Menu");
            System.out.println("3. Assigned Project Menu");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");


            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {//view residential menu
                    officerController.displayResidentialMenu(hdbOfficer);
                }
                case 2 ->{//view TeamApplication menu
                    officerController.displayTeamApplicationMenu(hdbOfficer);
                }
                case 3-> {//view assigned project
                    officerController.displayAssignedProjectMenu(hdbOfficer);
                }
                case 4-> {//exit
                    officerController.exitPortal();
                    printDivider();
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-4.\n");
            }

        }

    }


    public void displayAssignedProjectMenu(HdbOfficer officer){

    }



}//end of class
