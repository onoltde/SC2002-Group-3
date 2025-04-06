import java.util.Scanner;
import java.util.regex.Pattern;

public class ApplicantController {

    private static Scanner sc;
    private static Applicant applicant;

    public ApplicantController(Scanner scanner) {
        sc = scanner;
        new ApplicantUI(sc);
        new ApplicantRepo();
    }

    public static void runPortal() {
        //welcome menu display
        applicant = ApplicantUI.displayLogin();
        if (applicant == null){return;}         //if returns null == user exits program
        ApplicantUI.displayDashboard(applicant);

    }

    public static void exitProgram(){
        ApplicantRepo.saveApplicants();     //saves ApplicantList.csv file
    }

}//end of class