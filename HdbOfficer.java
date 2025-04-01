package Project;

import java.util.List;

public class HdbOfficer {

    private String nric;
    private int age;
    private String maritalStatus;
    private String password;
    private Project assignedProject;

    public HDBOfficer(String nric, int age, String maritalStatus, String password) {
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    public boolean login() {
        return false;
    }

    public void changePassword(String newPassword) {
        
    }

    public List<Project> viewProjects() {
        return null;
    }

    public Application applyForProject(Project project) {
        return null;
    }

    public ApplicationStatus viewApplicationStatus() {
        return null;
    }

    public RegistrationStatus registerForProject(String projectID) {
        if(!canRegister(projectID)){
            return RegistrationStatus.REJECTED;
        }
        
        return null;
    }

    public RegistrationStatus viewRegistrationStatus() {
        return null;
    }

    public Project viewProjectDetails(Project project) {
        return null;
    }

    public List<Application> viewApplicants() {
        return null;
    }

    public boolean approveApplication(String applicationID) {
        return false;
    }

    public boolean rejectApplication(String applicationID) {
        return false;
    }

    public boolean bookFlat(String applicationID, String flatType) {
        return false;
    }

    public Receipt generateReceipt(String applicationID) {
        return null;
    }

    public List<Enquiry> viewEnquiries() {
        return null;
    }

    public void replyToEnquiry(String enquiryID, String response) {
        
    }

    public void updateFlatAvailability(String flatType, int remaining) {
       
    }

    public void updateApplicationStatus(Applicant applicant, ApplicationStatus status) {
        
    }

    public String getNric() {
        return nric;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getPassword() {
        return password;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
    }

    public static boolean canRegister(String projectID) {
        return false;
    }
}
