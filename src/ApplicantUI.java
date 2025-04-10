import java.util.Scanner;
import java.util.regex.Pattern;

public final class ApplicantUI implements UserUI<Applicant>{
    private static ApplicantController controller;
    private static ApplicantRepo repo;

    public ApplicantUI(ApplicantController applicantController, ApplicantRepo applicantRepo){
        controller = applicantController;
        repo = applicantRepo;
    }

    public Applicant displayLogin(){

        while (true) {
            System.out.println();
            printDivider();
            System.out.println("\nApplicant Portal:");
            System.out.println("--------------------------");
            System.out.println("Please choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Forget Password");
            System.out.println("3. Back to Menu");
            System.out.print("Enter your choice (1-3): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {
                    Applicant applicant = login();
                    if (applicant != null) {
                        return applicant; // Return immediately on successful login
                    }
                }
                case 2 -> forgetPassword();
                case 3 -> {
                    exitToMenu();
                    return null; // Explicitly return null on exit
                }
                default -> System.out.println("Invalid choice! Please enter 1-3.\n");
            }

        }
    }

    public Applicant login(){
        try {
            printDivider();
            System.out.print("Enter NRIC: ");

            String nric = InputUtils.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!\n");
            }

            //2.checks if nric is in database
            String applicantId = "AP-" + nric.substring(5);  // generate applicant ID (AP-last4)
            Applicant applicant = repo.getUser(applicantId);
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

    public void forgetPassword() {
        try {
            System.out.print("Enter NRIC: ");
            String nric = InputUtils.nextLine().trim().toUpperCase();
            //1.check if nric is valid format
            if (!Pattern.matches("^[STFG]\\d{7}[A-Z]$", nric)) {
                throw new IllegalArgumentException("Invalid NRIC format!");
            }

            //2.checks if nric is in database
            String applicantId = "AP-" + nric.substring(5);  // generate applicant ID (AP-last4)
            Applicant applicant = repo.getUser(applicantId);
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

    public void displayDashboard(Applicant applicant){
        printDivider();
        System.out.println();
        System.out.printf("APPLICANT DASHBOARD" +
                        "\n---------------------\n" +
                        "Name: %s | Marital status: %s | Age: %d\n",
                applicant.getName(),
                applicant.getMaritalStatus(),
                applicant.getAge());

        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Please choose an option:");
            System.out.println("1. View my application");
            System.out.println("2. View current BTO projects");
            System.out.println("3. View my enquiries");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 1 -> {//view application menu
                    displayApplicationMenu(applicant);
                }
                case 2 ->{//view current BTO projects
                    controller.viewCurrentProjects(applicant);
                }
                case 3 -> {//view my enquiries
                    //xxxxxxxxxxxxxxxxxxxxx
                }
                case 4 -> {//exit
                    controller.exitPortal();    //saves changes to file
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1-4.\n");
            }

        }
    }



    public void displayApplicationMenu(Applicant applicant){
        printDivider();
        System.out.println();
        if (applicant.getResidentialApplication() == null){
            System.out.println("You do not have an active application.");
        }else{
            System.out.println("Applied to: " + applicant.getResidentialApplication().getProjectName());
            System.out.println("Flat type: " + applicant.getResidentialApplication().getFlatType());
            System.out.println("Application Status: " + applicant.getResidentialApplication().getStatus());
        }
    }

}//end of class
