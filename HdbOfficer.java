import java.util.*;

/**
 * Represents an HDB officer who can manage BTO projects and perform flat bookings.
 * Inherits from Applicant.
 */
public class HdbOfficer extends Applicant {

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
        super(nric, name, age, maritalStatus, password);
        this.officerId = "OF-" + nric.substring(5);
    }

    public String getOfficerId() {
        return officerId;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public void assignProject(Project project) {
        this.assignedProject = project;
    }

    /**
     * Books a flat for the given applicant under this officer's assigned project.
     */
    public boolean bookFlatForApplicant(String applicantNric, String flatType) {
        if (assignedProject == null) {
            System.out.println("No assigned project.");
            return false;
        }

        Applicant applicant = assignedProject.getApplicant(applicantNric);
        if (applicant == null) {
            System.out.println("Applicant not found in project.");
            return false;
        }

        Application app = applicant.getApplication();
        if (app == null || !app.getStatus().equals(ApplicationStatus.SUCCESSFUL)) {
            System.out.println("Applicant does not have a successful application.");
            return false;
        }

        if (!assignedProject.hasFlatAvailable(flatType)) {
            System.out.println("No flats available for selected type.");
            return false;
        }

        assignedProject.decreaseFlatCount(flatType);
        BookedFlat booked = new BookedFlat(flatType, assignedProject.getName(), java.time.LocalDateTime.now());
        applicant.setBookedFlat(booked);
        app.setStatus(ApplicationStatus.BOOKED);
        return true;
    }

    /**
     * Generate a flat booking receipt for the applicant.
     */
    public String generateReceipt(Applicant applicant) {
        if (applicant.getBookedFlat() == null) {
            return "No booking found.";
        }

        BookedFlat flat = applicant.getBookedFlat();
        return String.format(
            "Receipt\n-------\nName: %s\nNRIC: %s\nAge: %d\nMarital Status: %s\nProject: %s\nFlat Type: %s\nBooked On: %s\n",
            applicant.getName(),
            applicant.getNric(),
            applicant.getAge(),
            applicant.getMaritalStatus(),
            flat.getProjectName(),
            flat.getFlatType(),
            flat.getBookedOn().toString()
        );
    }

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