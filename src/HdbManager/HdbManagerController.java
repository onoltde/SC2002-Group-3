package HdbManager;
import Application.Residential.ResidentialApplicationController;
import Application.Team.TeamApplicationController;
import Project.ProjectController;
import Project.ProjectControllerInterface;
import Users.*;

public class HdbManagerController implements UserController{

    //Dependencies
    private static HdbManagerRepo managerRepo;
    private static HdbManagerUI managerUI;
    //controller dependencies
    private final ProjectControllerInterface projectController;
    private final ResidentialApplicationController resAppController;
    private final TeamApplicationController teamAppController;

    //constructor
    public HdbManagerController(ProjectControllerInterface projectController, ResidentialApplicationController resAppController, TeamApplicationController teamAppController) {
        this.projectController = projectController;
        this.teamAppController = teamAppController;
        this.resAppController = resAppController;

        managerRepo = new HdbManagerRepo();
        managerUI = new HdbManagerUI(this);
    }

    public void runPortal() {
        //welcome menu display
        HdbManager currentUser = managerUI.displayLogin(managerRepo);
        if (currentUser == null) {
            return;
        }         //if returns null == user exits program
        managerUI.displayDashboard(currentUser);

    }

    public void displayProjectMenu(HdbManager manager){
        projectController.displayProjectDashboard(manager);
    }

    public void saveFile() {
        managerRepo.saveFile();
    }

    public HdbManagerRepo getRepo(){
        return managerRepo;
    }


//    public void createProjectListing(String managerId, String projectId, String name, String neighbourhood, HashMap<String,Integer> flatTypes,
//                                        Date applicationOpenDate, Date applicationCloseDate, int officerSlots) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return;
//        manager.createProjectListing(projectId, name, neighbourhood, flatTypes, applicationOpenDate, applicationCloseDate, officerSlots);
//    }
//    //
//
//    public void editProjectListing(String managerId, String projectId, String name, String neighbourhood,
//                                    Date applicationOpenDate,Date applicationCloseDate) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return;
//        manager.editProjectListing(projectId, name, neighbourhood, applicationOpenDate, applicationCloseDate);
//    }
//
//    public void deleteProjectListing(String managerId, String projectId) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return;
//        manager.deleteProjectListing(projectId);
//    }
//
//    public void toggleProjectVisibility(String managerId, String projectId) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return;
//        manager.toggleProjectVisibility(projectId);
//    }
//
//    public void viewCreatedProjects(String managerId) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return;
//        manager.viewCreatedProjects();
//    }
//
//    public void viewPendingOfficers() {
//        HdbOfficerController.getPendingOfficers().forEach(System.out::println);
//    }
//
//    public void viewApprovedOfficers() {
//        HdbOfficerController.getApprovedOfficers().forEach(System.out::println);
//    }
//
//    public boolean approveOfficerApplication(String managerId, String officerId) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return false;
//        return manager.approveOfficerApplication(officerId);
//    }
//
//    public boolean approveApplicantBTOApplication(String managerId, String applicationId) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return false;
//        return manager.approveApplicantBTOApplication(applicationId);
//    }
//
//    public boolean approveApplicantWithdrawal(String managerId, String applicationId) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return false;
//        return manager.approveApplicantWithdrawal(applicationId);
//    }
//
//    // public void viewEnquiry() {
//    //     ProjectEnquiryController.getAllEnquiries()
//    //             .forEach(System.out::println);
//    // }
//
//    public void replyEnquiry(String managerId, String enquiryId, String response) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return;
//        manager.replyEnquiry(enquiryId, response);
//    }
//
//    public boolean isDuringApplicationPeriod(String managerId, Date date) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return true;
//        return manager.isDuringApplicationPeriod(date);
//    }
//
//    public boolean isManaging(String managerId, String projectId) {
//        HdbManager manager = managers.get(managerId);
//        if(!check(manager)) return false;
//        return manager.isManaging(projectId);
//    }
}