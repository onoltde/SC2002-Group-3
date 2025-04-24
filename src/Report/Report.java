package Report;
import Project.*;
import Users.*;
/**
 * Represents a report summarizing an applicant's data for a project.
 */
public class Report {
    private String reportId;
    private String projectName;
    private String applicantId;
    private Flat.Type flatType;
    private int applicantAge;
    private User.MaritalStatus martialStatus;

    /**
     * Constructs a Report instance with all required fields.
     */
    public Report(String reportId, String projectName, String applicantId,
                  Flat.Type flatType, int applicantAge, User.MaritalStatus martialStatus) {
        this.reportId = reportId;
        this.projectName = projectName;
        this.applicantId = applicantId;
        this.flatType = flatType;
        this.applicantAge = applicantAge;
        this.martialStatus = martialStatus;
    }

    // getter
    /** @return unique ID of the report */
    public String getId() { return reportId; }
     /** @return name of the project related to this report */
    public String getProjectName() { return projectName; }
    /** @return applicant ID related to this report */
    public String getApplicantId() { return applicantId; }
    /** @return age of the applicant */
    public int getApplicantAge() { return applicantAge; }
    /** @return flat type requested */
    public Flat.Type getFlatType() { return flatType; }
    /** @return marital status of the applicant */
    public User.MaritalStatus getMartialStatus() { return martialStatus; }
    /** @return formatted string representation of the report */
    @Override
    public String toString() {
        return "Report ID: " + reportId +
                "\nProject Name: " + projectName +
                "\nApplicant ID: " + applicantId +
                "\nFlat Type: " + flatType +
                "\nApplicant Age: " + applicantAge +
                "\nMarital Status: " + martialStatus + "\n";
    }
}
