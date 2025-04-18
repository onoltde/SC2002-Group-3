package HdbOfficer;
import Application.Team.*;
import Application.Residential.*;
import Project.Flat;
import Project.Project;
import Project.ProjectControllerInterface;
import Users.*;

public class HdbOfficerController implements UserController{

    //Dependencies
    private final HdbOfficerRepo officerRepo;
    private final HdbOfficerUI officerUI;
    //controller dependencies
    private final ProjectControllerInterface projectController;
    private final TeamApplicationController teamAppController;
    private final ResidentialApplicationController resAppController;

    public HdbOfficerController(ProjectControllerInterface projectController, ResidentialApplicationController resAppController, TeamApplicationController teamAppController) {
        this.projectController = projectController;
        this.resAppController = resAppController;
        this.teamAppController = teamAppController;
        officerRepo = new HdbOfficerRepo(resAppController.getRepo(),teamAppController.getRepo());
        officerUI = new HdbOfficerUI(this);
    }

    public void runPortal() {
        //welcome menu display
        HdbOfficer currentUser = officerUI.displayLogin(officerRepo);
        if (currentUser == null) {
            return;
        }         //if returns null == user exits program
        officerUI.displayDashboard(currentUser);

    }

    public void saveFile() {
        officerRepo.saveFile();
    }

    public void displayResApplicationMenu(HdbOfficer hdbOfficer){
        resAppController.displayApplicationMenu(hdbOfficer);
    }

    public void displayResidentialMenu(HdbOfficer hdbOfficer) {
        resAppController.displayApplicationMenu(hdbOfficer);
    }

    // Display and add teamApplication to the officer when applicable
    public void displayTeamApplicationMenu(HdbOfficer hdbOfficer) {
    	TeamApplication ta = teamAppController.applicationMenu(hdbOfficer);
        if (ta != null) {
        	hdbOfficer.appliedTeam(ta);
        	System.out.printf("Successfully applied for %s \n", ta.getProjectName());
        }
    }

    public void displayAssignedProjectMenu(HdbOfficer officer){
        officerUI.displayAssignedProjectMenu(officer);
    }

    public HdbOfficerRepo getRepo(){
        return officerRepo;
    }


}//end of class
