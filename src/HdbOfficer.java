import java.util.*;



public class HdbOfficer extends Applicant{

    private Project assignedProject;

    /**
     * Constructor for HdbOfficer.
     * @param name Officer's name
     * @param nric Officer's NRIC
     * @param age Officer's age
     * @param maritalStatus Officer's marital status
     * @param password Officer's password
     */
    public HdbOfficer(String name, String nric, int age, User.MaritalStatus maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
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