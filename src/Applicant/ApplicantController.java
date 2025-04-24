package Applicant;

import Application.Residential.ResidentialApplicationController;
import Enquiry.EnquiryController;
import Report.ReportController;
import Users.*;
import Project.*;

/**
 * The ApplicantController handles the business logic for the applicant portal.
 * It acts as a mediator between the user interface (ApplicantUI) and various controllers
 * for managing residential applications, enquiries, projects, and reports.
 */
public class ApplicantController implements UserController {

    // Dependencies
    private final ApplicantUI applicantUI;
    private final ApplicantRepo applicantRepo;

    // Controller dependencies
    private final ProjectControllerInterface projectController;
    private final ResidentialApplicationController resAppController;
    private final EnquiryController enquiryController;
    private final ReportController reportController;

    /**
     * Constructs an ApplicantController with the necessary controller dependencies.
     *
     * @param resAppController The controller responsible for managing residential applications.
     * @param projectController The controller responsible for managing projects.
     * @param enquiryController The controller responsible for managing enquiries.
     * @param reportController The controller responsible for managing reports.
     */
    public ApplicantController(ResidentialApplicationController resAppController,
                               ProjectControllerInterface projectController,
                               EnquiryController enquiryController,
                               ReportController reportController) {

        this.resAppController = resAppController;
        this.projectController = projectController;
        this.enquiryController = enquiryController;
        this.reportController = reportController;

        // Initializes the applicant repository and user interface
        applicantRepo = new ApplicantRepo(resAppController.getRepo());
        applicantUI = new ApplicantUI(this);
    }

    /**
     * Runs the applicant portal by displaying the login interface and dashboard.
     * If the login is successful, it proceeds to display the dashboard.
     */
    public void runPortal() {
        // Welcome menu display
        Applicant currentUser = applicantUI.displayLogin(applicantRepo);
        if (currentUser == null) {
            return; // Exit if login is unsuccessful or user exits the portal
        }
        // Display the dashboard for the current logged-in user
        applicantUI.displayDashboard(currentUser);
    }

    /**
     * Displays the current projects that the applicant can view.
     *
     * @param applicant The applicant who is viewing the projects.
     */
    public void viewCurrentProjects(Applicant applicant) {
        projectController.displayProjectDashboard(applicant, resAppController);
    }

    /**
     * Displays the application menu for the applicant to apply for a residential unit.
     *
     * @param applicant The applicant who is accessing the application menu.
     */
    public void displayApplicationMenu(Applicant applicant) {
        resAppController.displayApplicationMenu(applicant);
    }

    /**
     * Displays the enquiry menu for the applicant to inquire about projects or applications.
     *
     * @param applicant The applicant who is accessing the enquiry menu.
     */
    public void displayEnquiryMenu(Applicant applicant) {
        enquiryController.showApplicantMenu(applicant);
    }

    /**
     * Displays the report menu for the applicant to view reports.
     *
     * @param applicant The applicant who is accessing the report menu.
     */
    public void displayReports(Applicant applicant) {
        reportController.displayApplicantReports(applicant);
    }

    /**
     * Saves the applicant data to a file (e.g., ApplicantList.csv).
     */
    public void saveFile() {
        applicantRepo.saveFile(); // Saves the applicant data
    }

    /**
     * Gets the repository of applicants.
     *
     * @return The applicant repository.
     */
    public ApplicantRepo getApplicantRepo() {
        return applicantRepo;
    }
}