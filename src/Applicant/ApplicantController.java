package Applicant;
import Application.Residential.ResidentialApplicationController;
import Enquiry.EnquiryController;
import Users.*;
import Project.*;

public class ApplicantController implements UserController{

    //Dependencies
    private final ApplicantUI applicantUI;
    private final ApplicantRepo applicantRepo;
    //controller dependencies
    private final ProjectControllerInterface projectController;
    private final ResidentialApplicationController resAppController;
    private final EnquiryController enquiryController;

    //constructor
    public ApplicantController(ResidentialApplicationController resAppController,
                               ProjectControllerInterface projectController,
                               EnquiryController enquiryController) {

        this.resAppController = resAppController;
        this.projectController = projectController;
        this.enquiryController = enquiryController;

        applicantRepo = new ApplicantRepo(resAppController.getRepo());
        applicantUI = new ApplicantUI(this, enquiryController);
    }

    public void runPortal() {
        //welcome menu display
        Applicant currentUser = applicantUI.displayLogin(applicantRepo);
        if (currentUser == null){return;}         //if returns null == user exits program
        applicantUI.displayDashboard(currentUser);
    }

    public void viewCurrentProjects(Applicant applicant){
        projectController.displayProjectDashboard(applicant,resAppController);
    }

    public void displayApplicationMenu(Applicant applicant){
        resAppController.displayApplicationMenu(applicant);
    }

    public void saveFile(){
        applicantRepo.saveFile();     //saves ApplicantList.csv file
    }

    public ApplicantRepo getApplicantRepo(){
        return applicantRepo;
    }

}//end of class