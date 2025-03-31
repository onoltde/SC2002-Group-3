import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public UserManager(String filePath) {
        loadUsersFromFile(filePath);
    }

    private void loadUsersFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("User file not found. Starting with an empty user list.");
            return;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine();    
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 4) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                String name = parts[0].trim();
                String nric = parts[1].trim();
                int age = Integer.parseInt(parts[2].trim());
                MaritalStatus maritalStatus;
                try {
                    maritalStatus = MaritalStatus.valueOf(parts[3].trim().toUpperCase()); 
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid marital status on line: " + line);
                    continue; 
                }
    
                users.add(new User(name, nric, age, maritalStatus));
            }
            System.out.println("For debugging only: Users loaded successfully from file.");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
    }
    //Comment this out after debugging
    public void listUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users) {
                System.out.println(user);
            }
        }
    }
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

        MaritalStatus maritalStatus = getMaritalStatusInput(); 

        users.add(new User(name, nric, age, maritalStatus));
        System.out.println("Registration successful! Your default password is 'password'.");
    }

    private boolean isValidNric(String nric) {
        return nric.matches("^[ST]\\d{7}[A-Z]$");
    }

    private MaritalStatus getMaritalStatusInput() {
        while (true) {
            System.out.println("Select Marital Status:");
            for (MaritalStatus status : MaritalStatus.values()) {
                System.out.println("- " + status);
            }
            System.out.print("Enter marital status (SINGLE/MARRIED): ");
            String input = scanner.nextLine().toUpperCase();

            try {
                return MaritalStatus.valueOf(input);
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
