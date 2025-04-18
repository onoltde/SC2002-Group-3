package Application.Team;
import Application.Application.Status;
import Application.TeamApplicationControllerInterface;
import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.ProjectControllerInterface;


public class TeamApplicationController implements TeamApplicationControllerInterface {

    //dependencies
    private final TeamApplicationUI teamAppUI;
    private final TeamApplicationRepo teamAppRepo;

    private final ProjectControllerInterface projectController;

    public TeamApplicationController(ProjectControllerInterface projectController){
        teamAppUI = new TeamApplicationUI(this);
        teamAppRepo = new TeamApplicationRepo();

        this.projectController = projectController;
    }

    //maanger methods
    //@Override   //view team application as manager to accept/reject team applications
    public void displayApplicationMenu(HdbManager manager) {

    }

    //officer methods
    public void displayAssignedProject(HdbOfficer officer) {
        String appliedProjectName = officer.getTeamApplication().getProjectName();
        projectController.displayAdminProjectDetails(appliedProjectName);
    }

    //view team applications as officer to view/apply and returns TeamApplication object
    public TeamApplication applicationMenu(HdbOfficer officer) {
    	return teamAppUI.displayApplicationMenu(officer);
    }
    
    // DisplayProjects and add TeamApplication if applicable
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

    public TeamApplicationRepo getRepo(){
        return teamAppRepo;
    }

  public void addApplication(TeamApplication teamApp) {
		// TODO Auto-generated method stub

	}
}

