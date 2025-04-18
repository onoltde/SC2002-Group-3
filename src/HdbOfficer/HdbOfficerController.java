package HdbOfficer;
import Application.ResidentialApplicationControllerInterface;
import Application.Team.*;
import Enquiry.EnquiryController;
import HdbManager.HdbManager;
import Application.Residential.*;
import Application.TeamApplicationControllerInterface;
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
    private final EnquiryController enquiryController;

    public HdbOfficerController(ProjectControllerInterface projectController, ResidentialApplicationController resAppController, TeamApplicationController teamAppController, EnquiryController enquiryController) {
        this.projectController = projectController;
        this.resAppController = resAppController;
        this.teamAppController = teamAppController;
        this.enquiryController = enquiryController;
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

    // Display and add residentialApplication to the officer when applicable
    public void displayResidentialMenu(HdbOfficer hdbOfficer) {
        ResidentialApplication ra = resAppController.applicationMenu(hdbOfficer);
        if (ra != null) {
        	hdbOfficer.newApplication(ra);
        	hdbOfficer.addToBlackList(ra.getProjectName());
        	System.out.printf("Successfully applied for %s \n", ra.getProjectName());
        }
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
        if(officer.hasAssignedProject() == false) {
            System.out.println("You are not currently assigned to a project.");
            return;
        }else{
            projectController.displayAdminProjectDetails(officer.getAssignedProjectName());
            officerUI.displayAssignedProjectMenu(officer);
        }

    }

    public void viewProjectApplications(HdbOfficer officer){
        resAppController.viewApplications(officer.getAssignedProjectName());
    }

    public void viewProjectEnquiries(HdbOfficer officer){
    	enquiryController.showOfficerMenu(officer);
        enquiryController.saveChanges();
    }

    public HdbOfficerRepo getRepo(){
        return officerRepo;
    }


}//end of class
