package Application.Team;
import Application.Application.Status;
import Application.ApplicationController;
import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.ProjectController;
import Project.ProjectControllerInterface;
import Users.UserController;

public class TeamApplicationController implements ApplicationController {

    //dependencies
    private final TeamApplicationUI teamAppUI;
    private final TeamApplicationRepo teamAppRepo;

    private final ProjectControllerInterface projectController;

    public TeamApplicationController(ProjectControllerInterface projectController){
        teamAppUI = new TeamApplicationUI(this);
        teamAppRepo = new TeamApplicationRepo();

        this.projectController = projectController;
    }

    @Override   //view team applications as officer to view/apply
    public void displayApplicationMenu(HdbOfficer officer) {
        teamAppUI.displayApplicationMenu(officer);
    }

    @Override   //view team application as manager to accept/reject team applications
    public void displayApplicationMenu(HdbManager manager) {

    }

    public void displayAssignedProject(HdbOfficer officer) {
        String appliedProjectName = officer.getTeamApplication().getProjectName();
        projectController.displayAdminProjectDetails(appliedProjectName);
    }

 // DisplayProjects and add TeamApplication if applicable
    public TeamApplication displayProjects(HdbOfficer officer){
    	String check = projectController.displayTeamProjectsToApply(officer);
    	if (check != null) {
    		// check whether officer is currently an officer for other projects
    		if (officer.hasAssignedProject()) {
    			String source = officer.getAssignedProjectName();
    			boolean clash = projectController.checkClash(check, source);
    			if (clash) {
    				return null;
    			}
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

    public TeamApplicationRepo getRepo(){
        return teamAppRepo;
    }

	public void addApplication(TeamApplication teamApp) {
		// TODO Auto-generated method stub
		
	}
}

