package Application.Team;
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

    public void displayProjects(HdbOfficer officer){
        projectController.displayTeamProjectsToApply(officer);
    }

    public TeamApplicationRepo getRepo(){
        return teamAppRepo;
    }
}
