package Enquiry;
import java.util.*;
import Applicant.*;
import HdbManager.HdbManager;
import Project.*;
import Utility.InputUtils;


public class EnquiryUI {
    private final EnquiryRepo enquiryRepo;
    private final ProjectControllerInterface projectController;

    public EnquiryUI(EnquiryRepo enquiryRepo,
                     ProjectControllerInterface projectController) {
        this.enquiryRepo = enquiryRepo;
        this.projectController = projectController;
    }

    public void showApplicantMenu(Applicant user) {
        while(true) {
            System.out.println("\n======= Enquiry Menu =======");
            System.out.println("1. Create New Enquiry");
            System.out.println("2. View My Enquiries");
            System.out.println("3. Edit Enquiry");
            System.out.println("4. Delete Enquiry");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            int choice = InputUtils.readInt();

            switch(choice) {
                case 1:
                    createEnquiry(user);
                    break;
                case 2:
                    viewMyEnquiries(user);
                    break;
                case 3:
                    editEnquiry(user);
                    break;
                case 4:
                    deleteEnquiry(user);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void editEnquiry(Applicant user) {
        System.out.print("Enter enquiry ID to edit: ");
        String id = InputUtils.nextLine();
        Enquiry e = enquiryRepo.getEnquiry(id);

        if(e == null || !e.getAuthorId().equals(user.getId())) {
            System.out.println("Enquiry not found or not authorized!");
            return;
        }

        if(e.getStatus() != Enquiry.Status.PENDING) {
            System.out.println("Cannot edit answered or processed enquiries!");
            return;
        }

        System.out.print("Enter new message (press enter to keep current): ");
        String newMessage = InputUtils.nextLine();
        if(!newMessage.isEmpty()) {
            e.setMessage(newMessage);
            enquiryRepo.saveFile();
            System.out.println("Enquiry updated successfully!");
        }
    }

    private void deleteEnquiry(Applicant user) {
        System.out.print("Enter enquiry ID to delete: ");
        String id = InputUtils.nextLine();
        Enquiry e = enquiryRepo.getEnquiry(id);

        if(e == null || !e.getAuthorId().equals(user.getId())) {
            System.out.println("Enquiry not found or not authorized!");
            return;
        }

        enquiryRepo.getEnquiries().remove(id);
        enquiryRepo.saveFile();
        System.out.println("Enquiry deleted successfully!");
    }

    private void createEnquiry(Applicant user) {
        System.out.println("\n--- Create New Enquiry ---");
        System.out.print("Enter project name: ");
        String projectName = InputUtils.nextLine();
        if(projectController.getRepo().getProject(projectName) == null) {
            System.out.println("No such project!");
            return;
        }
        System.out.print("Enter enquiry title: ");
        String title = InputUtils.nextLine();
        System.out.print("Enter your message: ");
        String message = InputUtils.nextLine();

        String enquiryId = enquiryRepo.generateId();
        Enquiry newEnquiry = new Enquiry(enquiryId, projectName, user.getId(), title, message);
        enquiryRepo.addEnquiry(newEnquiry);
        enquiryRepo.saveFile();

        System.out.println("Enquiry created successfully! ID: " + enquiryId);
    }

    private void viewMyEnquiries(Applicant user) {
        List<String> enquiryIds = enquiryRepo.getEnquiriesByAuthor(user.getId());
        if(enquiryIds.isEmpty()) {
            System.out.println("No enquiries found!");
            return;
        }

        System.out.println("\n------- Your Enquiries -------");
        for(String id : enquiryIds) {
            Enquiry e = enquiryRepo.getEnquiry(id);
            if(e.getStatus() == Enquiry.Status.PENDING) {
                System.out.printf("[%s] %s\n%s\n%s\n-----------------------------\n",
                        e.getStatus(),
                        e.getTitle(),
                        e.getMessage(),
                        e.getId()
                );
            } else {
                System.out.printf("[%s] %s\n%s\n\nResponse: %s\n%s\n-----------------------------\n",
                        e.getStatus(),
                        e.getTitle(),
                        e.getMessage(),
                        (e.getResponse() == null ? "None response yet." : e.getResponse()),
                        e.getId()
                );
            }
        }
    }

    public void showManagerMenu(HdbManager user) {
        while(true) {
            System.out.println("\n======= Enquiry Menu =======");
            System.out.println("1. View Project Enquiries");
            System.out.println("2. Respond to Enquiry");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");

            int choice = InputUtils.readInt();


            switch(choice) {
                case 1:
                    viewProjectEnquiries(user);
                    break;
                case 2:
                    respondToEnquiry(user);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void viewProjectEnquiries(HdbManager user) {
        if(user.getManagedProject() == null) {
            System.out.println("The manager does not have managed project!");
            return;
        }
        List<String> enquiryIds = enquiryRepo.getEnquiriesByProject(user.getManagedProject().getName());
        if(enquiryIds.isEmpty()) {
            System.out.println("No enquiries found for this project!");
            return;
        }

        System.out.println("\n------- Project Enquiries -------");
        for(String id : enquiryIds) {
            Enquiry e = enquiryRepo.getEnquiry(id);
            if(e.getStatus() == Enquiry.Status.ANSWERED) continue;
            System.out.printf("By: %s\n[%s] %s\n%s\n%s\n-----------------------------\n",
                    e.getAuthorId(),
                    e.getStatus(),
                    e.getTitle(),
                    e.getMessage(),
                    e.getId()
            );
        }

        for(String id : enquiryIds) {
            Enquiry e = enquiryRepo.getEnquiry(id);
            if(e.getStatus() == Enquiry.Status.PENDING) continue;
            System.out.printf("By: %s\n[%s] %s\n%s\n\nResponse: %s\n%s\n-----------------------------\n",
                    e.getAuthorId(),
                    e.getStatus(),
                    e.getTitle(),
                    e.getMessage(),
                    (e.getResponse() == null ? "Null." : e.getResponse()),
                    e.getId()
            );
        }
    }

    private void respondToEnquiry(HdbManager user) {
        System.out.print("Enter enquiry ID to respond: ");
        String id = InputUtils.nextLine();
        Enquiry e = enquiryRepo.getEnquiry(id);

        if(e == null || !e.getProjectName().equals(user.getManagedProject().getName())) {
            System.out.println("Enquiry not found or not under your project!");
            return;
        }

        System.out.print("Enter your response: ");
        String response = InputUtils.nextLine();
        e.respond(response);
        enquiryRepo.saveFile();
        System.out.println("Response submitted successfully!");
    }
}