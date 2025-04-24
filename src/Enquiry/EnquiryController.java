package Enquiry;

import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Applicant.*;
import Project.ProjectControllerInterface;

/**
 * Controller class responsible for managing enquiries in the system.
 * It handles the creation of new enquiries, saving data, and providing the necessary UI interactions
 * for different user roles such as applicants, managers, and officers.
 */
public class EnquiryController {
    private EnquiryRepo enquiryRepo;
    private EnquiryUI enquiryUI;

    /**
     * Constructs an EnquiryController with the given ProjectControllerInterface.
     * Initializes the enquiry repository and UI for managing enquiries.
     *
     * @param projectController The project controller interface to interact with project data
     */
    public EnquiryController(ProjectControllerInterface projectController) {
        enquiryRepo = new EnquiryRepo();
        enquiryUI = new EnquiryUI(enquiryRepo, projectController);
    }

    /**
     * Adds a new enquiry to the repository with the given details.
     *
     * @param projectId  The ID of the project the enquiry is related to
     * @param authorId   The ID of the person making the enquiry
     * @param title      The title of the enquiry
     * @param message    The content of the enquiry message
     */
    public void addEnquiry(String projectId, String authorId, String title, String message) {
        String enquiryId = enquiryRepo.generateId();
        enquiryRepo.addEnquiry(new Enquiry(enquiryId, projectId, authorId, title, message));
    }

    // Getter methods

    /**
     * Retrieves the enquiry with the specified ID.
     *
     * @param enquiryId The ID of the enquiry to retrieve
     * @return The Enquiry object with the given ID, or null if not found
     */
    public Enquiry getEnquiry(String enquiryId) {
        return enquiryRepo.getEnquiry(enquiryId);
    }

    /**
     * Retrieves the enquiry repository associated with this controller.
     *
     * @return The EnquiryRepo object
     */
    public EnquiryRepo getRepo() {
        return enquiryRepo;
    }

    /**
     * Saves any changes made to the enquiry repository to the file.
     */
    public void saveChanges() {
        enquiryRepo.saveFile();
    }

    // Menu display methods for different user roles

    /**
     * Displays the applicant menu.
     *
     * @param user The applicant for whom the menu is to be displayed
     */
    public void showApplicantMenu(Applicant user) {
        enquiryUI.showApplicantMenu(user);
    }

    /**
     * Displays the manager menu.
     *
     * @param user The manager for whom the menu is to be displayed
     */
    public void showManagerMenu(HdbManager user) {
        enquiryUI.showManagerMenu(user);
    }

    /**
     * Displays the officer menu.
     *
     * @param user The officer for whom the menu is to be displayed
     */
    public void showOfficerMenu(HdbOfficer user) {
        enquiryUI.showOfficerMenu(user);
    }
}
