package Enquiry;
import java.util.*;
import Applicant.*;
import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.*;
import Utility.InputUtils;

/**
 * EnquiryUI class that provides the user interface for applicants to interact with their enquiries.
 * It allows creating, viewing, editing, and deleting enquiries.
 */
public class EnquiryUI {
    private final EnquiryRepo enquiryRepo;
    private final ProjectControllerInterface projectController;

    /**
     * Constructor to initialize EnquiryUI with the required repository and project controller.
     * @param enquiryRepo The enquiry repository used to manage enquiries.
     * @param projectController The project controller used to manage project-related operations.
     */
    public EnquiryUI(EnquiryRepo enquiryRepo,
                     ProjectControllerInterface projectController) {
        this.enquiryRepo = enquiryRepo;
        this.projectController = projectController;
    }

    /**
     * Displays the applicant menu where they can choose different enquiry-related options.
     * @param user The applicant using the system.
     */
    public void showApplicantMenu(Applicant user) {
        while(true) {
            System.out.println("\n======= Enquiry Menu =======");
            System.out.println("1. Create New Enquiry");
            System.out.println("2. View My Enquiries");
            System.out.println("3. Edit Enquiry");
            System.out.println("4. Delete Enquiry");
            System.out.println("5. Back");
            System.out.print("Enter your choice (1-5): ");

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
                    System.out.println("Invalid choice! Please enter 1-5.\n");
            }
        }
    }

    /**
     * Allows the applicant to edit an existing enquiry.
     * The applicant can update the message of their enquiry if it is in "PENDING" status.
     * @param user The applicant who wants to edit their enquiry.
     */
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

    /**
     * Allows the applicant to delete an existing enquiry.
     * The applicant can only delete enquiries they have created.
     * @param user The applicant who wants to delete their enquiry.
     */
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

    /**
     * Allows the applicant to create a new enquiry.
     * The applicant provides a project name, title, and message for the enquiry.
     * @param user The applicant who is creating the enquiry.
     */
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

    /**
     * Displays the list of enquiries created by the applicant.
     * This includes both pending and processed enquiries with responses if available.
     * @param user The applicant whose enquiries are being viewed.
     */
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

    // Officer

    /**
     * Displays the list of enquiries related to the project assigned to the officer.
     * The officer can view both pending and processed enquiries. It displays the enquiries
     * based on the status (PENDING or ANSWERED).
     * @param user The officer viewing the project enquiries.
     */
    private void viewProjectEnquiries(HdbOfficer user) {
        if(user.hasAssignedProject() == false) {
            System.out.println("The Officer does not have assigned project!");
            return;
        }
        List<String> enquiryIds = enquiryRepo.getEnquiriesByProject(user.getAssignedProjectName());
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

    /**
     * Displays the officer menu and allows the officer to either respond to an enquiry
     * or return to the previous screen.
     * @param user The officer who is interacting with the enquiry menu.
     */
    public void showOfficerMenu(HdbOfficer user) {
        viewProjectEnquiries(user);
        while(true) {
            System.out.println("\n======= Enquiry Menu =======");
            System.out.println("1. Respond to Enquiry");
            System.out.println("2. Back");
            System.out.print("Enter your choice (1-2): ");

            int choice = InputUtils.readInt();

            switch(choice) {
                case 1:
                    respondToEnquiry(user);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice! Please enter 1-2.\n");
            }
        }
    }

    /**
     * Allows the officer to respond to a specific enquiry.
     * The officer can only respond to enquiries under their assigned project.
     * @param user The officer responding to the enquiry.
     */
    private void respondToEnquiry(HdbOfficer user) {
        System.out.print("Enter enquiry ID to respond: ");
        String id = InputUtils.nextLine();
        Enquiry e = enquiryRepo.getEnquiry(id);

        if(e == null || !e.getProjectName().equals(user.getAssignedProjectName())) {
            System.out.println("Enquiry not found or not under your project!");
            return;
        }

        System.out.print("Enter your response: ");
        String response = InputUtils.nextLine();
        e.respond(response);
        enquiryRepo.saveFile();
        System.out.println("Response submitted successfully!");
    }

    /**
     * Displays the manager menu where the manager can view project enquiries
     * or respond to an enquiry.
     * @param user The manager who is interacting with the enquiry menu.
     */
    public void showManagerMenu(HdbManager user) {
        while(true) {
            System.out.println("\n======= Enquiry Menu =======");
            System.out.println("1. View Project Enquiries");
            System.out.println("2. Respond to Enquiry");
            System.out.println("3. Back");
            System.out.print("Enter your choice (1-3): ");

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
                    System.out.println("Invalid choice! Please enter 1-3.\n");
            }
        }
    }

    /**
     * Displays the list of enquiries related to the project managed by the manager.
     * The manager can view both pending and processed enquiries. It displays the enquiries
     * based on the status (PENDING or ANSWERED).
     * @param user The manager viewing the project enquiries.
     */
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

    /**
     * Allows the manager to respond to a specific enquiry.
     * The manager can only respond to enquiries related to their managed project.
     * @param user The manager responding to the enquiry.
     */
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