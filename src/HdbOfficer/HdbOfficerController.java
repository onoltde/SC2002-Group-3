package HdbOfficer;

import Applicant.Applicant;
import Application.Application;
import Application.ResidentialApplicationControllerInterface;
import Application.Team.*;
import Enquiry.EnquiryController;
import HdbManager.HdbManager;
import Application.Residential.*;
import Application.TeamApplicationControllerInterface;
import Project.Flat;
import Project.Project;
import Project.ProjectControllerInterface;
import Report.ReportController;
import Applicant.ApplicantController;
import Users.*;

/**
 * Controller for managing HDB Officer functionalities.
 * Handles the interaction between HDB Officer UI and other components such as project, application, enquiry, and report management.
 */
public class HdbOfficerController implements UserController {

    // Dependencies
    private final HdbOfficerRepo officerRepo;
    private final HdbOfficerUI officerUI;

    // Controller dependencies
    private final ProjectControllerInterface projectController;
    private final TeamApplicationController teamAppController;
    private final ResidentialApplicationController resAppController;
    private final EnquiryController enquiryController;
    private final ReportController reportController;
    private final ApplicantController applicantController;

    /**
     * Constructs an HDB Officer Controller with the necessary dependencies.
     *
     * @param projectController       The controller for managing project-related operations.
     * @param resAppController        The controller for handling residential applications.
     * @param teamAppController       The controller for handling team applications.
     * @param enquiryController       The controller for managing enquiries.
     * @param reportController        The controller for generating and managing reports.
     * @param applicantController     The controller for handling applicant-related operations.
     */
    public HdbOfficerController(ProjectControllerInterface projectController, ResidentialApplicationController resAppController,
                                TeamApplicationController teamAppController, EnquiryController enquiryController,
                                ReportController reportController, ApplicantController applicantController) {
        this.projectController = projectController;
        this.resAppController = resAppController;
        this.teamAppController = teamAppController;
        this.enquiryController = enquiryController;
        this.reportController = reportController;
        this.applicantController = applicantController;
        officerRepo = new HdbOfficerRepo(resAppController.getRepo(), teamAppController.getRepo());
        officerUI = new HdbOfficerUI(this);
    }

    /**
     * Runs the portal, displaying the login UI and handling the HDB Officer's actions.
     */
    public void runPortal() {
        // Welcome menu display
        HdbOfficer currentUser = officerUI.displayLogin(officerRepo);
        if (currentUser == null) {
            return;  // If null, user exits the program
        }
        officerUI.displayDashboard(currentUser);
    }

    /**
     * Saves the current state of the officer repository.
     */
    public void saveFile() {
        officerRepo.saveFile();
    }

    /**
     * Displays the residential application menu and adds the application to the officer's records when applicable.
     *
     * @param hdbOfficer The officer who is applying for residential applications.
     */
    public void displayResidentialMenu(HdbOfficer hdbOfficer) {
        ResidentialApplication ra = resAppController.applicationMenu(hdbOfficer);
        if (ra != null) {
            hdbOfficer.newApplication(ra);
            hdbOfficer.addToBlackList(ra.getProjectName());
            System.out.printf("Successfully applied for %s \n", ra.getProjectName());
        }
    }

    /**
     * Displays the team application menu and processes the officer's team application when applicable.
     *
     * @param hdbOfficer The officer who is applying for team applications.
     */
    public void displayTeamApplicationMenu(HdbOfficer hdbOfficer) {
        TeamApplication ta = teamAppController.applicationMenu(hdbOfficer);
        if (ta != null) {
            hdbOfficer.appliedTeam(ta);
            System.out.printf("Successfully applied for %s \n", ta.getProjectName());
        }
    }

    /**
     * Displays the assigned project menu if the officer has an assigned project.
     *
     * @param officer The officer for whom the assigned project details are displayed.
     */
    public void displayAssignedProjectMenu(HdbOfficer officer) {
        if (!officer.hasAssignedProject()) {
            System.out.println("You are not currently assigned to a project.");
            return;
        } else {
            projectController.displayAdminProjectDetails(officer.getAssignedProjectName());
            officerUI.displayAssignedProjectMenu(officer);
        }
    }

    /**
     * Displays the project applications for the assigned project.
     *
     * @param officer The officer who is viewing the project applications.
     */
    public void viewProjectApplications(HdbOfficer officer) {
        resAppController.viewApplications(officer.getAssignedProjectName());
    }

    /**
     * Displays project enquiries and allows the officer to make changes.
     *
     * @param officer The officer who is viewing the project enquiries.
     */
    public void viewProjectEnquiries(HdbOfficer officer) {
        enquiryController.showOfficerMenu(officer);
        enquiryController.saveChanges();
    }

    /**
     * Displays the report menu and generates a report based on the officer's actions.
     *
     * @param officer The officer for whom the report is being generated.
     */
    public void viewReport(HdbOfficer officer) {
        while (true) {
            String res = reportController.showOfficerMenu(officer);
            if (res == null) continue;
            if (res.equals("a")) break;
            generateReport(res, officer);
        }
        reportController.getRepo().saveFile();
    }

    /**
     * Generates a report for a specific applicant.
     *
     * @param applicantId The ID of the applicant for whom the report is generated.
     * @param officer     The officer requesting the report.
     */
    public void generateReport(String applicantId, HdbOfficer officer) {
        ResidentialApplication application = resAppController.getRepo().getApplications().get(applicantId);
        if (application == null) {
            System.out.println("No such application!");
            return;
        }
        if (application.getStatus() != Application.Status.BOOKED) {
            System.out.println("Applicant must have booked a flat!");
            return;
        }
        if (!application.getProjectName().equals(officer.getAssignedProjectName())) {
            System.out.println("The officer is not in the project!");
            return;
        }
        Applicant applicant = applicantController.getApplicantRepo().getUser(applicantId);
        if (applicant == null) {
            System.out.println("No such applicant!");
            return;
        }
        System.out.println("Generated Successfully!");
        System.out.println("Report ID: " + reportController.getRepo().addReport(application.getProjectName(),
                applicantId, application.getFlatType(),
                applicant.getAge(), applicant.getMaritalStatus()));
    }

    /**
     * Gets the repository for HDB Officer data.
     *
     * @return The officer repository.
     */
    public HdbOfficerRepo getRepo() {
        return officerRepo;
    }
}