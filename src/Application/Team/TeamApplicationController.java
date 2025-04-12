package Application.Team;
import Application.ApplicationController;
import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.ProjectController;
import Users.UserController;

public class TeamApplicationController implements ApplicationController {

    private UserController sourceController;
    private  TeamApplicationUI teamAppUI;

    public TeamApplicationController(UserController sourceController){
        this.sourceController = sourceController;
        teamAppUI = new TeamApplicationUI(this);
    }

    @Override   //view team applications as officer to view/apply
    public void displayApplicationMenu(HdbOfficer officer) {
        teamAppUI.displayApplicationMenu(officer);
    }

    @Override   //view team application as manager to accept/reject team applications
    public void displayApplicationMenu(HdbManager manager) {

    }

    public void displayAssignedProject(HdbOfficer officer) {
        ProjectController projectController = new ProjectController(sourceController);
        String appliedProjectName = officer.getTeamApplication().getProjectName();
        projectController.displayAdminProjectDetails(appliedProjectName);
    }

    public void displayProjects(HdbOfficer officer){
        ProjectController projectController = new ProjectController(sourceController);
        projectController.displayTeamProjectsToApply(officer);
    }
}
