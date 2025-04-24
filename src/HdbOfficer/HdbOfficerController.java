package HdbOfficer;
import Applicant.Applicant;
import Application.Application;
import Application.ResidentialApplicationControllerInterface;
import Application.Team.*;
import Enquiry.EnquiryController;
import HdbManager.HdbManager;
import Application.Residential.*;
import Application.TeamApplicationControllerInterface;
import Project.Flat;
import Project.Project;
import Project.ProjectControllerInterface;
import Report.ReportController;
import Applicant.ApplicantController;
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
    private final ReportController reportController;
    private final ApplicantController applicantController;

    public HdbOfficerController(ProjectControllerInterface projectController, ResidentialApplicationController resAppController,
                                TeamApplicationController teamAppController, EnquiryController enquiryController,
                                ReportController reportController, ApplicantController applicantController) {
        this.projectController = projectController;
        this.resAppController = resAppController;
        this.teamAppController = teamAppController;
        this.enquiryController = enquiryController;
        this.reportController = reportController;
        this.applicantController = applicantController;
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

    public void viewReport(HdbOfficer officer) {
        while(true) {
            String res = reportController.showOfficerMenu(officer);
            if(res == null) continue;
            if(res == "a") break;
            generateReport(res, officer);

        }
        reportController.getRepo().saveFile();
    }
    public void generateReport(String applicantId, HdbOfficer officer) {
        ResidentialApplication application = resAppController.getRepo().getApplications().get(applicantId);
        if(application == null) {
            System.out.println("No such application!");
            return;
        }
        if(application.getStatus() != Application.Status.BOOKED) {
            System.out.println("Applicant must have booked a flat!");
            return;
        }
        if(!application.getProjectName().equals(officer.getAssignedProjectName())) {
            System.out.println("The officer is not in the project!");
            return;
        }
        Applicant applicant = applicantController.getApplicantRepo().getUser(applicantId);
        if(applicant == null) {
            System.out.println("No such applicant!");
            return;
        }
        System.out.println("Generated Successfully!");
        System.out.println("Report ID: " + reportController.getRepo().addReport(application.getProjectName(),
                applicantId, application.getFlatType(),
                applicant.getAge(), applicant.getMaritalStatus()));
    }

    public HdbOfficerRepo getRepo(){
        return officerRepo;
    }


}//end of class
