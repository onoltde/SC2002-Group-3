package Report;

import Project.*;
import Users.*;

/**
 * Represents a report containing details about a housing project applicant.
 */
public class Report {
    private String reportId;
    private String projectName;
    private String applicantId;
    private Flat.Type flatType;
    private int applicantAge;
    private User.MaritalStatus maritalStatus;

    /**
     * Constructs a Report with the given details.
     *
     * @param reportId     the unique ID of the report
     * @param projectName  the name of the housing project
     * @param applicantId  the applicant's ID
     * @param flatType     the type of flat applied for
     * @param applicantAge the age of the applicant
     * @param maritalStatus the marital status of the applicant
     */
    public Report(String reportId, String projectName, String applicantId,
                  Flat.Type flatType, int applicantAge, User.MaritalStatus maritalStatus) {
        this.reportId = reportId;
        this.projectName = projectName;
        this.applicantId = applicantId;
        this.flatType = flatType;
        this.applicantAge = applicantAge;
        this.maritalStatus = maritalStatus;
    }

    /**
     * @return the report ID
     */
    public String getId() {
        return reportId;
    }

    /**
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @return the applicant's ID
     */
    public String getApplicantId() {
        return applicantId;
    }

    /**
     * @return the applicant's age
     */
    public int getApplicantAge() {
        return applicantAge;
    }

    /**
     * @return the flat type
     */
    public Flat.Type getFlatType() {
        return flatType;
    }

    /**
     * @return the marital status of the applicant
     */
    public User.MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Returns a string representation of the report.
     *
     * @return formatted string with report details
     */
    @Override
    public String toString() {
        return "Report ID: " + reportId +
                "\nProject Name: " + projectName +
                "\nApplicant ID: " + applicantId +
                "\nFlat Type: " + flatType +
                "\nApplicant Age: " + applicantAge +
                "\nMarital Status: " + maritalStatus + "\n";
    }
}
