package Report;
import Project.*;
import Users.*;

public class Report {
    private String reportId;
    private String projectName;
    private String applicantId;
    private Flat.Type flatType;
    private int applicantAge;
    private User.MaritalStatus martialStatus;

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
    public String getId() { return reportId; }
    public String getProjectName() { return projectName; }
    public String getApplicantId() { return applicantId; }
    public int getApplicantAge() { return applicantAge; }
    public Flat.Type getFlatType() { return flatType; }
    public User.MaritalStatus getMartialStatus() { return martialStatus; }

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
