package Project;

class Application {
    private Applicant applicant;
    private Project project;
    private ApplicationStatus status;
    private String flatType;

    public Application(Applicant applicant, Project project, ApplicationStatus status, String flatType) {
        this.applicant = applicant;
        this.project = project;
        this.status = status;
        this.flatType = flatType;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Project getProject() {
        return project;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getFlatType() {
        return flatType;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}