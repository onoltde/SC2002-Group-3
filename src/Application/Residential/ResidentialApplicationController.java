package Application.Residential;

import Application.*;
import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.*;
import Applicant.*;
import Project.ProjectControllerInterface;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controller class for managing residential applications.
 * Implements the ResidentialApplicationControllerInterface and coordinates between
 * the UI, repository, and project controller to handle application processes.
 */
public class ResidentialApplicationController implements ResidentialApplicationControllerInterface {

    //dependencies
    private final ResidentialApplicationUI resAppUI;
    private final ResidentialApplicationRepo resAppRepo;

    private final ProjectControllerInterface projectController;

    /**
     * Constructs a new ResidentialApplicationController with the specified project controller.
     *
     * @param projectController The project controller to handle project-related operations
     */
    public ResidentialApplicationController(ProjectControllerInterface projectController) {
        this.resAppUI = new ResidentialApplicationUI(this);
        this.resAppRepo = new ResidentialApplicationRepo();
        this.projectController = projectController;
    }

    /**
     * Displays the application menu for an applicant.
     *
     * @param applicant The applicant viewing the menu
     */
    public void displayApplicationMenu(Applicant applicant) {
        resAppUI.displayApplicationMenu(applicant);
    }

    /**
     * Displays the application menu for an HDB officer.
     *
     * @param officer The HDB officer viewing the menu
     */
    public void displayApplicationMenu(HdbOfficer officer) {
        resAppUI.displayApplicationMenu(officer);
    }

    /**
     * Displays applications filtered by project name.
     *
     * @param projectName The name of the project to filter applications by
     */
    public void viewApplications(String projectName){
        ArrayList<ResidentialApplication> filteredApplications = resAppRepo.filterByProjectName(projectName);
        resAppUI.viewApplications(filteredApplications);
    }

    /**
     * Displays the application menu for an HDB officer and returns the created application.
     *
     * @param officer The HDB officer viewing the menu
     * @return The created application if a new one is made, null otherwise
     */
    public ResidentialApplication applicationMenu(HdbOfficer officer) {
        return resAppUI.displayApplicationMenu(officer);
    }

    /**
     * Handles the withdrawal request process for an application.
     * Updates the application status to WITHDRAWING if the request is confirmed.
     *
     * @param application The application to withdraw
     */
    public void requestWithdrawal(ResidentialApplication application){
        if (resAppUI.requestWithdrawal(application)){
            if (application.getStatus() == Application.Status.BOOKED){
                //NEED TO IMPLEMENT WAY TO UPDATE BOOKED NUMBER!!!
            }
            application.updateStatus(Application.Status.WITHDRAWING);
        }
    }

    /**
     * Displays available projects for application and creates a new application if selected.
     *
     * @param officer The HDB officer applying for a project
     * @param flatType The type of flat being applied for
     * @return The created application if a project is selected, null otherwise
     */
    public ResidentialApplication displayProjectsToApply(HdbOfficer officer, Flat.Type flatType) {
        String projName = projectController.displayResProjectsToApply(officer,flatType);
        if (projName != null) {
        	// check if is officer of same project
        	ResidentialApplication ra = new ResidentialApplication(officer.getId(), Application.Status.PENDING, projName, flatType);
        	resAppRepo.addApplication(ra);
        	return ra;
        }
        return null;
    }

    /**
     * Gets the residential application repository.
     *
     * @return The residential application repository
     */
    public ResidentialApplicationRepo getRepo(){
        return resAppRepo;
    }

    /**
     * Displays the details of a specific project's flat.
     *
     * @param projectName The name of the project
     * @param flatType The type of flat to display details for
     */
    public void displayProjectFlatDetails(String projectName, Flat.Type flatType){
        projectController.displayProjectFlatDetails(projectName,flatType);
    }

    /**
     * Adds a new residential application to the repository.
     *
     * @param residentialApplication The application to add
     */
    public void addApplication(ResidentialApplication residentialApplication) {
        resAppRepo.addApplication(residentialApplication);
	}

    /**
     * Creates and submits a new application for a project.
     *
     * @param applicant The applicant submitting the application
     * @param project The project being applied for
     * @param flatType The type of flat being applied for
     */
    public void applyProject(Applicant applicant, Project project, Flat.Type flatType){
        ResidentialApplication newApplication = new ResidentialApplication(applicant.getId(), Application.Status.PENDING, project.getName(),flatType);
        addApplication(newApplication);
        applicant.newApplication(newApplication);
    }

    /**
     * Initiates the booking process for a successful application.
     *
     * @param application The application to make a booking for
     */
    public void makeBooking(ResidentialApplication application) {
        if (application.getStatus() != Application.Status.SUCCESSFUL){
            System.out.println("You cannot make a booking yet. Please wait until your application is successful.");
        }else{
            application.setStatus(Application.Status.BOOKING);
        }
    }

    /**
     * Books a unit for an application and updates its status.
     *
     * @param application The application to book a unit for
     * @return true if the booking was successful, false otherwise
     */
    public boolean bookUnit(ResidentialApplication application){
        boolean res = projectController.bookFlat(application.getProjectName(), application.getFlatType());
        if(res) application.updateStatus(Application.Status.BOOKED);
        return res;
    }

    /**
     * Displays all applications for a specific project.
     *
     * @param project The project to display applications for
     */
    public void displayApplicationsByProject(Project project) {
        if(project == null) {
            System.out.println("Manager does not have project!");
            return;
        }
        ArrayList<ResidentialApplication> applications = resAppRepo.filterByProjectName(project.getName());
        resAppUI.displayApplications(applications);
    }

    /**
     * Displays the application menu for an HDB manager.
     *
     * @param manager The HDB manager viewing the menu
     * @return List of application IDs that were processed, or null if no action was taken
     */
    public ArrayList<String> displayApplicationMenu(HdbManager manager) { 
        return resAppUI.displayApplicationMenu(manager); 
    }

    /**
     * Processes an application (approve/reject) for a manager.
     *
     * @param manager The manager processing the application
     * @param applicantId The ID of the applicant
     * @param status true to approve, false to reject
     * @return List containing the action type, applicant ID, and status
     */
    public ArrayList<String> processApplication(HdbManager manager, String applicantId, boolean status) {
        return new ArrayList<>(Arrays.asList("a", applicantId, Boolean.toString(status)));
    }

    /**
     * Approves a withdrawal request for a manager.
     *
     * @param manager The manager approving the withdrawal
     * @param applicantId The ID of the applicant
     * @return List containing the action type and applicant ID
     */
    public ArrayList<String> approveWithdrawal(HdbManager manager, String applicantId) {
        return new ArrayList<>(Arrays.asList("b", applicantId));
    }

}//end of class
