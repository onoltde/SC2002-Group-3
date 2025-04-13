package HdbOfficer;
import Application.Team.*;
import Application.Residential.*;
import Users.*;

public class HdbOfficerController implements UserController{
    //Dependencies
    private static HdbOfficer currentUser = null;
    private static HdbOfficerRepo officerRepo;
    private static ResidentialApplicationRepo resAppRepo;
    private static TeamApplicationRepo teamAppRepo;
    private static HdbOfficerUI officerUI;

    public HdbOfficerController() {
        resAppRepo = new ResidentialApplicationRepo();
        teamAppRepo = new TeamApplicationRepo();
        officerRepo = new HdbOfficerRepo(resAppRepo,teamAppRepo);
        officerUI = new HdbOfficerUI(this, officerRepo);
    }

    public void runPortal() {
        //welcome menu display
        currentUser = officerUI.displayLogin();
        if (currentUser == null) {
            return;
        }         //if returns null == user exits program
        officerUI.displayDashboard(currentUser);

    }

    public void exitPortal() {
        officerRepo.saveFile();
    }

    public void displayResApplicationMenu(HdbOfficer hdbOfficer){
        ResidentialApplicationController resAppController = new ResidentialApplicationController(this);
        resAppController.displayApplicationMenu(hdbOfficer);
    }

    public void displayResidentialMenu(HdbOfficer hdbOfficer) {
        ResidentialApplicationController resAppController = new ResidentialApplicationController(this);
        resAppController.displayApplicationMenu(hdbOfficer);
    }

    public void displayTeamApplicationMenu(HdbOfficer hdbOfficer) {
        TeamApplicationController teamAppController = new TeamApplicationController(this);
        teamAppController.displayApplicationMenu(hdbOfficer);
    }

    public void displayAssignedProjectMenu(HdbOfficer officer){
        officerUI.displayAssignedProjectMenu(officer);
    }





    public HdbOfficerRepo getRepo(){
        return officerRepo;
    }


}//end of class