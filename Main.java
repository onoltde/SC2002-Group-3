import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager("ApplicantList.csv");
        userManager.listUsers();

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
                    userManager.login(); 
                    break;
                case "2":
                    userManager.register(); 
                    break;
                case "3":
                    userManager.forgotPassword(); 
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
