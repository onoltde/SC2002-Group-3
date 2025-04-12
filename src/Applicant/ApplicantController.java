package Applicant;
import Users.*;
import Application.Residential.*;
import Project.*;

public class ApplicantController implements UserController{

    //Dependencies
    private static Applicant currentUser = null;
    private static ApplicantUI applicantUI;
    private static ResidentialApplicationRepo resApplicationRepo;
    private static ApplicantRepo applicantRepo;

    public ApplicantController() {
        resApplicationRepo = new ResidentialApplicationRepo();
        applicantRepo = new ApplicantRepo(resApplicationRepo);
        applicantUI = new ApplicantUI(this , applicantRepo);

    }

    public void runPortal() {
        //welcome menu display
        currentUser = applicantUI.displayLogin();
        if (currentUser == null){return;}         //if returns null == user exits program
        applicantUI.displayDashboard(currentUser);

    }

    public void viewCurrentProjects(Applicant applicant){
        ProjectController projectController = new ProjectController(this);
        projectController.displayProjectDashboard(applicant);
    }

    public void displayApplicationMenu(Applicant applicant){
        ResidentialApplicationController resAppController = new ResidentialApplicationController(this);
        resAppController.displayApplicationMenu(applicant);
    }

    public void exitPortal(){
        applicantRepo.saveFile();     //saves ApplicantList.csv file
    }

    public ApplicantRepo getApplicantRepo(){
        return applicantRepo;
    }

}//end of class