package Enquiry;
import java.util.*;

public class EnquiryUI {
    private static EnquiryRepo enquiryRepo = new EnquiryRepo();
    private static Scanner scanner = new Scanner(System.in);
    private static String currentUserId;
    private static String currentProjectId;

    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        while(true) {
            System.out.println("\n=== Enquiry System ===");
            System.out.println("1. Manager Menu");
            System.out.println("2. Applicant Menu");
            System.out.println("3. Exit");
            System.out.print("Select user type: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch(choice) {
                case 1:
                    System.out.print("Enter manager ID: ");
                    currentUserId = scanner.nextLine();
                    showApplicantMenu();
                    break;
                case 2:
                    System.out.print("Enter applicant ID: ");
                    currentUserId = scanner.nextLine();
                    System.out.print("Enter project ID you're managing: ");
                    currentProjectId = scanner.nextLine();
                    showManagerMenu();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void showApplicantMenu() {
        while(true) {
            System.out.println("\n=== Enquiry Menu ===");
            System.out.println("1. Create New Enquiry");
            System.out.println("2. View My Enquiries");
            System.out.println("3. Edit Enquiry");
            System.out.println("4. Delete Enquiry");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    createEnquiry();
                    break;
                case 2:
                    viewMyEnquiries();
                    break;
                case 3:
                    editEnquiry();
                    break;
                case 4:
                    deleteEnquiry();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void showManagerMenu() {
        while(true) {
            System.out.println("\n=== Enquiry Menu ===");
            System.out.println("1. View Project Enquiries");
            System.out.println("2. Respond to Enquiry");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    viewProjectEnquiries();
                    break;
                case 2:
                    respondToEnquiry();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void createEnquiry() {
        System.out.println("\n--- Create New Enquiry ---");
        System.out.print("Enter project ID: ");
        String projectId = scanner.nextLine();
        System.out.print("Enter enquiry title: ");
        String title = scanner.nextLine();
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();

        String enquiryId = enquiryRepo.generateId();
        Enquiry newEnquiry = new Enquiry(enquiryId, projectId, currentUserId, title, message);
        enquiryRepo.addEnquiry(newEnquiry);
        enquiryRepo.saveFile();
        
        System.out.println("Enquiry created successfully! ID: " + enquiryId);
    }

    private static void viewMyEnquiries() {
        List<String> enquiryIds = enquiryRepo.getEnquiriesByAuthor(currentUserId);
        if(enquiryIds.isEmpty()) {
            System.out.println("No enquiries found!");
            return;
        }

        System.out.println("\n--- Your Enquiries ---");
        for(String id : enquiryIds) {
            Enquiry e = enquiryRepo.getEnquiry(id);
            System.out.printf("[%s] %s - %s\n", 
                e.getStatus(), 
                e.getTitle(),
                e.getMessage().substring(0, Math.min(20, e.getMessage().length())) + "...");
        }
        
        System.out.print("\nEnter enquiry ID to view details (or 'back' to return): ");
        String input = scanner.nextLine();
        if(!input.equalsIgnoreCase("back")) {
            viewEnquiryDetails(input);
        }
    }

    private static void viewProjectEnquiries() {
        List<String> enquiryIds = enquiryRepo.getEnquiriesByProject(currentProjectId);
        if(enquiryIds.isEmpty()) {
            System.out.println("No enquiries found for this project!");
            return;
        }

        System.out.println("\n--- Project Enquiries ---");
        for(String id : enquiryIds) {
            Enquiry e = enquiryRepo.getEnquiry(id);
            System.out.printf("[%s] %s - %s\n", 
                e.getStatus(), 
                e.getTitle(),
                e.getAuthorId());
        }
        
        System.out.print("\nEnter enquiry ID to view details (or 'back' to return): ");
        String input = scanner.nextLine();
        if(!input.equalsIgnoreCase("back")) {
            viewEnquiryDetails(input);
        }
    }

    private static void viewEnquiryDetails(String enquiryId) {
        Enquiry e = enquiryRepo.getEnquiry(enquiryId);
        if(e == null) {
            System.out.println("Enquiry not found!");
            return;
        }

        System.out.println("\n--- Enquiry Details ---");
        System.out.println("ID: " + e.getId());
        System.out.println("Project: " + e.getProjectId());
        System.out.println("Title: " + e.getTitle());
        System.out.println("Message: " + e.getMessage());
        System.out.println("Status: " + e.getStatus());
        if(e.getStatus() == EnquiryStatus.ANSWERED) {
            System.out.println("Response: " + e.getResponse());
        }
    }

    private static void editEnquiry() {
        System.out.print("Enter enquiry ID to edit: ");
        String id = scanner.nextLine();
        Enquiry e = enquiryRepo.getEnquiry(id);
        
        if(e == null || !e.getAuthorId().equals(currentUserId)) {
            System.out.println("Enquiry not found or not authorized!");
            return;
        }

        if(e.getStatus() != EnquiryStatus.PENDING) {
            System.out.println("Cannot edit answered or processed enquiries!");
            return;
        }

        System.out.print("Enter new message (press enter to keep current): ");
        String newMessage = scanner.nextLine();
        if(!newMessage.isEmpty()) {
            e.setMessage(newMessage);
            enquiryRepo.saveFile();
            System.out.println("Enquiry updated successfully!");
        }
    }

    private static void respondToEnquiry() {
        System.out.print("Enter enquiry ID to respond: ");
        String id = scanner.nextLine();
        Enquiry e = enquiryRepo.getEnquiry(id);
        
        if(e == null || !e.getProjectId().equals(currentProjectId)) {
            System.out.println("Enquiry not found or not under your project!");
            return;
        }

        System.out.print("Enter your response: ");
        String response = scanner.nextLine();
        e.respond(response);
        enquiryRepo.saveFile();
        System.out.println("Response submitted successfully!");
    }

    private static void deleteEnquiry() {
        System.out.print("Enter enquiry ID to delete: ");
        String id = scanner.nextLine();
        Enquiry e = enquiryRepo.getEnquiry(id);
        
        if(e == null || !e.getAuthorId().equals(currentUserId)) {
            System.out.println("Enquiry not found or not authorized!");
            return;
        }

        enquiryRepo.getEnquiries().remove(id);
        enquiryRepo.saveFile();
        System.out.println("Enquiry deleted successfully!");
    }
}