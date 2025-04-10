import java.util.Scanner;

public class ApplicantController implements UserController{

    //Dependencies
    private static Applicant currentUser = null;
    private static ApplicantUI UI;
    private static ResidentialApplicationRepo resApplicationRepo;
    private static ApplicantRepo applicantRepo;

    public ApplicantController() {
        resApplicationRepo = new ResidentialApplicationRepo();
        applicantRepo = new ApplicantRepo(resApplicationRepo);
        UI = new ApplicantUI(this , applicantRepo);

    }

    public void runPortal() {
        //welcome menu display
        currentUser = UI.displayLogin();
        if (currentUser == null){return;}         //if returns null == user exits program
        UI.displayDashboard(currentUser);

    }

    public void viewCurrentProjects(Applicant applicant){
        ProjectController projectController = new ProjectController();
        projectController.displayProjectDashboard(applicant);

    }

    public void exitPortal(){
        applicantRepo.saveFile();     //saves ApplicantList.csv file
    }

    public ApplicantRepo getApplicantRepo(){
        return applicantRepo;
    }

}//end of class