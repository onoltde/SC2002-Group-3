package Application.Team;
import Application.Application.Status;
import Application.Residential.ResidentialApplication;
import Application.TeamApplicationControllerInterface;
import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.Project;
import Project.ProjectControllerInterface;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controller class for managing team applications.
 * Implements the TeamApplicationControllerInterface and coordinates between
 * the UI, repository, and project controller to handle team application processes.
 */
public class TeamApplicationController implements TeamApplicationControllerInterface {

    //dependencies
    private final TeamApplicationUI teamAppUI;
    private final TeamApplicationRepo teamAppRepo;

    private final ProjectControllerInterface projectController;

    /**
     * Constructs a new TeamApplicationController with the specified project controller.
     *
     * @param projectController The project controller to handle project-related operations
     */
    public TeamApplicationController(ProjectControllerInterface projectController){
        teamAppUI = new TeamApplicationUI(this);
        teamAppRepo = new TeamApplicationRepo();

        this.projectController = projectController;
    }

    /**
     * Displays the details of the project assigned to an officer.
     *
     * @param officer The officer whose assigned project details to display
     */
    public void displayAssignedProject(HdbOfficer officer) {
        String appliedProjectName = officer.getTeamApplication().getProjectName();
        projectController.displayAdminProjectDetails(appliedProjectName);
    }

    /**
     * Displays the application menu for an HDB officer and returns the created application.
     *
     * @param officer The HDB officer viewing the menu
     * @return The created application if a new one is made, null otherwise
     */
    public TeamApplication applicationMenu(HdbOfficer officer) {
    	return teamAppUI.displayApplicationMenu(officer);
    }
    
    /**
     * Displays available projects for team application and creates a new application if selected.
     *
     * @param officer The HDB officer applying for a team
     * @return The created application if a project is selected, null otherwise
     */
    public TeamApplication displayProjects(HdbOfficer officer){
    	String check = projectController.displayTeamProjectsToApply(officer);
    	if (check != null) {
    		// check if the officer is already applying for other projects
    		if (officer.hasTeamApplication()) {
    			return null;
    		}

    		TeamApplication ta = new TeamApplication(officer.getId(), check, Status.PENDING);
    		teamAppRepo.addApplication(ta);
    		// need to update officer's information
    		return ta;
    	}
    	else {
    		return null;
    	}
    }

    /**
     * Gets the team application repository.
     *
     * @return The team application repository
     */
    public TeamApplicationRepo getRepo(){
        return teamAppRepo;
    }

    /**
     * Adds a new team application to the repository.
     *
     * @param teamApp The application to add
     */
    public void addApplication(TeamApplication teamApp) {
		// TODO Auto-generated method stub

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
        ArrayList<TeamApplication> applications = teamAppRepo.filterByProjectName(project.getName());
        teamAppUI.displayApplications(applications);
    }

    /**
     * Displays the application menu for an HDB manager.
     *
     * @param manager The HDB manager viewing the menu
     * @return List of application IDs that were processed, or null if no action was taken
     */
    public ArrayList<String> displayApplicationMenu(HdbManager manager) { return teamAppUI.displayApplicationMenu(manager); }

    /**
     * Processes an application (approve/reject) for a manager.
     *
     * @param manager The manager processing the application
     * @param officerId The ID of the officer
     * @param status true to approve, false to reject
     * @return List containing the action type, officer ID, and status
     */
    public ArrayList<String> processApplication(HdbManager manager, String officerId, boolean status) {
        return new ArrayList<>(Arrays.asList("a", officerId, Boolean.toString(status)));
    }

    /**
     * Approves a withdrawal request for a manager.
     *
     * @param manager The manager approving the withdrawal
     * @param officerId The ID of the officer
     * @return List containing the action type and officer ID
     */
    public ArrayList<String> approveWithdrawal(HdbManager manager, String officerId) {
        return new ArrayList<>(Arrays.asList("b", officerId));
    }
}

