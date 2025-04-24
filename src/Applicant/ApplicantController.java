package Applicant;

import Application.Residential.ResidentialApplicationController;
import Enquiry.EnquiryController;
import Users.*;
import Project.*;

/**
 * Controller class responsible for coordinating applicant-related operations,
 * including UI display, application handling, project viewing, and enquiries.
 * Implements the UserController interface.
 */
public class ApplicantController implements UserController {

    // Dependencies
    /** User interface handler for applicant interactions. */
    private final ApplicantUI applicantUI;

    /** Repository for managing applicant data. */
    private final ApplicantRepo applicantRepo;

    // Controller dependencies
    /** Controller for handling project-related operations. */
    private final ProjectControllerInterface projectController;

    /** Controller for handling residential application operations. */
    private final ResidentialApplicationController resAppController;

    /** Controller for managing applicant enquiries. */
    private final EnquiryController enquiryController;

    /**
     * Constructs an ApplicantController with required controllers.
     *
     * @param resAppController Residential application controller
     * @param projectController Controller for project management
     * @param enquiryController Controller for enquiry handling
     */
    public ApplicantController(ResidentialApplicationController resAppController,
                               ProjectControllerInterface projectController,
                               EnquiryController enquiryController) {

        this.resAppController = resAppController;
        this.projectController = projectController;
        this.enquiryController = enquiryController;

        applicantRepo = new ApplicantRepo(resAppController.getRepo());
        applicantUI = new ApplicantUI(this);
    }

    /**
     * Starts the applicant portal, handles login and displays the dashboard.
     */
    public void runPortal() {
        // welcome menu display
        Applicant currentUser = applicantUI.displayLogin(applicantRepo);
        if (currentUser == null){ return; } // if returns null, user exits program
        applicantUI.displayDashboard(currentUser);
    }

    /**
     * Displays available projects for the given applicant.
     *
     * @param applicant The currently logged-in applicant
     */
    public void viewCurrentProjects(Applicant applicant){
        projectController.displayProjectDashboard(applicant, resAppController);
    }

    /**
     * Displays the application-related menu for the applicant.
     *
     * @param applicant The applicant using the system
     */
    public void displayApplicationMenu(Applicant applicant){
        resAppController.displayApplicationMenu(applicant);
    }

    /**
     * Displays the enquiry menu for the applicant.
     *
     * @param applicant The applicant user
     */
    public void displayEnquiryMenu(Applicant applicant) {
        enquiryController.showApplicantMenu(applicant);
    }

    /**
     * Saves the applicant data to file.
     */
    public void saveFile(){
        applicantRepo.saveFile(); // saves ApplicantList.csv file
    }

    /**
     * Retrieves the applicant repository.
     *
     * @return the applicant repository instance
     */
    public ApplicantRepo getApplicantRepo(){
        return applicantRepo;
    }

} // end of class
