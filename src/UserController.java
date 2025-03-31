import java.util.*;
import java.io.*;

public class UserController {
    private List<User> users = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public UserController() {
        //removed load from file method because user will have no database --> pull data from ApplicantList/OfficerList etc.
        //delete this
    }

    public User addUser

    public void login() {
        System.out.print("Enter NRIC: ");
        String nric = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getNric().equals(nric) && user.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome, " + user.getNric());
                return;
            }
        }
        System.out.println("Invalid NRIC or password. Please try again.");
    }

    public void register() {
        //register method needs changing based on what type of user it is -> can use polymorphism and call overloaded constructor to create new user type
        //delete this
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        String nric;
        while (true) {
            System.out.print("Enter NRIC: ");
            nric = scanner.nextLine().toUpperCase();
            if (isValidNric(nric)) {
                break;
            }
            System.out.println("Invalid NRIC format! NRIC must start with S/T, followed by 7 digits, and end with a letter.");
        }

        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        User.MaritalStatus maritalStatus = getMaritalStatusInput();

        users.add(new User());
        System.out.println("Registration successful! Your default password is 'password'.");
    }

    private boolean isValidNric(String nric) {
        return nric.matches("^[ST]\\d{7}[A-Z]$");
    }

    private User.MaritalStatus getMaritalStatusInput() {
        while (true) {
            System.out.println("Select Marital Status:");
            for (User.MaritalStatus status : User.MaritalStatus.values()) {
                System.out.println("- " + status);
            }
            System.out.print("Enter marital status (SINGLE/MARRIED): ");
            String input = scanner.nextLine().toUpperCase();

            try {
                return User.MaritalStatus.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Please enter 'SINGLE' or 'MARRIED'.");
            }
        }
    }

    public void forgotPassword() {
        System.out.print("Enter NRIC: ");
        String nric = scanner.nextLine();

        for (User user : users) {
            if (user.getNric().equals(nric)) {
                System.out.println("Password reset successful. Your new password is 'password'.");
                user.changePassword("password");
                return;
            }
        }
        System.out.println("User not found.");
    }
}