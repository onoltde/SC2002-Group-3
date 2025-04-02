import java.util.*;
import java.io.*;

public class HdbManager extends User {
    private ArrayList<String> managedProjects;
    private String managerId;

    /**
     * Constructor for HDBManager.
     * @param name Manager's name
     * @param nric Manager's NRIC
     * @param age Manager's age
     * @param maritalStatus Manager's marital status
     * @param password Manager's password
     */

    public HdbManager(String name, String nric, int age, MaritalStatus maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        managerId = "MA" + nric.substring(nric.length() - 4);
        managedProjects = new ArrayList<String>();
    }

    // getter
    public String getId() { return managerId; }

    public boolean createProjectListing(String projectId, String name, String neighbourhood, HashMap<String,Integer> flatTypes,
                                        Date applicationOpenDate, Date applicationCloseDate, int officerSlots) {
        if (this.isDuringApplicationPeriod(applicationOpenDate) || 
            this.isDuringApplicationPeriod(applicationCloseDate)) {
            System.out.println("Period is overlapping!!");
            return false;
        }
        ProjectController.createProjectListing( projectId, name, neighbourhood, flatTypes,
                                                applicationOpenDate, applicationCloseDate, this.managerId, officerSlots);
        managedProjects.add(projectId);
        return true;
    }

    public void editProjectListing(String projectId, String name, String neighbourhood,
                                    Date applicationOpenDate,Date applicationCloseDate) {
        Project project = ProjectController.getProjectListing(projectId);
        if(project == null) {
            System.out.println("Such project doesn't exist!!");
            return;
        }
        if(!this.isManaging(projectId)) {
            System.out.println("Managers can only edit projects managed by themselves!!!!");
            return;
        }
        project.setName(name);
        project.setNeighbourhood(neighbourhood);
        project.setApplicationOpenDate(applicationOpenDate);
        project.setApplicationCloseDate(applicationCloseDate);
    }

    public void deleteProjectListing(String projectId) {
        Project project = ProjectController.getProjectListing(projectId);
        if(project == null) {
            System.out.println("Such project doesn't exist!!");
            return;
        }
        if(!this.isManaging(projectId)) {
            System.out.println("Managers can only delete projects managed by themselves!!!!");
            return;
        }
        ProjectController.deleteProjectListing(projectId);
        managedProjects.remove(projectId);
    }

    public void toggleProjectVisibility(String projectId) {
        Project project = ProjectController.getProjectListing(projectId);
        if(project == null) {
            System.out.println("Such project doesn't exist!!");
            return;
        }
        if(!this.isManaging(projectId)) {
            System.out.println("Managers can only toggle visibility of projects managed by themselves!!");
            return;
        }
        ProjectController.toggleProjectVisibility(projectId); // !!!!!
    }

    public void viewCreatedProjects() {
        managedProjects.forEach(System.out::println);
    }

    public void viewPendingOfficers() {
        HdbOfficerController.getPendingOfficers().forEach(System.out::println);
    }

    public void viewApprovedOfficers() {
        HdbOfficerController.getApprovedOfficers().forEach(System.out::println);
    }

    public boolean approveOfficerApplication(String officerId) {
        HdbOfficer officer = HdbOfficerController.getOfficer(officerId);

        if(officer == null) {
            System.out.println("No such officer");
            return false;
        }

        if(officer.getAssignedProject() == null) {
            System.out.println("The officer didn't apply for any project!!");
            return false;
        }

        Application application = ApplicationController.getApplicationByApplicant(officerId);
        if(!this.isManaging(application.getProjectId())) {
            System.out.println("The manager is not managing this project!!");
            return false;
        }
        application.approve();
        return true;
    }

    public boolean approveApplicantBTOApplication(String applicationId) {
        Application application = ApplicationController.getApplication(applicationId);
        if(application == null) {
            System.out.println("No such application!!");
            return false;
        }
        if(!this.isManaging(application.getProjectId())) {
            System.out.println("The manager is not managing this project!!");
            return false;
        }
        application.approve();
        return true;
    }

    public boolean approveApplicantWithdrawal(String applicationId) {
        Application application = ApplicationController.getApplication(applicationId);
        if (application == null) {
            System.out.println("No such application!!");
            return false;
        }
        if(!this.isManaging(application.getProjectId())) {
            System.out.println("Can approve withdrawal on only managed projects");
            return false;
        }
        application.withdraw();
        return true;
    }

    // public void viewEnquiry() {
    //     ProjectEnquiryController.getAllEnquiries()
    //             .forEach(System.out::println);
    // }

    public boolean replyEnquiry(String enquiryId, String response) {
        Enquiry enquiry = EnquiryController.getEnquiry(enquiryId);
        if (enquiry == null) {
            System.out.println("No such enquiry!!");
            return false;
        }
        String projectId = enquiry.getProjectId();
        if(!this.isManaging(projectId)) {
            System.out.println("The manager is not managing this project!!");
            return false;
        }
        EnquiryController.replyEnquiry(enquiryId, response);
        return true;
    }

    public boolean isDuringApplicationPeriod(Date date) {
        for (String projectId : managedProjects) {
            Project project = ProjectController.getProjectListing(projectId);
            if (project == null) continue;
            if (!date.before(project.getApplicationOpenDate()) && !date.after(project.getApplicationCloseDate())) {
                return true;
            }
        }
        return false;
    }

    public boolean isManaging(String projectId) {
        return managedProjects.contains(projectId);
    }
}