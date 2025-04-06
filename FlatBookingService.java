import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FlatBookingService {

    public boolean bookFlat(HdbOfficer officer, String applicantNric) {
        Project project = officer.getAssignedProject();
        if (project == null) {
            System.out.println("Officer is not assigned to any project.");
            return false;
        }

        Applicant applicant = project.getApplicant(applicantNric);
        if (applicant == null || applicant.getApplication() == null) {
            System.out.println("Applicant not found or no application present.");
            return false;
        }

        Application app = applicant.getApplication();
        if (!app.getStatus().equals(ApplicationStatus.SUCCESSFUL)) {
            System.out.println("Cannot book flat. Current application status: " + app.getStatus());
            return false;
        }

        String flatType = app.getFlatType();
        if (!project.hasFlatAvailable(flatType)) {
            System.out.println("No more units available for: " + flatType);
            return false;
        }

        // Update project inventory
        project.decreaseFlatCount(flatType);

        // Update applicant status
        app.setStatus(ApplicationStatus.BOOKED);
        applicant.setBookedFlat(new BookedFlat(flatType, project.getName(), LocalDateTime.now()));

        generateReceipt(applicant, project);
        return true;
    }

    private void generateReceipt(Applicant applicant, Project project) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("\n===== Flat Booking Receipt =====");
        System.out.println("Name: " + applicant.getName());
        System.out.println("NRIC: " + applicant.getNric());
        System.out.println("Age: " + applicant.getAge());
        System.out.println("Marital Status: " + applicant.getMaritalStatus());
        System.out.println("Flat Type: " + applicant.getBookedFlat().getFlatType());
        System.out.println("Project: " + project.getName());
        System.out.println("Booked On: " + applicant.getBookedFlat().getBookedOn().format(formatter));
        System.out.println("================================\n");
    }
}
