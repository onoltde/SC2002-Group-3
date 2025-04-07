import java.util.*;
import java.io.*;

public class HdbManagerController{
    private static Scanner sc;
    //Dependencies
    private static HdbManager currentUser= null;
    private static HdbManagerRepo repo;
    private static HdbManagerUI UI;

    public HdbManagerController(Scanner scanner) {
        sc = scanner;
        UI = new HdbManagerUI(sc,this);
        repo = new HdbManagerRepo();
    }

    public void runPortal() {
        //welcome menu display
        currentUser = UI.displayLogin();
        if (currentUser == null) {
            return;
        }         //if returns null == user exits program
        UI.displayDashboard(currentUser);

    }

    public void exitProgram() {
        repo.saveFile();
    }

    public HdbManagerRepo getRepo(){
        return repo;
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