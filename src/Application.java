public class Application {
    private String applicantId;
    private Status status;
    private final String projectName;
    private final Flat.Type flatType;



    public enum Status {
        PENDING,
        SUCCESSFUL,
        UNSUCCESSFUL,
        BOOKED;
    }

    public Application(String applicantId, Application.Status status, String projectName, Flat.Type flatType) {
        this.applicantId = applicantId;
        this.status = status;
        this.projectName = projectName;
        this.flatType = flatType;

    }

    public String getProjectName() {
        return projectName;
    }

    public Flat.Type getFlatType() {
        return flatType;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
