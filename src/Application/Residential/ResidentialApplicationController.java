package Application.Residential;

import Application.*;
import HdbManager.HdbManager;
import HdbOfficer.HdbOfficer;
import Project.*;
import Applicant.*;
import Project.ProjectControllerInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class ResidentialApplicationController implements ResidentialApplicationControllerInterface {

    //dependencies
    private final ResidentialApplicationUI resAppUI;
    private final ResidentialApplicationRepo resAppRepo;

    private final ProjectControllerInterface projectController;

    public ResidentialApplicationController(ProjectControllerInterface projectController) {
        this.resAppUI = new ResidentialApplicationUI(this);
        this.resAppRepo = new ResidentialApplicationRepo();
        this.projectController = projectController;
    }

    public void displayApplicationMenu(Applicant applicant) {
        resAppUI.displayApplicationMenu(applicant);
    }

    //to apply as resident
    public void displayApplicationMenu(HdbOfficer officer) {
        resAppUI.displayApplicationMenu(officer);
    }

    public void viewApplications(String projectName){
        ArrayList<ResidentialApplication> filteredApplications = resAppRepo.filterByProjectName(projectName);
        resAppUI.viewApplications(filteredApplications);
    }

    //to apply as resident
    public ResidentialApplication applicationMenu(HdbOfficer officer) {
        return resAppUI.displayApplicationMenu(officer);
    }

    public void requestWithdrawal(ResidentialApplication application){
        if (resAppUI.requestWithdrawal(application)){
            if (application.getStatus() == Application.Status.BOOKED){
                //NEED TO IMPLEMENT WAY TO UPDATE BOOKED NUMBER!!!
            }
            application.updateStatus(Application.Status.WITHDRAWING);
        }
    }

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

    public ResidentialApplicationRepo getRepo(){
        return resAppRepo;
    }

    public void displayProjectFlatDetails(String projectName, Flat.Type flatType){
        projectController.displayProjectFlatDetails(projectName,flatType);
    }

	public void addApplication(ResidentialApplication residentialApplication) {
        resAppRepo.addApplication(residentialApplication);
	}

    //applicant methods
    public void applyProject(Applicant applicant, Project project, Flat.Type flatType){
        ResidentialApplication newApplication = new ResidentialApplication(applicant.getId(), Application.Status.PENDING, project.getName(),flatType);
        addApplication(newApplication);
        applicant.newApplication(newApplication);
    }

    public void makeBooking(ResidentialApplication application) {
        if (application.getStatus() != Application.Status.SUCCESSFUL){
            System.out.println("You cannot make a booking yet. Please wait until your application is successful.");
        }else{
            application.setStatus(Application.Status.BOOKING);
        }
    }

    public boolean bookUnit(ResidentialApplication application){
        return projectController.bookFlat(application.getProjectName(), application.getFlatType());
    }

    // manager methods
    public void displayApplicationsByProject(Project project) {
        if(project == null) {
            System.out.println("Manager does not have project!");
            return;
        }
        ArrayList<ResidentialApplication> applications = resAppRepo.filterByProjectName(project.getName());
        resAppUI.displayApplications(applications);
    }

    public ArrayList<String> displayApplicationMenu(HdbManager manager) { return resAppUI.displayApplicationMenu(manager); }

    public ArrayList<String> processApplication(HdbManager manager, String applicantId, boolean status) {
        return new ArrayList<>(Arrays.asList("a", applicantId, Boolean.toString(status)));
    }

    public ArrayList<String> approveWithdrawal(HdbManager manager, String applicantId) {
        return new ArrayList<>(Arrays.asList("b", applicantId));
    }

}//end of class
