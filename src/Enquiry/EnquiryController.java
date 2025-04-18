package Enquiry;
import HdbManager.HdbManager;
import Applicant.*;
import Project.ProjectControllerInterface;

public class EnquiryController {
    private EnquiryRepo enquiryRepo;
    private EnquiryUI enquiryUI;

    public EnquiryController(ProjectControllerInterface projectController) {
        enquiryRepo = new EnquiryRepo();
        enquiryUI = new EnquiryUI(this, enquiryRepo, projectController);
    }

    public void addEnquiry(String projectId, String authorId, String title, String message) {
        String enquiryId = enquiryRepo.generateId();
        enquiryRepo.addEnquiry(new Enquiry(enquiryId, projectId, authorId, title, message));
    }

    // getter
    public Enquiry getEnquiry(String enquiryId) {
        return enquiryRepo.getEnquiry(enquiryId);
    }

    public EnquiryRepo getRepo() {
        return enquiryRepo;
    }

    public void showApplicantMenu(Applicant user) { enquiryUI.showApplicantMenu(user); }
    public void showManagerMenu(HdbManager user) { enquiryUI.showManagerMenu(user); }
}
