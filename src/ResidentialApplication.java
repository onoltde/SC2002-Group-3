public class ResidentialApplication implements Application {

    private final String applicantId;   //can be applicantID or officerID depending on who
    private Application.Status status;
    private final String projectName;
    private final Flat.Type flatType;

    public ResidentialApplication(String applicantId, ResidentialApplication.Status status, String projectName, Flat.Type flatType) {
        this.applicantId = applicantId;
        this.status = status;
        this.projectName = projectName;
        this.flatType = flatType;

    }

    public String getProjectName() {
        return projectName;
    }

    public TeamApplication.Status getStatus() {
        return status;
    }

    public Flat.Type getFlatType() {
        return flatType;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

}