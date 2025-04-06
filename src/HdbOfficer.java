import java.util.*;

/**
 * Represents an HDB officer who can manage BTO projects and related operations.
 * Extends the Applicant class which also inherits basic User properties as well as Applicant properties.
 */
public class HdbOfficer extends Applicant{

    private Project assignedProject;
    private final String officerId;
    /**
     * Constructor for HdbOfficer.
     * @param name Officer's name
     * @param nric Officer's NRIC
     * @param age Officer's age
     * @param maritalStatus Officer's marital status
     * @param password Officer's password
     */
    public HdbOfficer(String name, String nric, int age, User.MaritalStatus maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password,null);
        this.officerId = "OF-" + nric.substring(5);
    }

    public String getId(){return officerId;}

    @Override
    public String toString() {
        return "Officer" + super.toString() + ", ID: " + officerId + "}" ;
    }

    public List<Project> viewProjects() {
        return null;
    }

//
//    /**
//     * Books a flat for the given applicant under this officer's assigned project.
//     */
//    public boolean bookFlatForApplicant(String applicantNric, String flatType) {
//        if (assignedProject == null) {
//            System.out.println("No assigned project.");
//            return false;
//        }
//
//        Applicant applicant = assignedProject.getApplicant(applicantNric);
//        if (applicant == null) {
//            System.out.println("Applicant not found in project.");
//            return false;
//        }
//
//        Application app = applicant.getApplication();
//        if (app == null || !app.getStatus().equals(ApplicationStatus.SUCCESSFUL)) {
//            System.out.println("Applicant does not have a successful application.");
//            return false;
//        }
//
//        if (!assignedProject.hasFlatAvailable(flatType)) {
//            System.out.println("No flats available for selected type.");
//            return false;
//        }
//
//        assignedProject.decreaseFlatCount(flatType);
//        BookedFlat booked = new BookedFlat(flatType, assignedProject.getName(), java.time.LocalDateTime.now());
//        applicant.setBookedFlat(booked);
//        app.updateStatus(ApplicationStatus.BOOKED);
//        return true;
//    }
//
//    /**
//     * Generate a flat booking receipt for the applicant.
//     */
//    public String generateReceipt(Applicant applicant) {
//        if (applicant.getBookedFlat() == null) {
//            return "No booking found.";
//        }
//
//        BookedFlat flat = applicant.getBookedFlat();
//        return String.format(
//            "Receipt\n-------\nName: %s\nNRIC: %s\nAge: %d\nMarital Status: %s\nProject: %s\nFlat Type: %s\nBooked On: %s\n",
//            applicant.getName(),
//            applicant.getNric(),
//            applicant.getAge(),
//            applicant.getMaritalStatus(),
//            flat.getProjectName(),
//            flat.getFlatType(),
//            flat.getBookedOn().toString()
//        );
//    }

    // Add more methods (e.g., reply to enquiries) as needed





//    public Application applyForProject(Project project) {
//        return null;
//    }
//
//    public ApplicationStatus viewApplicationStatus() {
//        return null;
//    }
//
//    public RegistrationStatus registerForProject(String projectID) {
//        if(!canRegister(projectID)){
//            return RegistrationStatus.REJECTED;
//        }
//
//        return null;
//    }
//
//    public RegistrationStatus viewRegistrationStatus() {
//        return null;
//    }
//
//    public Project viewProjectDetails(Project project) {
//        return null;
//    }
//
//    public List<Application> viewApplicants() {
//        return null;
//    }
//
//    public boolean approveApplication(String applicationID) {
//        return false;
//    }
//
//    public boolean rejectApplication(String applicationID) {
//        return false;
//    }
//
//    public boolean bookFlat(String applicationID, String flatType) {
//        return false;
//    }
//
//    public Receipt generateReceipt(String applicationID) {
//        return null;
//    }
//
//    public List<Enquiry> viewEnquiries() {
//        return null;
//    }
//
//    public void replyToEnquiry(String enquiryID, String response) {
//
//    }
//
//    public void updateFlatAvailability(String flatType, int remaining) {
//
//    }
//
//    public void updateApplicationStatus(Applicant applicant, ApplicationStatus status) {
//
//    }
//
//    public String getNric() {
//        return nric;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public MaritalStatus getMaritalStatus() {
//        return maritalStatus;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public Project getAssignedProject() {
//        return assignedProject;
//    }
//
//    public void setAssignedProject(Project assignedProject) {
//        this.assignedProject = assignedProject;
//    }
//
//    public static boolean canRegister(String projectID) {
//        return false;
//    }
}
