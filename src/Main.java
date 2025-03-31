import java.util.*;

public class Main {
    public static void main(String[] args) {
        //TEST MANAGER
        HdbManagerController managerController = new HdbManagerController();
        HdbManager manager1 = managerController.getHdbManager("MA-901G");
        System.out.println("manager1 is" + manager1.getName());
        managerController.addHdbManager("testerManager","T0888888F",20, User.MaritalStatus.SINGLE, "PASSWORD123");
        managerController.saveHdbManagers();
        //TEST APPLICANT
        ApplicantController applicantController = new ApplicantController();
        Applicant applicant1 = applicantController.getApplicant("AP-543C");
        System.out.println("applicant1 is" + applicant1.getName());
        applicantController.addApplicant("testerApplicant","T0888838F",20, User.MaritalStatus.SINGLE, "PASSWORD123");
        applicantController.saveApplicants();
        //TEST OFFICER
        HdbOfficerController officerController = new HdbOfficerController();
        HdbOfficer officer1 = officerController.getHdbOfficer("OF-567J");
        System.out.println("officer1 is" + officer1.getName());
        officerController.addHdbOfficer("testerOfficer","T0333333F",20, User.MaritalStatus.SINGLE, "PASSWORD123");
        officerController.saveHdbOfficers();


        //KC's main
        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController("ApplicantList.csv");
        userController.listUsers(); //For debugging

        printWelcomeMessage();

        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    userController.login();
                    break;
                case "2":
                    userController.register();
                    break;
                case "3":
                    userController.forgotPassword();
                    break;
                case "4":
                    System.out.println("Exiting... Thank you for using BMS!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please enter 1, 2, 3, or 4.");
            }
        }
    }

    public static void printWelcomeMessage() {
        System.out.println("______  _______  ______");
        System.out.println("(____  \\(_______)/ _____)");
        System.out.println("____)  )_  _  _( (____  ");
        System.out.println("|  __  (| ||_|| |\\____ \\ ");
        System.out.println("| |__)  ) |   | |_____) )");
        System.out.println("|______/|_|   |_(______/ ");
        System.out.println("Welcome to the BTO Management System!\n");


    }
}