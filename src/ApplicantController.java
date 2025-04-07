import java.util.Scanner;
import java.util.regex.Pattern;

public class ApplicantController implements UserController{

    private static Scanner sc;
    //Dependencies
    private static Applicant currentUser = null;
    private static ApplicantUI UI;
    private static ApplicationRepo applicationRepo;
    private static ApplicantRepo applicantRepo;

    public ApplicantController(Scanner scanner) {
        sc = scanner;
        UI = new ApplicantUI(sc,this);
        applicationRepo = new ApplicationRepo();
        applicantRepo = new ApplicantRepo(applicationRepo);
    }

    public void runPortal() {
        //welcome menu display
        currentUser = UI.displayLogin();
        if (currentUser == null){return;}         //if returns null == user exits program
        UI.displayDashboard(currentUser);

    }

    public void exitProgram(){
        applicantRepo.saveFile();     //saves ApplicantList.csv file
    }

    public ApplicantRepo getApplicantRepo(){
        return applicantRepo;
    }

}//end of class